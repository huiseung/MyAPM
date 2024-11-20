package org.example.collector.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.List;
import org.example.collector.storage.CollectorStorage;
import org.example.collector.message.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectorNetwork implements Runnable {
    private final CollectorStorage collectorStorage;
    private final ObjectMapper objectMapper;
    private volatile boolean running = true;

    @Autowired
    public CollectorNetwork(CollectorStorage collectorStorage, ObjectMapper objectMapper) {
        this.collectorStorage = collectorStorage;
        this.objectMapper = objectMapper;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(9999));
            channel.configureBlocking(false);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("Listening on port 9999...");

            ByteBuffer buffer = ByteBuffer.allocate(65507);

            while (running) {
                try {
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();

                        if (key.isReadable()) {
                            DatagramChannel datagramChannel = (DatagramChannel) key.channel();
                            buffer.clear();
                            InetSocketAddress sender = (InetSocketAddress) datagramChannel.receive(buffer);

                            if (sender != null) {
                                buffer.flip();
                                byte[] jsonBytes = new byte[buffer.limit()];
                                buffer.get(jsonBytes);

                                try {
                                    String json = new String(jsonBytes, "UTF-8");
                                    List<Metric> metrics = objectMapper.readValue(json, new TypeReference<List<Metric>>() {});
                                    collectorStorage.saveMetric(sender, metrics);
                                } catch (Exception e) {
                                    System.err.println("Failed to process JSON: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Selector error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
