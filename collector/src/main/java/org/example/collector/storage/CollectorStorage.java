package org.example.collector.storage;

import java.net.InetSocketAddress;
import java.util.List;
import org.example.collector.message.Metric;
import org.springframework.stereotype.Component;

@Component
public class CollectorStorage {

    public void saveMetric(InetSocketAddress sender, List<Metric> metrics) {
        System.out.println("Received from " + sender + ": " + metrics);
    }
}
