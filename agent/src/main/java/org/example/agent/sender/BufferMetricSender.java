package org.example.agent.sender;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.example.agent.sender.buffer.Buffer;
import org.example.agent.sender.transport.Transport;
import org.example.common.message.Metric;

public class BufferMetricSender implements Sender<Metric> {
    private final Buffer<Metric> buffer;
    private final int bufferSize;
    private final float sampleRate;

    private final Transport<Metric> transport;

    private final ScheduledExecutorService scheduler;
    private final int scheduleInterval;

    private final Random random;

    public BufferMetricSender(Buffer<Metric> buffer, int bufferSize, float sampleRate, int scheduleInterval, Transport<Metric> transport) {
        this.buffer = buffer;
        this.bufferSize = bufferSize;
        this.sampleRate = sampleRate;

        this.transport = transport;

        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.scheduleInterval = scheduleInterval;
        this.scheduler.scheduleAtFixedRate(this::flush, 0, this.scheduleInterval, TimeUnit.SECONDS);

        this.random = new Random();
    }

    public BufferMetricSender(Buffer<Metric> buffer, Transport<Metric> transport) {
        this(buffer, 10, 0.5f, 1, transport);
    }


    @Override
    public void send(Metric metric) {
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
