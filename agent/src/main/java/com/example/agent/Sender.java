package com.example.agent;

import com.example.agent.message.Metric;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void sendMetric(Metric metric) {
        System.out.println(metric);
        try (DatagramSocket socket = new DatagramSocket();
             ByteArrayOutputStream byteStream = new ByteArrayOutputStream();) {
            // 객체를 직렬화
            String json = objectMapper.writeValueAsString(metric);
            byte[] buffer = json.getBytes();
            InetAddress address = InetAddress.getByName("127.0.0.1"); // UDP 수신 서버 주소
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9999);
            socket.send(packet);
            System.out.println("send metric");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
