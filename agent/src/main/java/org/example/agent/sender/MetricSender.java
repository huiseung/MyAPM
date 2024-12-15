package org.example.agent.sender;

import org.example.agent.sender.transport.Transport;
import org.example.common.message.Metric;

public class MetricSender implements Sender<Metric> {
    private final Transport<Metric> transport;

    public MetricSender(Transport<Metric> transport) {
        this.transport = transport;
    }


    @Override
    public void send(Metric metric) {
        transport.send(metric);
    }
}
