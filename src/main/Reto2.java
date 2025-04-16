import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reto2 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String opcion;

        do {

            System.out.println("Ingrese un anime (espacio para salir): ");
            opcion = sc.nextLine().trim();

            if (opcion.isEmpty()) {
                System.out.println("Adiós! (• ◡•)");
                break;
            }

            List<Anime.Data> resultados = buscarAnime(opcion);
            if (resultados.isEmpty()) {
                System.out.println("El anime ingresado no existe ( •͡˘ _•͡˘)");
                continue;
            }

            for (int i = 0; i < resultados.size(); i++) {
                System.out.printf("[%d] %s (ID: %d)%n", i + 1, resultados.get(i).title, resultados.get(i).mal_id);
            }

            System.out.print("Seleccione el número del anime que desea ver: ");
            int seleccion;
            try {
                seleccion = Integer.parseInt(sc.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            if (seleccion < 0 || seleccion >= resultados.size()) {
                System.out.println("Selección fuera de rango.");
                continue;
            }

            Anime.Data seleccionado = resultados.get(seleccion);
            System.out.println();
            System.out.println("=== Detalles del Anime ===");
            System.out.println(seleccionado.toString());
            System.out.println();

            System.out.println("Visualizar el anime seleccionado? (S/N): ");
            opcion = sc.nextLine();

            if (opcion.equalsIgnoreCase("s")) {
                System.out.println("Abriendo en web... ⊂(◉‿◉)⊂");
                mostrarEnWeb(seleccionado);
            }

        } while (true);
    }

    private static List<Anime.Data> buscarAnime(String nombre) throws IOException {
        String endpoint = "https://api.jikan.moe/v4/anime?q=" +
                URLEncoder.encode(nombre, StandardCharsets.UTF_8) + "&limit=10";


        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        con.disconnect();

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("data");

        List<Anime.Data> lista = new ArrayList<>();
        for (JsonElement e : results) {
            Anime.Data anime = gson.fromJson(e, Anime.Data.class);
            lista.add(anime);
        }
        return lista;

    }

    private static void mostrarEnWeb(Anime.Data anime) throws IOException {
        String html = "<!DOCTYPE html>\n" +
                "<html lang='es'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>" + anime.title + "</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }\n" +
                "        .card { background: white; padding: 20px; max-width: 800px; margin: auto; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }\n" +
                "        img { max-width: 100%; height: auto; border-radius: 10px; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class='card'>\n" +
                "    <h1>" + anime.title + "</h1>\n" +
                (anime.title_english != null ? "<h2>" + anime.title_english + "</h2>\n" : "") +
                "    <img src='" + anime.images.jpg.large_image_url + "' alt='Imagen del anime'>\n" +
                "    <p><strong>Sinopsis:</strong> " + anime.synopsis + "</p>\n" +
                "    <p><strong>Tipo:</strong> " + anime.type + "</p>\n" +
                "    <p><strong>Fuente:</strong> " + anime.source + "</p>\n" +
                "    <p><strong>Episodios:</strong> " + anime.episodes + "</p>\n" +
                "    <p><strong>Estado:</strong> " + anime.status + "</p>\n" +
                "    <p><strong>Puntuación:</strong> " + anime.score + " / 10</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        // Escribir el HTML a un archivo
        String fileName = "anime_" + anime.mal_id + ".html";
        java.nio.file.Files.write(java.nio.file.Paths.get(fileName), html.getBytes());

        // Abrir el archivo en el navegador
        java.awt.Desktop.getDesktop().browse(new java.io.File(fileName).toURI());
    }
}

class Anime {
    public Data data;

    public static class Data {
        public int mal_id;
        public String url;
        public Images images;
        public Trailer trailer;
        public boolean approved;
        public List<Title> titles;
        public String title;
        public String title_english;
        public String title_japanese;
        public List<String> title_synonyms;
        public String type;
        public String source;
        public int episodes;
        public String status;
        public boolean airing;
        public Aired aired;
        public String duration;
        public String rating;
        public double score;
        public int scored_by;
        public int rank;
        public int popularity;
        public int members;
        public int favorites;
        public String synopsis;
        public String background;
        public String season;
        public int year;
        public Broadcast broadcast;
        public List<Producer> producers;
        public List<Producer> licensors;
        public List<Producer> studios;
        public List<Genre> genres;
        public List<Genre> explicit_genres;
        public List<Genre> themes;
        public List<Genre> demographics;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("📺 Anime: ").append(title).append("\n");
            if (title_english != null && !title_english.equals(title))
                sb.append("🇬🇧 Título en inglés: ").append(title_english).append("\n");
            sb.append("🆔 ID: ").append(mal_id).append("\n");
            sb.append("🎭 Tipo: ").append(type).append(" | Fuente: ").append(source).append("\n");
            sb.append("🎬 Episodios: ").append(episodes).append("\n");
            sb.append("📅 Emitido: ").append(aired.string).append("\n");
            sb.append("📡 Estado: ").append(status).append("\n");
            sb.append("⭐ Puntuación: ").append(score)
                    .append(" (por ").append(scored_by).append(" usuarios)").append("\n");
            sb.append("🏆 Ranking: #").append(rank)
                    .append(" | Popularidad: #").append(popularity).append("\n");
            sb.append("👥 Miembros: ").append(members).append(" | Favoritos: ").append(favorites).append("\n");

            if (genres != null && !genres.isEmpty()) {
                sb.append("🎨 Géneros: ");
                for (int i = 0; i < genres.size(); i++) {
                    sb.append(genres.get(i).name);
                    if (i < genres.size() - 1) sb.append(", ");
                }
                sb.append("\n");
            }

            if (themes != null && !themes.isEmpty()) {
                sb.append("🎯 Temas: ");
                for (int i = 0; i < themes.size(); i++) {
                    sb.append(themes.get(i).name);
                    if (i < themes.size() - 1) sb.append(", ");
                }
                sb.append("\n");
            }

            sb.append("\n📝 Sinopsis:\n").append(synopsis).append("\n");
            return sb.toString();
        }
    }

    public static class Images {
        public Format jpg;
        public Format webp;

        public static class Format {
            public String image_url;
            public String small_image_url;
            public String large_image_url;
        }
    }

    public static class Trailer {
        public String youtube_id;
        public String url;
        public String embed_url;
        public TrailerImages images;

        public static class TrailerImages {
            public String image_url;
            public String small_image_url;
            public String medium_image_url;
            public String large_image_url;
            public String maximum_image_url;
        }
    }

    public static class Title {
        public String type;
        public String title;
    }

    public static class Aired {
        public String from;
        public String to;
        public Prop prop;
        public String string;

        public static class Prop {
            public DateInfo from;
            public DateInfo to;

            public static class DateInfo {
                public int day;
                public int month;
                public int year;
            }
        }
    }

    public static class Broadcast {
        public String day;
        public String time;
        public String timezone;
        public String string;
    }

    public static class Producer {
        public int mal_id;
        public String type;
        public String name;
        public String url;
    }

    public static class Genre {
        public int mal_id;
        public String type;
        public String name;
        public String url;
    }
}
