import java.util.Scanner;

public class tes {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {

      // Meminta input string dari pengguna
      System.out.print("Masukkan string yang akan dienkripsi: ");
      String inputString = scanner.nextLine();

      // Enkripsi string
      String encryptedString = encryptString(inputString);
      System.out.println("Hasil enkripsi: " + encryptedString);

      // Dekripsi string
      String decryptedString = decryptString(encryptedString);
      System.out.println("Hasil dekripsi: " + decryptedString);
      scanner.close();
    }
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
