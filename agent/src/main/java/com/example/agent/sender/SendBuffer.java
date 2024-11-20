package com.example.agent.sender;

import com.example.agent.message.Metric;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class SendBuffer {
    private final ObjectMapper objectMapper;
    private final List<Metric> buffer;
    private final ReentrantLock lock;
    private static final int MAX_PACKET_SIZE = 65507;

    public SendBuffer() {
        this.lock = new ReentrantLock();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        buffer = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        lock.lock();
        try {
            buffer.add(metric);
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        lock.lock();
        try {
            return buffer.size();
        } finally {
            lock.unlock();
        }
    }


    public void flush() {
        lock.lock();
        try {
            if (buffer.isEmpty()) {
                return;
            }

            List<Metric> metricsToSend = new ArrayList<>(buffer);
            buffer.clear();

            String json = objectMapper.writeValueAsString(metricsToSend);
            byte[] data = json.getBytes();

            if (data.length > MAX_PACKET_SIZE) {
                System.err.println("데이터 크기가 UDP 패킷의 최대 크기를 초과합니다.");
                return;
            }

            InetAddress address = InetAddress.getByName("127.0.0.1"); // UDP 수신 서버 주소
            try (DatagramSocket socket = new DatagramSocket()) {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, 9999);
                socket.send(packet);
                System.out.println("메트릭 전송 완료");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
