package org.example.collector.storage;

import java.net.InetSocketAddress;
import org.example.collector.message.Metric;
import org.springframework.stereotype.Component;

@Component
public class CollectorStorage {

    public void saveMetric(InetSocketAddress sender, Metric metric) {
        System.out.println("Received from " + sender + ": " + metric);
    }
}
