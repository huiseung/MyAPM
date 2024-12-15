package org.example.agent.sender;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.example.agent.sender.buffer.Buffer;
import org.example.agent.sender.transport.Transport;
import org.example.common.context.Span;

public class BufferSpanSender implements Sender<Span>{
    private final Buffer<Span> buffer;
    private final int bufferSize;
    private final float sampleRate;

    private final Transport<Span> transport;

    private final ScheduledExecutorService scheduler;
    private final int scheduleInterval;

    private final Random random;

    public BufferSpanSender(Buffer<Span> buffer, int bufferSize, float sampleRate, int scheduleInterval, Transport<Span> transport) {
        this.buffer = buffer;
        this.bufferSize = bufferSize;
        this.sampleRate = sampleRate;

        this.transport = transport;

        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.scheduleInterval = scheduleInterval;
        this.scheduler.scheduleAtFixedRate(this::flush, 0, this.scheduleInterval, TimeUnit.SECONDS);

        this.random = new Random();
    }

    public BufferSpanSender(Buffer<Span> buffer, Transport<Span> transport) {
        this(buffer, 10, 0.5f, 1, transport);
    }


    @Override
    public void send(Span metric) {
        if(isSample()){
            int size = buffer.addAndGetSize(metric);
            if(size >= bufferSize) {
                flush();
            }
        }
    }

    private boolean isSample(){
        return random.nextFloat() < sampleRate;
    }

    private void flush() {
        transport.sendBuffer(buffer.flush());
    }
}
