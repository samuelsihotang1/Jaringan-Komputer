import java.io.*;
import java.net.*;

public class TransferFile {

  public static void main(String args[]) throws Exception {
    // membuat objek BufferedReader untuk mengambil input dari keyboard
    BufferedReader masukan = new BufferedReader(new InputStreamReader(System.in));
    // port yang digunakan
    int portTujuan;

    // menentukan apakah client adalah pengirim atau penerima
    if (args.length > 0 && args[0].equals("pengirim")) {
      try {
        // meminta inputan ID dari pengguna
        System.out.print("ID Anda: ");
        String str = masukan.readLine();
        // menjumlahkan nilai ASCII dari ID pengguna untuk menentukan port yang digunakan
        portTujuan = str.codePoints().sum();

        // membuat socket server
        ServerSocket serverSocket = new ServerSocket(portTujuan);

        // menerima koneksi dari client
        Socket socketpengguna = serverSocket.accept();
        System.out.println("Kamu telah terhubung");

        // membaca file yang akan dikirim
        System.out.print("Masukkan nama file beserta ekstensinya: ");
        String namafile = masukan.readLine();

        // mengirim nama file ke client
        DataOutputStream kirim = new DataOutputStream(socketpengguna.getOutputStream());
        kirim.writeBytes(namafile + "\n");

        // membaca file yang akan dikirim
        File kirimfile = new File(namafile);
        FileInputStream inputfile = new FileInputStream(kirimfile);

        // membungkus file yang akan dikirim dengan BufferedInputStream
        BufferedInputStream bufferfile = new BufferedInputStream(inputfile);

        // mengirim file ke client
        OutputStream streamKirim = socketpengguna.getOutputStream();

        byte[] buffer = new byte[1024];
        int bacaBytes;
        // membaca file yang akan dikirim
        while ((bacaBytes = bufferfile.read(buffer)) != -1) {
          streamKirim.write(buffer, 0, bacaBytes);
        }
        streamKirim.flush();

        // menutup semua koneksi dan socket
        bufferfile.close();
        streamKirim.close();
        socketpengguna.close();
        serverSocket.close();

        System.out.println("Pengiriman file berhasil");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (args.length > 0 && args[0].equals("penerima")) {
      try {
        // mendeklarasikan variabel yang dibutuhkan
        String poort = null;
        String namafile = null;
        String foldername = "folder-penerima";
        Boolean again = false;
        Socket socketpengguna = null;
        do {
          try {
            // meminta inputan ID dari pengguna
            if (again == true) {
              System.out.print("Coba Masukkan Kembali ID: ");
            } else {
              System.out.print("Masukkan ID Teman anda: ");
            }
            // menerima inputan ID dari pengguna
            poort = masukan.readLine();

            // menjumlahkan nilai ASCII dari ID pengguna untuk menentukan port yang digunakan
            portTujuan = poort.codePoints().sum();

            // membuat socket client
            socketpengguna = new Socket("localhost", portTujuan);
            System.out.println("Kamu telah terhubung");
            again = false;
          } catch (UnknownHostException el) {
            System.out.println("Gagal Mengakses Target");
            again = true;
          } catch (IOException e2) {
            System.out.println("Gagal Mengakses Target");
            again = true;
          }
        } while (again == true);

        // membaca file yang diterima dari server
        InputStream streamMasukan = socketpengguna.getInputStream();
        // membungkus file yang diterima dengan BufferedInputStream
        BufferedInputStream streamMasukanBuffer = new BufferedInputStream(streamMasukan);

        // menerima nama file
        BufferedReader terimaStrLawan = new BufferedReader(new InputStreamReader(socketpengguna.getInputStream()));
        // membaca nama file
        namafile = terimaStrLawan.readLine();

        // membuat folder jika belum ada
        File folder = new File(foldername);
        if (!folder.exists()) {
          folder.mkdir();
        }
        
        // menuliskan file yang diterima ke folder
        FileOutputStream terimaFile = new FileOutputStream(foldername + "/" + namafile);
        // membungkus file yang diterima dengan BufferedOutputStream
        BufferedOutputStream bufferfile = new BufferedOutputStream(terimaFile);

        // menuliskan file yang diterima ke folder
        byte[] buffer = new byte[1024];
        int bacaBytes;
        while ((bacaBytes = streamMasukanBuffer.read(buffer)) != -1) {
          bufferfile.write(buffer, 0, bacaBytes);
        }
        bufferfile.flush();

        // menutup koneksi dan socket client
        streamMasukanBuffer.close();
        bufferfile.close();
        socketpengguna.close();

        System.out.println("Pengiriman file berhasil");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}