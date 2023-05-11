import java.io.*;
import java.net.*;

public class Client {
  public final static int REMOTE_PORT = 424;

  public static void main(String args[]) throws Exception {
    Socket cl = null;
    BufferedReader is = null;
    DataOutputStream os = null;
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    String userInput = null;
    String output = null;
    boolean stop = false;
    try {
      cl = new Socket("localhost", REMOTE_PORT);
      is = new BufferedReader(new InputStreamReader(cl.getInputStream()));
      os = new DataOutputStream(cl.getOutputStream());
      System.out.println("Koneksi ke server berhasil!");
    } catch (UnknownHostException el) {
      System.out.println("Gagal Mengakses Host");
      stop = true;
    } catch (IOException e2) {
      System.out.println("Gagal Mengakses Host");
      stop = true;
    }

    if (stop == true) {
      System.exit(0);
    }

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

    try {
      is.close();
      os.close();
      cl.close();
    } catch (IOException x) {
      System.out.println("Gagal Menutup Koneksi" + x);
    }
  }
}
