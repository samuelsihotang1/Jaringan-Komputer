import java.io.*;
import java.net.*;

public class Client {
  public static void main(String args[]) throws Exception {
    Socket cl = null;
    BufferedReader is = null;
    DataOutputStream os = null;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String userInput = null;
    String output = null;

    // Connect to server
    cl = connectToServer(stdin);
    is = new BufferedReader(new InputStreamReader(cl.getInputStream()));
    os = new DataOutputStream(cl.getOutputStream());
    // Connect to server

    // Chat loop
    while (true) {
      if (stdin != null) {
        userInput = stdin.readLine();
        os.writeBytes(userInput + "\n");
        if (userInput.equalsIgnoreCase("exit")) {
          break;
        }
      }
      if (is != null) {
        output = is.readLine();
        System.out.println("Dari server: " + output);
        if (output.equalsIgnoreCase("exit")) {
          break;
        }
      }
    }

    // Close connection
    try {
      is.close();
      os.close();
      cl.close();
    } catch (IOException x) {
      System.out.println("Gagal Menutup Koneksi" + x);
    }
  }

  // Function to Connect to Server
  private static Socket connectToServer(BufferedReader stdin) throws IOException {
    Socket cl = null;
    boolean again = false;
    int REMOTE_PORT;

    do {
      try {
        if (again == true) {
          System.out.print("Coba Masukkan Kembali Host: ");
        } else {
          System.out.print("Masukkan Host: ");
        }
        REMOTE_PORT = Integer.parseInt(stdin.readLine());
        cl = new Socket("localhost", REMOTE_PORT);
        again = false;
      } catch (UnknownHostException el) {
        System.out.println("Gagal Mengakses Host");
        again = true;
      } catch (IOException e2) {
        System.out.println("Gagal Mengakses Host");
        again = true;
      }
    } while (again == true);

    System.out.println("Koneksi ke server berhasil!");
    return cl;
  }
}
