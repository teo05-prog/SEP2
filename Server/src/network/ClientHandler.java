package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import model.exceptions.NotFoundException;
import model.exceptions.ServerFailureException;
import model.exceptions.ValidationException;
import network.exceptions.InvalidActionException;
import network.requestHandlers.RequestHandler;
import startup.ServiceProvider;
import utilities.LogLevel;
import utilities.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler implements Runnable
{

  private final Socket socket;
  private final ServiceProvider serviceProvider;
  private final Logger logger;
  private final Gson gson = new GsonBuilder().create();

  public ClientHandler(Socket socket, ServiceProvider serviceProvider)
  {
    this.socket = socket;
    this.serviceProvider = serviceProvider;
    logger = serviceProvider.getLogger();
  }

  @Override public void run()
  {
    try (BufferedReader incomingData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outgoingData = new PrintWriter(socket.getOutputStream(), true))
    {
      handleRequestWithErrorHandling(incomingData, outgoingData);
    }
    catch (IOException e)
    {
      logger.log(Arrays.toString(e.getStackTrace()), LogLevel.ERROR);
    }
    finally
    {
      try
      {
        socket.close();
      }
      catch (IOException ignore)
      {
      }
    }
  }

  private void handleRequestWithErrorHandling(BufferedReader incomingData, PrintWriter outgoingData) throws IOException
  {
    try
    {
      handleRequest(incomingData, outgoingData);
    }
    catch (NotFoundException | InvalidActionException | ValidationException e)
    {
      logger.log(e.getMessage(), LogLevel.INFO);
      ErrorResponse payload = new ErrorResponse(e.getMessage());
      Response error = new Response("ERROR", payload);
      outgoingData.println(gson.toJson(error));
    }
    catch (ServerFailureException e)
    {
      logger.log(Arrays.toString(e.getStackTrace()) + "\n" + e.getMessage(), LogLevel.ERROR);
      ErrorResponse payload = new ErrorResponse(e.getMessage());
      Response error = new Response("SERVER_FAILURE", payload);
      outgoingData.println(gson.toJson(error));
    }
    catch (ClassCastException e)
    {
      logger.log(e.getMessage(), LogLevel.INFO);
      ErrorResponse payload = new ErrorResponse("Invalid request");
      Response error = new Response("ERROR", payload);
      outgoingData.println(gson.toJson(error));
    }
    catch (Exception e)
    {
      logger.log(Arrays.toString(e.getStackTrace()), LogLevel.ERROR);
      ErrorResponse payload = new ErrorResponse(e.getMessage());
      Response error = new Response("SERVER_FAILURE", payload);
      outgoingData.println(gson.toJson(error));
    }
  }

  private void handleRequest(BufferedReader incomingData, PrintWriter outgoingData) throws Exception
  {
    String inputJson = incomingData.readLine();
    Request request = gson.fromJson(inputJson, Request.class);
    logger.log(
        "Incoming request: " + request.handler() + "/" + request.action() + ". Body: " + gson.toJson(request.payload()),
        LogLevel.INFO);
    RequestHandler handler = switch (request.handler())
    {
      case "login" -> serviceProvider.getLoginRequestHandler();
      case "register" -> serviceProvider.getRegisterRequestHandler();
      case "search" -> serviceProvider.getSearchRequestHandler();
      case "trains" -> serviceProvider.getTrainsRequestHandler();
      case "schedules" -> serviceProvider.getSchedulesRequestHandler();
      case "seat" -> serviceProvider.getSeatRequestHandler();
      case "ticket" -> serviceProvider.getTicketRequestHandler();
      case "add" -> serviceProvider.getAddRequestHandler();
      case "modify" -> serviceProvider.getModifyRequestHandler();
      case "user" -> serviceProvider.getUserDetailsRequestHandler();
      default -> throw new IllegalStateException("Unexpected value: " + request.handler());
    };

    try
    {
      Object result = handler.handler(request.action(), request.payload());
      Response response = new Response("SUCCESS", result);
      outgoingData.println(gson.toJson(response));
    }
    catch (ValidationException e)
    {
      logger.log("Validation failed: " + e.getMessage(), LogLevel.WARNING);
      Response error = new Response("ERROR", new ErrorResponse(e.getMessage()));
      outgoingData.println(gson.toJson(error));
    }
    catch (Exception e)
    {
      logger.log("Unexpected server error: " + e.getMessage(), LogLevel.ERROR);
      Response error = new Response("SERVER_FAILURE", new ErrorResponse("Internal server error"));
      outgoingData.println(gson.toJson(error));
    }
  }
}
