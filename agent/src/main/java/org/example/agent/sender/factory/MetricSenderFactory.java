package org.example.agent.sender.factory;

import org.example.agent.config.AgentConfig;
import org.example.agent.sender.BufferMetricSender;
import org.example.agent.sender.Sender;
import org.example.agent.sender.buffer.LockBuffer;
import org.example.agent.sender.transport.SoutTransport;
import org.example.common.message.Metric;

public class MetricSenderFactory {
    private static Sender<Metric> sender;

    public static void init(AgentConfig agentConfig) {
        sender = new BufferMetricSender(new LockBuffer<Metric>(), new SoutTransport<Metric>());
    }

    public static Sender<Metric> getSender() {
        return sender;
    }
}
