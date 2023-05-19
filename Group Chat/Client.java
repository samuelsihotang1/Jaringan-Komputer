import java.io.*;
import java.net.*;

public class Client {
  public static void main(String[] args) throws IOException {
    // Membuat objek BufferedReader untuk membaca input dari keyboard
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    // Menentukan apakah program ini akan berperan sebagai pengirim atau penerima
    if (args.length != 0 && args[0].equals("sender")) {
      System.out.print("Masukkan kata kunci: ");
      // Membaca input dari keyboard
      String str = stdin.readLine();
      // Menjumlahkan nilai ASCII dari setiap karakter pada string
      int port = str.codePoints().sum();
      
      // Membuat koneksi ke server
      Socket socket = new Socket("localhost", port);
      // Membuat objek PrintWriter untuk menulis ke socket
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      System.out.print("ID Anda: ");
      // Meminta input userID dari keyboard
      String userID = stdin.readLine();
      // Mengirim userID ke server
      out.println(userID);

      while (true) {
        System.out.print("> ");
        // Meminta input pesan dari keyboard
        String message = stdin.readLine();
        // Jika pesan yang dikirim adalah "---", maka program akan berhenti
        if (message.equals("---")) {
          break;
        }
        // Mengirim pesan ke server
        out.println(message);
      }
      socket.close();
    } else if (args.length != 0 && args[0].equals("receiver")) {
      System.out.print("Masukkan kata kunci: ");
      // Membaca input dari keyboard
      String str = stdin.readLine();
      // Menjumlahkan nilai ASCII dari setiap karakter pada string
      int port = str.codePoints().sum();

      // Membuat koneksi ke server
      Socket socket = new Socket("localhost", port);
      // Membuat objek BufferedReader untuk membaca dari socket
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      while (true) {
        // Membaca pesan dari server
        String response;
        // Jika pesan yang diterima tidak null, maka program akan menampilkan pesan
        while ((response = in.readLine()) != null) {
          // Menampilkan pesan
          System.out.println(response);
          break;
        }
      }
    }
  }
}
