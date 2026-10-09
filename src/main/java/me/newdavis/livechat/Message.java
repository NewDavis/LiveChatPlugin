package me.newdavis.livechat;

import org.json.JSONObject;

public class Message {

    private String author;
    private String message;
    private String date;

    public Message(String author, String message, String date) {
        this.author = author;
        this.message = message;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("author", author);
        jsonObject.put("message", message);
        jsonObject.put("date", date);

        return jsonObject;
    }
}
