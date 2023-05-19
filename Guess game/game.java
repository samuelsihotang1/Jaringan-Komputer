import java.io.*;
import java.net.*;

public class game {

  public static void main(String args[]) throws Exception {
    // Mendeklarasikan variabel
    ServerSocket joinServer = null;
    Socket socketServerr = null;
    Socket clientt = null;
    BufferedReader terima = null;
    DataOutputStream kirim = null;
    BufferedReader inputan = new BufferedReader(new InputStreamReader(System.in));
    String inputanPengguna = null;
    String dariYangLain = null;
    String mode = null;
    int targetport;
    String enkripsi = null;
    String dekripsi = null;
    Boolean mulai = false;
    int point = 3;

    // Menentukan mode server atau client
    if (args.length > 0 && args[0].equals("server")) {
      mode = "server";
    }
    if (args.length > 0 && args[0].equals("client")) {
      mode = "client";
      mulai = true;
    }

    // Memulai server atau client dan menentukan port
    if (mode.equals("server")) {
      try {
        System.out.print("ID Anda: ");
        String str = inputan.readLine();
        targetport = str.codePoints().sum();
        joinServer = new ServerSocket(targetport);
        socketServerr = joinServer.accept();
        terima = new BufferedReader(new InputStreamReader(socketServerr.getInputStream()));
        kirim = new DataOutputStream(socketServerr.getOutputStream());
        System.out.println("Kamu telah terhubung");
      } catch (IOException e) {
        System.out.println(e);
      }
    } else if (mode.equals("client")) {
      clientt = connectToServer(inputan);
      terima = new BufferedReader(new InputStreamReader(clientt.getInputStream()));
      kirim = new DataOutputStream(clientt.getOutputStream());
    }

    // Mulai game
    try {
      while (true) {
        // Menampilkan poin
        System.out.println("Poin anda sekarang: " + point);
        System.out.println("");
        // Menampilkan angka yang diinput oleh pengguna
        if (mulai == true) {
          do {
            // Meminta inputan angka dari pengguna
            System.out.print("Masukkan angka (0-9): ");
            // Membaca inputan pengguna
            inputanPengguna = inputan.readLine();
            // Mengecek apakah inputan pengguna berupa angka
            if (Integer.parseInt(inputanPengguna) > 9 || Integer.parseInt(inputanPengguna) < 0) {
              System.out.println("Anda memasukkan angka di luar dari 0-9");
            }
          } while (Integer.parseInt(inputanPengguna) > 9 || Integer.parseInt(inputanPengguna) < 0);
          // Mengulang jika inputan pengguna berupa angka di luar dari 0-9

          // Mengenkripsi inputan pengguna
          enkripsi = enkripsiKata(inputanPengguna);
          // Mengirimkan inputan pengguna ke lawan
          kirim.writeBytes(enkripsi + "\n");

          // Mengecek apakah inputan pengguna berupa ---
          if (inputanPengguna.equalsIgnoreCase("---")) {
            System.exit(0);
          }
        }
        // Menerima inputan angka dari lawan
        dariYangLain = terima.readLine();
        // Mendekripsi inputan lawan
        dekripsi = dekripsiKata(dariYangLain);
        System.out.println("");
        System.out.print("Tebak angka yang diinput oleh teman anda (0-9): ");
        // Membaca inputan pengguna
        inputanPengguna = inputan.readLine();
        // Mengecek apakah inputan pengguna sama dengan inputan lawan
        if (Integer.parseInt(inputanPengguna) == Integer.parseInt(dekripsi)) {
          System.out.println("Anda benar, anda mendapatkan tambahan 1 poin");
          // Menambahkan poin jika inputan pengguna sama dengan inputan lawan
          point++;
        } else if (dekripsi.equalsIgnoreCase("---") || inputanPengguna.equalsIgnoreCase("---")) {
          // Mengecek apakah inputan pengguna atau inputan lawan berupa ---
          System.exit(0);
        } else {
          // Mengurangi poin jika inputan pengguna tidak sama dengan inputan lawan
          System.out.println("Anda salah, anda kehilangan 1 poin");
          point--;
        }
        // Mengubah nilai mulai menjadi true
        mulai = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Close connection
    try {
      // Menutup semua koneksi
      terima.close();
      kirim.close();
      if (mode.equals("server")) {
        socketServerr.close();
        joinServer.close();
      } else {
        clientt.close();
      }
    } catch (IOException x) {
      System.out.println("Gagal Menutup Koneksi");
    }
  }

  // Function to Connect to Server
  public static Socket connectToServer(BufferedReader inputan) throws IOException {
    Socket klien = null;
    boolean lagi = false;
    int targetport;

    do {
      try {
        if (lagi == true) {
          // Jika gagal mengakses target
          System.out.print("Coba Masukkan Kembali ID: ");
        } else {
          // Jika berhasil mengakses target
          System.out.print("Masukkan ID Target: ");
        }

        // Membaca inputan pengguna
        String str = inputan.readLine();
        // Mengubah inputan pengguna menjadi angka untuk port
        targetport = str.codePoints().sum();
        // Membuat koneksi ke server
        klien = new Socket("localhost", targetport);
        // Mengubah nilai lagi menjadi false
        lagi = false;
      } catch (UnknownHostException el) {
        System.out.println("Gagal Mengakses Target");
        lagi = true;
      } catch (IOException e2) {
        System.out.println("Gagal Mengakses Target");
        lagi = true;
      }
    } while (lagi == true);

    System.out.println("Kamu telah terhubung");
    return klien;
  }

  public static String enkripsiKata(String input) {
    // Mengenkripsi inputan pengguna
    StringBuilder enkripsi = new StringBuilder();
    // Mengubah setiap karakter menjadi karakter setelahnya
    for (int i = 0; i < input.length(); i++) {
      char karakter = input.charAt(i);
      karakter += 1 + i;
      enkripsi.append(karakter);
    }
    // Mengembalikan nilai enkripsi
    return enkripsi.toString();
  }

  public static String dekripsiKata(String input) {
    // Mendekripsi inputan lawan
    StringBuilder dekripsi = new StringBuilder();
    // Mengubah setiap karakter menjadi karakter sebelumnya
    for (int i = 0; i < input.length(); i++) {
      char karakter = input.charAt(i);
      karakter -= 1 + i;
      dekripsi.append(karakter);
    }
    // Mengembalikan nilai dekripsi
    return dekripsi.toString();
  }
}