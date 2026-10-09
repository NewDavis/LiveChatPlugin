package me.newdavis.livechat;

import me.newdavis.websocket.server.Server;

import java.util.ArrayList;
import java.util.List;

public class LiveChatManager {

    private static List<Message> messages = new ArrayList<>();

    public static void addMessage(Message message) {
        messages.add(message);

        Server.getServer().broadcastMessage(message.toJson().toString());
    }

    public static List<Message> getMessages() {
        return messages;
    }

}
