package com.example.agent.sender;

import com.example.agent.message.Metric;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sender {
    private final ScheduledExecutorService scheduler;
    private final SendBuffer sendBuffer;
    private final int BUFFER_SIZE;
    private final long FLUSH_INTERVAL;
    private final double SAMPLING_RATE;
    private final Random random;

    private Sender(int bufferSize, long flushInterval, double samplingRate){
        sendBuffer = new SendBuffer();
        BUFFER_SIZE = bufferSize;
        FLUSH_INTERVAL = flushInterval;
        SAMPLING_RATE = samplingRate;
        random = new Random();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(sendBuffer::flush, 0, flushInterval, TimeUnit.SECONDS);
    }

    private static Sender instance;

    public static Sender getInstance() {
        if (instance == null) {
            synchronized (Sender.class) {
                if (instance == null) {
                    instance = new Sender(5, 5, 0.4); // 적절한 파라미터로 초기화
                }
            }
        }
        return instance;
    }

    public void sendMetric(Metric metric) {
        if(sampling()){
            sendBuffer.addMetric(metric);
            if(sendBuffer.getSize() >= BUFFER_SIZE){
                sendBuffer.flush();
            }
        }
    }

    private boolean sampling(){
        return random.nextDouble() < SAMPLING_RATE;
    }

    public void shutdown() {
        scheduler.shutdown();
        try{
            if(!scheduler.awaitTermination(5, TimeUnit.SECONDS)){
                scheduler.shutdownNow();;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
