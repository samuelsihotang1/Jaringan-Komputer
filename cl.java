import java.io.*;
import java.net.*;

public class cl {
  public static void main(String[] args) throws IOException {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("ID Anda: ");
    String userID = stdin.readLine();
    if (userID.equals("---")) {
      System.exit(0);
    }

    Socket socket = new Socket("localhost", 12345);
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

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
  }
}
