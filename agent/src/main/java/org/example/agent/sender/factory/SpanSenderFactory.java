package org.example.agent.sender.factory;

import java.util.List;
import org.example.agent.config.AgentConfig;
import org.example.agent.sender.BufferSpanSender;
import org.example.agent.sender.Sender;
import org.example.agent.sender.buffer.LockBuffer;
import org.example.agent.sender.transport.HttpTransport;
import org.example.agent.sender.transport.SoutTransport;
import org.example.common.context.Span;

public class SpanSenderFactory {
    private static Sender<Span> sender;

    public static void init(AgentConfig agentConfig) {
        //sender = new BufferSpanSender(new LockBuffer<Span>(), new SoutTransport<List<Span>>());
        sender = new BufferSpanSender(new LockBuffer<Span>(), new HttpTransport<List<Span>>(agentConfig));
    }

    public static Sender<Span> getSender() {
        return sender;
    }
}
