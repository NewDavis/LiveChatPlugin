package me.newdavis.listener;

import me.newdavis.client.AuthenticatedClient;
import me.newdavis.websocket.client.Client;
import me.newdavis.websocket.event.EventListener;
import me.newdavis.websocket.event.Listener;
import me.newdavis.websocket.event.client.messenger.in.ClientMessageReceiveCreateRequestEvent;

public class AuthenticationListener implements Listener {

    @EventListener
    public void onRequest(ClientMessageReceiveCreateRequestEvent event) {
        Client client = (Client) event.getRequest().getRequester();

        if (!client.isHandshakeHandled()) return;
        if (!client.hasVerifiedVersion()) return;
        if (AuthenticatedClient.authenticated.containsKey(client)) return;

        String requestedRoute = event.getRequest().getRoute().route().toLowerCase();

        switch (requestedRoute) {
            case "/api/authenticate/":
            case "/api/fetch/previous-messages/":
                break;
            default:
                event.setCanceled(true);
        }
    }

}
