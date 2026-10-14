package me.newdavis.listener;

import me.newdavis.client.AuthenticatedClient;
import me.newdavis.websocket.event.EventListener;
import me.newdavis.websocket.event.Listener;
import me.newdavis.websocket.event.client.messenger.in.ClientMessageReceiveCreateRequestEvent;

public class AuthenticationListener implements Listener {

    @EventListener
    public void onRequest(ClientMessageReceiveCreateRequestEvent event) {
        if (!event.getClient().isHandshakeHandled()) return;
        if (!event.getClient().hasVerifiedVersion()) return;
        if (AuthenticatedClient.authenticated.containsKey(event.getClient())) return;

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
