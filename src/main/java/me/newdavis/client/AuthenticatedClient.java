package me.newdavis.client;

import com.auth0.jwt.interfaces.DecodedJWT;
import me.newdavis.LiveChatPlugin;
import me.newdavis.websocket.client.Client;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class AuthenticatedClient {

    public static HashMap<Client, AuthenticatedClient> authenticated = new HashMap<>();

    private Client client;
    private DecodedJWT decodedJWT;
    private JSONObject account;

    public AuthenticatedClient(Client client, DecodedJWT jwt) {
        this.client = client;
        this.decodedJWT = jwt;

        authenticated.put(client, this);
    }

    public void fetchAccountInformation(String accessToken) throws Exception {
        URI url = new URI(LiveChatPlugin.getConfiguration().getString("authenticate-endpoint"));

        HttpRequest request = HttpRequest.newBuilder(url)
                .setHeader("Authorization", "Bearer " + accessToken)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());
        if (jsonResponse.getInt("status") != 200) {
            throw new Exception("Could not retrieve account information");
        }

        this.account = jsonResponse.getJSONObject("data").getJSONObject("account");
    }

    public Client getClient() {
        return client;
    }

    public DecodedJWT getDecodedJWT() {
        return decodedJWT;
    }

    public JSONObject getAccount() {
        return account;
    }
}
