import java.io.*;
import java.net.*;

public class Client {

  public static void main(String args[]) throws Exception {
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    int targetport;

    if (args.length > 0 && args[0].equals("pengirim")) {
      try {
        System.out.print("ID Anda: ");
        String str = stdin.readLine();
        targetport = str.codePoints().sum();

        // membuat socket server
        ServerSocket serverSocket = new ServerSocket(targetport);

        // menerima koneksi dari client
        Socket clientSocket = serverSocket.accept();
        System.out.println("Kamu telah terhubung");

        // membaca file yang akan dikirim
        System.out.print("Masukkan nama file beserta ekstensinya: ");
        String filename = stdin.readLine();

        DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
        os.writeBytes(filename); 

        File fileToSend = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(fileToSend);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        OutputStream outputStream = clientSocket.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        // menutup koneksi dan socket server
        bufferedInputStream.close();
        outputStream.close();
        clientSocket.close();
        serverSocket.close();

        System.out.println("Pengiriman file berhasil");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (args.length > 0 && args[0].equals("penerima")) {
      try {
        String poort = null;
        String filename = null;
        String foldername = "folder-penerima";
        Boolean again = false;
        Socket clientSocket = null;
        do {
          try {
            if (again == true) {
              System.out.print("Coba Masukkan Kembali ID: ");
            } else {
              System.out.print("Masukkan ID Teman anda: ");
            }
            poort = stdin.readLine();
            targetport = poort.codePoints().sum();
            clientSocket = new Socket("localhost", targetport);
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
        InputStream inputStream = clientSocket.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        // membuat folder hohohihe jika belum ada
        File folder = new File(foldername);
        if (!folder.exists()) {
          folder.mkdir();
        }

        // menerima nama file
        BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
        filename = is.readLine();

        // menuliskan file yang diterima ke disk
        FileOutputStream fileOutputStream = new FileOutputStream(foldername + "/" + filename);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
          bufferedOutputStream.write(buffer, 0, bytesRead);
        }
        bufferedOutputStream.flush();

        // menutup koneksi dan socket client
        bufferedInputStream.close();
        bufferedOutputStream.close();
        clientSocket.close();

        System.out.println("Pengiriman file berhasil");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}