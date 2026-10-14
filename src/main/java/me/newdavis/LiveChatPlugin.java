package me.newdavis;

import me.newdavis.configuration.LiveChatConfiguration;
import me.newdavis.controller.AuthenticationController;
import me.newdavis.controller.FetchMessagesController;
import me.newdavis.controller.SendMessageController;
import me.newdavis.listener.AuthenticationListener;
import me.newdavis.util.jwks.JWKSManager;
import me.newdavis.websocket.exception.configuration.InvalidFileExtensionException;
import me.newdavis.websocket.plugin.Plugin;
import me.newdavis.websocket.server.Server;

import java.io.IOException;

public class LiveChatPlugin extends Plugin {

    private static LiveChatConfiguration configuration;

    @Override
    public void start() {
        try {
            configuration = new LiveChatConfiguration();

            configuration.load();
        } catch (InvalidFileExtensionException | IOException e) {
            throw new RuntimeException(e);
        }

        JWKSManager.fetchJWKS();

        Server.getRouter().registerRoutes(new FetchMessagesController());
        Server.getRouter().registerRoutes(new SendMessageController());
        Server.getRouter().registerRoutes(new AuthenticationController());

        Server.getEventManager().registerListener(new AuthenticationListener());
    }

    @Override
    public void stop() {

    }

    public static LiveChatConfiguration getConfiguration() {
        return configuration;
    }
}
