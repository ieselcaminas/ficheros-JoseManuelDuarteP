import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Reto2 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String opcion;

        do {

            System.out.println("Ingrese un anime (espacio para salir): ");
            opcion = sc.nextLine();
            String endpoint = "https://api.jikan.moe/v4/anime/" + opcion;

            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");



        } while (true);
    }
}
