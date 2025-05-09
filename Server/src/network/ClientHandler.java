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
import services.ServiceProvider;
import utilities.LogLevel;
import utilities.Logger;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;

public class ClientHandler implements Runnable
{

  private final Socket clientSocket;
  private final ServiceProvider serviceProvider;
  private final Logger logger;
  private final Gson gson = new GsonBuilder().create();

  public ClientHandler(Socket clientSocket, ServiceProvider serviceProvider)
  {
    this.clientSocket = clientSocket;
    this.serviceProvider = serviceProvider;
    logger = serviceProvider.getLogger();
  }

  @Override public void run()
  {
    try (BufferedReader incomingData = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    PrintWriter outgoingData = new PrintWriter(clientSocket.getOutputStream(),true))
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
        clientSocket.close();
      }
      catch (IOException ignore) {}
    }
  }

  private void handleRequestWithErrorHandling(BufferedReader incomingData,
      PrintWriter outgoingData) throws IOException
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
      logger.log(Arrays.toString(e.getStackTrace()) + "\n" + e.getMessage(),
          LogLevel.ERROR);
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

  private void handleRequest(BufferedReader incomingData,
      PrintWriter outgoingData)
      throws IOException, ClassNotFoundException, SQLException
  {
    String inputJson = incomingData.readLine();
    Request request = gson.fromJson(inputJson, Request.class);
    logger.log("Incoming request: " + request.handler() + "/" + request.action()
        + ". Body: " + gson.toJson(request.payload()), LogLevel.INFO);
    RequestHandler handler = switch (request.handler())
    {
      case "login" -> serviceProvider.getLoginRequestHandler();
      case "register" -> serviceProvider.getRegisterRequestHandler();
      case "search" -> serviceProvider.getSearchRequestHandler();
      case "trains" -> serviceProvider.getTrainsRequestHandler();
      case "seat" -> serviceProvider.getSeatRequestHandler();
      case "confirm" -> serviceProvider.getConfirmRequestHandler();
      case "add" -> serviceProvider.getAddRequestHandler();
      case "mainAdmin" -> serviceProvider.getMainAdminRequestHandler();
      case "modify" -> serviceProvider.getModifyRequestHandler();
      case "myAccountTraveller" -> serviceProvider.getMyAccountRequestHandler();
          default -> throw new IllegalStateException(
          "Unexpected value: " + request.handler());
    };

    Object result = handler.handler(request.action(), request.payload());
    Response response = new Response("SUCCESS", result);
    outgoingData.println(gson.toJson(response));
  }
}
