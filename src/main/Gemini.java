import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Gemini {

    // CAMBIA AQUÍ: pega tu clave OpenRouter
    private static final String API_KEY = "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";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca pregunta:");
        String prompt = sc.nextLine();

        try {
            String response = generateText(prompt);
            System.out.println("Respuesta del modelo:\n" + response);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al llamar a OpenRouter: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String generateText(String prompt) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        JSONObject message = new JSONObject()
                .put("role", "user")
                .put("content", prompt);

        JSONObject json = new JSONObject()
                .put("model", "openai/gpt-3.5-turbo") // Puedes cambiar el modelo, por ejemplo: "mistralai/mistral-7b-instruct"
                .put("messages", new JSONArray().put(message));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("HTTP-Referer", "https://tu-app.com") // cambia por tu app o "https://localhost"
                .header("X-Title", "MiAppJava")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());

        // Verifica errores
        if (!jsonResponse.has("choices")) {
            throw new RuntimeException("Respuesta inesperada:\n" + jsonResponse.toString(2));
        }

        return jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}
