package org.example.agent.sender.buffer;

import java.util.List;

public interface Buffer<T> {
    int addAndGetSize(T data);
    List<T> flush();
}
