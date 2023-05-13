import java.io.*;
import java.net.*;
import java.util.*;

public class se {
  private static List<Socket> clients = new ArrayList<Socket>();

  public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(12345);
    System.out.println("Port Server adalah 12345");

    while (true) {
      Socket client = server.accept();
      clients.add(client);
      Thread t = new Thread(new ClientHandler(client));
      t.start();
    }
  }

  private static class ClientHandler implements Runnable {
    private Socket client;
    private String userID;

    public ClientHandler(Socket client) {
      this.client = client;
    }

    public void run() {
      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        userID = in.readLine();
        System.out.println("[" + userID + "] bergabung");
        while (true) {
          String message = in.readLine();
          if (message == null) {
            clients.remove(client);
            System.out.println("[" + userID + "] keluar");
            break;
          }
          System.out.println("[" + userID  + "] > " + message);
          for (Socket otherClient : clients) {
            if (otherClient != client) {
              PrintWriter out = new PrintWriter(otherClient.getOutputStream(), true);
              out.println(userID + ": " + message);
            }
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
