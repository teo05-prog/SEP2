package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;

import java.io.*;
import java.net.Socket;

public class ClientSocket
{
  private static final Gson gson = new GsonBuilder().create();

  public static Object sentRequest(Request request)
  {
    try (Socket socket = new Socket("localhost", 4892);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
    {
      String jsonRequest = gson.toJson(request);
      out.println(jsonRequest);

      String jsonResponse = in.readLine();
      Response response = gson.fromJson(jsonResponse, Response.class);

      switch (response.status())
      {
        case "SUCCESS" ->
        {
          return response.payload();
        }
        case "ERROR" ->
        {
          ErrorResponse error = gson.fromJson(gson.toJson(response.payload()), ErrorResponse.class);
          throw new RuntimeException(error.errorMessage());
        }
        case "SERVER_FAILURE" ->
            throw new RuntimeException("Server failed to process request. Please try again later.");
        default -> throw new RuntimeException("Unknown server status: " + response.status());
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException("Could not connect to server!", e);
    }
  }

  public static Object sendRequest(String handler, String action, Object payload)
  {
    Request request = new Request(handler, action, payload);
    return sentRequest(request);
  }
}
