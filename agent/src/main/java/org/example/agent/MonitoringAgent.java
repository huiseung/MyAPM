package org.example.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.example.agent.config.AgentConfig;
import org.example.agent.measurement.CpuMemoryMetricMeasure;
import org.example.agent.plugin.DispatcherServletMonitoring;
import org.example.agent.plugin.MySQLMonitoring;
import org.example.agent.plugin.PluginRegistry;
import org.example.agent.plugin.SpringMvcMonitoring;
import org.example.agent.sender.factory.MetricSenderFactory;
import org.example.agent.sender.factory.SpanSenderFactory;
import org.example.agent.util.Log;
import org.yaml.snakeyaml.Yaml;

public class MonitoringAgent {
    public static void premain(String args, Instrumentation inst) {
        if (args.isEmpty()) {
            Log.log("Missing configuration file path. ex) -javaagent:path/agent.jar=config.yml");
            return;
        }
        Log.log("Monitoring Agent is starting...");

        AgentConfig agentConfig = readYaml(args);
        if (agentConfig == null) {
            return;
        }
        System.out.println(agentConfig);
        setPlugin(agentConfig, inst);
        SpanSenderFactory.init(agentConfig);
        //
        MetricSenderFactory.init(agentConfig);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(CpuMemoryMetricMeasure::measureMetric, 0,
                agentConfig.getAgent().getMetric().getMeasureInterval(), TimeUnit.SECONDS);
    }

    private static AgentConfig readYaml(String args) {
        try {
            File configFile = new File(args);
            InputStream inputStream = new FileInputStream(configFile);
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream, AgentConfig.class);
        } catch (Exception e) {
            Log.log("fail read config file");
            return null;
        }
    }

    private static void setPlugin(AgentConfig agentConfig, Instrumentation inst) {
        PluginRegistry pluginRegistry = new PluginRegistry();
        MetricSenderFactory.init(agentConfig);
        // register
        pluginRegistry.register(new DispatcherServletMonitoring());
        pluginRegistry.register(new SpringMvcMonitoring());
        for(String plugin : agentConfig.getAgent().getPlugin()){
            if(plugin.equals("mysql")){
                pluginRegistry.register(new MySQLMonitoring());
            }
        }
        // setup
        pluginRegistry.setupAll(inst);
    }
}
