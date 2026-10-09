package me.newdavis.controller;

import me.newdavis.livechat.LiveChatManager;
import me.newdavis.livechat.Message;
import me.newdavis.websocket.route.Controller;
import me.newdavis.websocket.route.Route;
import me.newdavis.websocket.route.request.Request;
import me.newdavis.websocket.route.request.RequestStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SendMessageController implements Controller {

    @Route(route = "/api/send-message/")
    public void send(Request request) {
        String author = (String) request.getBody().get("author");
        String content = (String) request.getBody().get("message");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        Message message = new Message(author, content, date);

        LiveChatManager.addMessage(message);

        request.setResult(RequestStatus.OK);
    }

}
