package org.example.agent.sender.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.example.agent.config.AgentConfig;

public class HttpTransport<T> implements Transport<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AgentConfig agentConfig;

    public HttpTransport(AgentConfig agentConfig){
        this.agentConfig = agentConfig;
    }

    @Override
    public void send(T data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            sendRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRequest(String json) {
        String urlString = "http://" + agentConfig.getCollector().getAddress() + ":" + agentConfig.getCollector().getPort() + "/span";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try(OutputStream outputStream = connection.getOutputStream()){
                outputStream.write(json.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
