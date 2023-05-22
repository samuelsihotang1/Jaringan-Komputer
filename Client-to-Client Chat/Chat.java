import java.io.*;
import java.net.*;

public class Chat {

  public static void main(String args[]) throws Exception {
    // Deklarasi Variabel
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

    // Menentukan Mode Server atau Client
    if (args.length > 0 && args[0].equals("server")) {
      mode = "server";
    }
    if (args.length > 0 && args[0].equals("client")) {
      mode = "client";
    }

    // Jika mode server, maka buat server socket
    if (mode.equals("server")) {
      try {
        System.out.print("ID Anda: ");
        // Membaca input dari user
        String str = stdin.readLine();
        // Menghitung jumlah karakter dari input user
        targetport = str.codePoints().sum();
        // Membuat server socket
        checkServer = new ServerSocket(targetport);
        // Menunggu koneksi dari client
        clientSocket = checkServer.accept();
        // Membuat input dan output stream
        is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        os = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Kamu telah terhubung");
      } catch (IOException e) {
        System.out.println(e);
      }
      // Jika mode client, maka buat client socket
    } else {
      // Membuat koneksi ke server
      cl = connectToServer(stdin);
      is = new BufferedReader(new InputStreamReader(cl.getInputStream()));
      os = new DataOutputStream(cl.getOutputStream());
    }

    // Chat loop
    try {
      while (true) {
        if (mode.equals("client")) {
          System.out.print("Anda: ");
          // Membaca input dari user
          userInput = stdin.readLine();
          // Mengenkripsi input user
          encrypteduserInput = encryptString(userInput);
          // Mengirimkan input user ke server
          os.writeBytes(encrypteduserInput + "\n");
          // Jika input user adalah exit, maka keluar dari loop
          if (userInput.equalsIgnoreCase("exit")) {
            break;
          }
          // Menerima pesan dari server
          output = is.readLine();
          // Mendekripsi pesan dari server
          decryptedString = decryptString(output);
          System.out.println("Teman: " + decryptedString);
          // Jika pesan dari server adalah exit, maka keluar dari loop
          if (decryptedString.equalsIgnoreCase("exit")) {
            break;
          }
          // Jika mode server, maka menerima pesan dari client
        } else if (mode.equals("server")) {
          // Menerima pesan dari client
          output = is.readLine();
          // Mendekripsi pesan dari client
          decryptedString = decryptString(output);
          System.out.println("Teman: " + decryptedString);
          // Jika pesan dari client adalah exit, maka keluar dari loop
          if (decryptedString.equalsIgnoreCase("exit")) {
            break;
          }
          System.out.print("Anda: ");
          // Membaca input dari user
          userInput = stdin.readLine();
          // Mengenkripsi input user
          encrypteduserInput = encryptString(userInput);
          // Mengirimkan input user ke client
          os.writeBytes(encrypteduserInput + "\n");
          // Jika input user adalah exit, maka keluar dari loop
          if (userInput.equalsIgnoreCase("exit")) {
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Menutup koneksi
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

  // Fungsi untuk membuat koneksi ke server
  public static Socket connectToServer(BufferedReader stdin) throws IOException {
    // Deklarasi Variabel
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
        // Membaca input dari user
        String str = stdin.readLine();
        // Menghitung jumlah karakter dari input user
        REMOTE_PORT = str.codePoints().sum();
        // Membuat koneksi ke server
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
    // Deklarasi Variabel
    StringBuilder encrypted = new StringBuilder();
    // Mengenkripsi input user
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      c += 1 + i;
      encrypted.append(c);
    }
    // Mengembalikan nilai input yang telah dienkripsi
    return encrypted.toString();
  }

  public static String decryptString(String input) {
    // Deklarasi Variabel
    StringBuilder decrypted = new StringBuilder();
    // Mendekripsi input user
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      c -= 1 + i;
      decrypted.append(c);
    }
    // Mengembalikan nilai input yang telah didekripsi
    return decrypted.toString();
  }
}