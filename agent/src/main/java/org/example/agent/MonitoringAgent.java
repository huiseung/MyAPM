package org.example.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.Map;
import org.example.agent.config.AgentConfig;
import org.example.agent.plugin.PluginRegistry;
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
        if(agentConfig == null) {
            return;
        }
        System.out.println(agentConfig);
        setPlugin(agentConfig, inst);
    }

    private static AgentConfig readYaml(String args) {
        try {
            File configFile = new File(args);
            InputStream inputStream = new FileInputStream(configFile);
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream, AgentConfig.class);
        }catch (Exception e) {
            Log.log("fail read config file");
            return null;
        }
    }

    private static String getConfigValue(Map<String, Object> config, String section, String key) {
        Map<String, Object> sectionMap = (Map<String, Object>) config.get(section);
        if (sectionMap == null || !sectionMap.containsKey(key)) {
            Log.log("Error: Missing configuration value for [" + section + "." + key + "]");
            return null;
        }
        return sectionMap.get(key).toString();
    }

    private static void setPlugin(AgentConfig agentConfig, Instrumentation inst) {
        PluginRegistry pluginRegistry = new PluginRegistry();
        // register

        // setup
        pluginRegistry.setupAll(inst);
    }
}
