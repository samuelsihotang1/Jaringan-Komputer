import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public ClientHandler(Socket client) {
      this.client = client;
    }

    public void run() {
      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        userID = in.readLine();
        while (true) {
          String message = in.readLine();
          if (message == null) {
            clients.remove(client);
            break;
          } else {
            for (Socket Client : clients) {
              PrintWriter out = new PrintWriter(Client.getOutputStream(), true);
              Date date = new Date();
              String currentTime = formatter.format(date);
              out.println("[" + userID + "]'" + currentTime + "' > " + message);
            }
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
