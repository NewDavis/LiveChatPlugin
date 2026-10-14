package me.newdavis.configuration;

import me.newdavis.websocket.configuration.WebSocketFile;
import me.newdavis.websocket.exception.configuration.InvalidFileExtensionException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class LiveChatConfiguration extends WebSocketFile {

    @Override
    public File getFile() {
        return new File("configuration/live-chat.ws");
    }

    @Override
    public LinkedHashMap<String, Object> getDefaultValues() {
        return new LinkedHashMap<>(){{
            put("jwks-endpoint", "http://host.docker.internal:8087/auth/.well-known/jwks");
            put("authenticate-endpoint", "http://host.docker.internal:8087/v2/account/authenticate");
        }};
    }

    public LiveChatConfiguration() throws InvalidFileExtensionException, IOException {
        super(true);
    }

}
