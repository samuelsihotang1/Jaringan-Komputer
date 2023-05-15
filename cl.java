import java.io.*;
import java.net.*;

public class cl {
  public static void main(String[] args) throws IOException {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    if (args.length != 0 && args[0].equals("sender")) {
      System.out.print("Masukkan kata kunci: ");
      String str = stdin.readLine();
      int port = str.codePoints().sum();
      
      Socket socket = new Socket("localhost", port);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      System.out.print("ID Anda: ");
      String userID = stdin.readLine();
      out.println(userID);

      while (true) {
        System.out.print("> ");
        String message = stdin.readLine();
        if (message.equals("---")) {
          break;
        }
        out.println(message);
      }
      socket.close();
    } else if (args.length != 0 && args[0].equals("receiver")) {
      System.out.print("Masukkan kata kunci: ");
      String str = stdin.readLine();
      int port = str.codePoints().sum();

      Socket socket = new Socket("localhost", port);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      while (true) {
        String response;
        while ((response = in.readLine()) != null) {
          System.out.println(response);
          break;
        }
      }
    }
  }
}
