package me.newdavis.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import me.newdavis.client.AuthenticatedClient;
import me.newdavis.util.JWT;
import me.newdavis.websocket.client.Client;
import me.newdavis.websocket.route.Controller;
import me.newdavis.websocket.route.Route;
import me.newdavis.websocket.route.request.Request;
import me.newdavis.websocket.route.request.RequestStatus;
import org.json.JSONObject;

public class AuthenticationController implements Controller {

    @Route(route = "/api/authenticate/")
    public void authenticate(Request request) throws Exception {
        if (!request.getHeader().containsKey("Authorization")) {
            request.setResult(RequestStatus.SERVER_ERROR);
            return;
        }

        String accessToken = request.getHeader().get("Authorization").toString().replace("Bearer ", "");

        if (accessToken.isEmpty()) {
            request.setResult(RequestStatus.SERVER_ERROR);
            request.addResponseData(new JSONObject(){{
                put("message", "MISSING_AUTHORIZATION");
            }});
            return;
        }

        JWT jwt = new JWT(accessToken);
        try {
            DecodedJWT decoded = jwt.validate();
            AuthenticatedClient authClient = new AuthenticatedClient(
                (Client)request.getRequester(),
                decoded
            );

            authClient.fetchAccountInformation(accessToken);

            request.addResponseData(new JSONObject(){{
                put("authenticated", authClient.getAccount().getString("username"));
            }});
            request.setResult(RequestStatus.OK);
        } catch (Exception e) {
            request.setResult(RequestStatus.SERVER_ERROR);
            request.addResponseData(new JSONObject(){{
                put("message", e.getMessage());
            }});
        }
    }

}
