package org.example.agent.sender.transport;


public class SoutTransport<T> implements Transport<T> {
    @Override
    public void send(T data) {
        System.out.println(data);
    }
}
