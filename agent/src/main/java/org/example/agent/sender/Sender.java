package org.example.agent.sender;

public interface Sender<T> {
    void send(T metric);
}
