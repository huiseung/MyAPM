package org.example.agent.sender.transport;

import java.util.List;

public class SoutTransport<T> implements Transport<T> {
    @Override
    public void send(T data) {
        System.out.println(data);
    }

    @Override
    public void sendBuffer(List<T> data) {
        System.out.println(data);
    }
}
