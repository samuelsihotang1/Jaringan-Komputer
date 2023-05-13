import java.io.*;
import java.net.*;
import java.util.*;

public class se {
  private static List<Socket> clients = new ArrayList<Socket>();

  public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(12345);

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
        PrintWriter out;
        userID = in.readLine();
        while (true) {
          String message = in.readLine();
          if (message == null) {
            clients.remove(client);
            break;
          }
          for (Socket Client : clients) {
            out = new PrintWriter(Client.getOutputStream(), true);
            out.println("[" + userID + "] > " + message);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
