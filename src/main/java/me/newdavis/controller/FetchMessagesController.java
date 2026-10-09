package me.newdavis.controller;

import me.newdavis.livechat.LiveChatManager;
import me.newdavis.livechat.Message;
import me.newdavis.websocket.route.Controller;
import me.newdavis.websocket.route.Route;
import me.newdavis.websocket.route.request.Request;
import me.newdavis.websocket.route.request.RequestStatus;
import org.json.JSONArray;

public class FetchMessagesController implements Controller {

    @Route(route = "/api/fetch/previous-messages/")
    public void fetch(Request request) {
        JSONArray messages = new JSONArray();

        for (Message message : LiveChatManager.getMessages()) {
            messages.put(message.toJson());
        }

        request.addResponseData(messages.toString());
        request.setResult(RequestStatus.OK);
    }

}
