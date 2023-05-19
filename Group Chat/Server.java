import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
  // Membuat list untuk mengumpulkan client
  private static List<Socket> clients = new ArrayList<Socket>();

  public static void main(String[] args) throws IOException {
    // Membuat scanner untuk input
    Scanner scanner = new Scanner(System.in);
    // Menerima input dari user
    System.out.print("Masukkan kata kunci: ");
    String str = scanner.nextLine();
    // Menjumlahkan ASCII dari kata kunci
    int port = str.codePoints().sum();
    // Membuat server socket berdasarkan jumlah ASCII
    ServerSocket server = new ServerSocket(port);

    while (true) {
      // Menerima koneksi dari client
      Socket client = server.accept();
      // Menambahkan client ke list
      clients.add(client);
      // Membuat thread untuk client
      Thread threadClient = new Thread(new pengurusPengguna(client));
      // Menjalankan thread
      threadClient.start();
    }
  }

  private static class pengurusPengguna implements Runnable {
    // Membuat socket untuk client
    private Socket client;
    // Membuat variabel untuk menyimpan userID
    private String userID;
    // Membuat variabel untuk menyimpan waktu dengan format HH:mm:ss
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    // Constructor
    public pengurusPengguna(Socket client) {
      this.client = client;
    }

    public void run() {
      try {
        // Membuat buffer untuk membaca input dari client
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // Membaca userID dari client
        userID = in.readLine();
        while (true) {
          // Membaca pesan dari client
          String message = in.readLine();
          // Jika pesan kosong, maka client akan dihapus dari list
          if (message == null) {
            clients.remove(client);
            break;
          } else {
            // Jika pesan tidak kosong, maka pesan akan dikirim ke semua client
            for (Socket Client : clients) {
              // Membuat buffer untuk mengirim pesan ke client
              PrintWriter out = new PrintWriter(Client.getOutputStream(), true);
              // Membuat variabel untuk menyimpan waktu saat ini
              Date date = new Date();
              // Mengubah waktu saat ini menjadi string dengan format HH:mm:ss
              String currentTime = formatter.format(date);
              // Mengirim pesan ke client
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
