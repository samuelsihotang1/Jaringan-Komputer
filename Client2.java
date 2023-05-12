import java.io.*;
import java.net.*;

public class Client2 {

  public static void main(String args[]) throws Exception {
    ServerSocket checkServer = null;
    Socket clientSocket = null;
    Socket cl = null;
    BufferedReader is = null;
    DataOutputStream os = null;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String userInput = null;
    String output = null;
    String mode = null;
    int targetport;
    String encrypteduserInput = null;
    String decryptedString = null;

    // Determine mode: server or client
    if (args.length > 0 && args[0].equals("server")) {
      mode = "server";
    }
    if (args.length > 0 && args[0].equals("client")) {
      mode = "client";
    }

    // Start server or connect to server
    if (mode.equals("server")) {
      try {
        System.out.print("ID Anda: ");
        String str = stdin.readLine();
        targetport = str.codePoints().sum();
        checkServer = new ServerSocket(targetport);
        clientSocket = checkServer.accept();
        is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        os = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Kamu telah terhubung");
      } catch (IOException e) {
        System.out.println(e);
      }
    } else {
      cl = connectToServer(stdin);
      is = new BufferedReader(new InputStreamReader(cl.getInputStream()));
      os = new DataOutputStream(cl.getOutputStream());
    }

    // Chat loop
    try {
      while (true) {
        if (mode.equals("client")) {
          System.out.print("Anda: ");
          userInput = stdin.readLine();
          encrypteduserInput = encryptString(userInput);
          os.writeBytes(encrypteduserInput + "\n");
          if (userInput.equalsIgnoreCase("exit")) {
            break;
          }
          output = is.readLine();
          decryptedString = decryptString(output);
          System.out.println("Teman: " + decryptedString);
          if (decryptedString.equalsIgnoreCase("exit")) {
            break;
          }
        } else if (mode.equals("server")) {
          output = is.readLine();
          decryptedString = decryptString(output);
          System.out.println("Teman: " + decryptedString);
          if (decryptedString.equalsIgnoreCase("exit")) {
            break;
          }
          System.out.print("Anda: ");
          userInput = stdin.readLine();
          encrypteduserInput = encryptString(userInput);
          os.writeBytes(encrypteduserInput + "\n");
          if (userInput.equalsIgnoreCase("exit")) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Close connection
    try {
      is.close();
      os.close();
      if (mode.equals("server")) {
        clientSocket.close();
        checkServer.close();
      } else {
        cl.close();
      }
    } catch (IOException x) {
      System.out.println("Gagal Menutup Koneksi");
    }
  }

  // Function to Connect to Server
  public static Socket connectToServer(BufferedReader stdin) throws IOException {
    Socket cl = null;
    boolean again = false;
    int REMOTE_PORT;

    do {
      try {
        if (again == true) {
          System.out.print("Coba Masukkan Kembali ID: ");
        } else {
          System.out.print("Masukkan ID Target: ");
        }

        String str = stdin.readLine();
        REMOTE_PORT = str.codePoints().sum();
        cl = new Socket("localhost", REMOTE_PORT);
        again = false;
      } catch (UnknownHostException el) {
        System.out.println("Gagal Mengakses Target");
        again = true;
      } catch (IOException e2) {
        System.out.println("Gagal Mengakses Target");
        again = true;
      }
    } while (again == true);

    System.out.println("Kamu telah terhubung");
    return cl;
  }

  public static String encryptString(String input) {
    StringBuilder encrypted = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      c += 1 + i;
      encrypted.append(c);
    }
    return encrypted.toString();
  }

  public static String decryptString(String input) {
    StringBuilder decrypted = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      c -= 1 + i;
      decrypted.append(c);
    }
    return decrypted.toString();
  }

}