import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Gemini {

    // Pega aquí tu clave tipo "sk-or-v1-..."
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
                .put("model", "openai/gpt-3.5-turbo") // Cambia por otro si lo deseas, como "mistralai/mistral-7b-instruct"
                .put("messages", new JSONArray().put(message));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("HTTP-Referer", "https://localhost")
                .header("X-Title", "MiAppJava")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());

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
