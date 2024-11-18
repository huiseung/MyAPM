package org.example.collector;

import jakarta.annotation.PostConstruct;
import org.example.collector.network.CollectorNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectorStarter {
    private final CollectorNetwork collectorNetwork;

    @Autowired
    public CollectorStarter(CollectorNetwork collectorNetwork) {
        this.collectorNetwork = collectorNetwork;
    }

    @PostConstruct
    public void start(){
        Thread thread = new Thread(collectorNetwork);
        thread.start();
    }
}
