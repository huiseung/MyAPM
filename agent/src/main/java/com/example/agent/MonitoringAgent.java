package com.example.agent;

import com.example.agent.plugin.DispatcherServletMonitoring;
import com.example.agent.plugin.PluginRegistry;
import com.example.agent.sender.Sender;
import java.lang.instrument.Instrumentation;

public class MonitoringAgent {
    public static void premain(String args, Instrumentation inst) {
        System.out.println("Starting Monitoring Agent...");

        PluginRegistry pluginRegistry = new PluginRegistry();
        pluginRegistry.register(new DispatcherServletMonitoring());
        pluginRegistry.setupAll(inst);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down sender...");
            Sender.getInstance().shutdown();
        }));
    }
}
