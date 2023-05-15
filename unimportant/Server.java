import java.io.*;
import java.net.*;

public class Server {
  public final static int TESTPORT = 424;

  public static void main(String[] args) {
    ServerSocket checkServer = null;
    String line;
    BufferedReader is = null;
    DataOutputStream os = null;
    Socket clientSocket = null;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String userInput = null;

    // Start server
    try {
      checkServer = new ServerSocket(TESTPORT);
      System.out.println("Aplikasi server hidup dengan ID: " + TESTPORT);
    } catch (IOException e) {
      System.out.println(e);
    }
    // Start server

    try {
      clientSocket = checkServer.accept();
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      os = new DataOutputStream(clientSocket.getOutputStream());

      while (true) {
        if (is != null) {
          line = is.readLine();
          System.out.println("Dari client: " + line);
          if (line.equalsIgnoreCase("exit")) {
            break;
          }
        }
        if (stdin != null) {
          userInput = stdin.readLine();
          os.writeBytes(userInput.toString() + "\n");
          if (userInput.equalsIgnoreCase("exit")) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      os.close();
      is.close();
      clientSocket.close();
      checkServer.close();
    } catch (IOException ic) {
      ic.printStackTrace();
    }
  }
}