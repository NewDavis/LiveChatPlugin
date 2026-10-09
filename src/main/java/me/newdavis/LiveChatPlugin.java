package me.newdavis;

import me.newdavis.controller.FetchMessagesController;
import me.newdavis.controller.SendMessageController;
import me.newdavis.websocket.plugin.Plugin;
import me.newdavis.websocket.server.Server;

public class LiveChatPlugin extends Plugin {

    @Override
    public void start() {
        Server.getRouter().registerRoutes(new FetchMessagesController());
        Server.getRouter().registerRoutes(new SendMessageController());
    }

    @Override
    public void stop() {

    }

}
