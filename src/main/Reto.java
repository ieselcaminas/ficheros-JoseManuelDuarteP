import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Reto {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("Ingrese un Pokemon (ingrese vacío para salir): ");
            opcion = sc.nextLine();
            String endpoint = "https://pokeapi.co/api/v2/pokemon/" + opcion;

            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 404) {
                System.out.println("No se encontro el Pokemon (ㆆ _ ㆆ)");
                continue;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            Gson gson = new Gson();
            Pokemon pokemon = gson.fromJson(response.toString(), Pokemon.class);

            if (opcion.equalsIgnoreCase("")) {
                break;
            }

            System.out.println(pokemon);
        } while (true);
    }
}
class Pokemon {
    public int id;
    public String name;
    public int base_experience;
    public int height;
    public boolean is_default;
    public int order;
    public int weight;
    public List<AbilityInfo> abilities;
    public List<Form> forms;
    public List<GameIndex> game_indices;
    public Species species;
    public List<StatInfo> stats;
    public List<TypeInfo> types;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Pokémon ===\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nombre: ").append(name).append("\n");
        sb.append("Altura: ").append(height).append("\n");
        sb.append("Peso: ").append(weight).append("\n");
        sb.append("Base Exp: ").append(base_experience).append("\n");
        sb.append("Orden: ").append(order).append("\n");
        sb.append("Default: ").append(is_default).append("\n");

        sb.append("\n--- Tipos ---\n");
        for (TypeInfo t : types) {
            sb.append("- ").append(t.type.name).append("\n");
        }

        sb.append("\n--- Habilidades ---\n");
        for (AbilityInfo a : abilities) {
            sb.append("- ").append(a.ability.name);
            if (a.is_hidden) sb.append(" (oculta)");
            sb.append("\n");
        }

        sb.append("\n--- Estadísticas ---\n");
        for (StatInfo s : stats) {
            sb.append("- ").append(s.stat.name)
                    .append(": ").append(s.base_stat)
                    .append(" (Effort: ").append(s.effort).append(")\n");
        }

        return sb.toString();
    }
}

// Subclases

class AbilityInfo {
    public Ability ability;
    public boolean is_hidden;
    public int slot;
}

class Ability {
    public String name;
    public String url;
}

class Form {
    public String name;
    public String url;
}

class GameIndex {
    public int game_index;
    public Version version;
}

class Version {
    public String name;
    public String url;
}

class Species {
    public String name;
    public String url;
}

class StatInfo {
    public int base_stat;
    public int effort;
    public Stat stat;
}

class Stat {
    public String name;
    public String url;
}

class TypeInfo {
    public int slot;
    public Type type;
}

class Type {
    public String name;
    public String url;
}
