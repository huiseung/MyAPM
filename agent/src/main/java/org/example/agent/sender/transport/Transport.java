package org.example.agent.sender.transport;

import java.util.List;

public interface Transport<T> {
    void send(T data);
    void sendBuffer(List<T> data);
}
