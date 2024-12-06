package org.example.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.Map;
import org.example.agent.config.AgentConfig;
import org.example.agent.util.Log;
import org.yaml.snakeyaml.Yaml;

public class MonitoringAgent {

    public static void premain(String args, Instrumentation inst) {
        if(args.isEmpty()){
            Log.log("please args. ex) -javaagent:path/agent.jar=config.yml");
        }
        Log.log("Agent is starting...");
        try{
            File configFile = new File(args);
            if(!configFile.exists()){
                Log.log(String.format("not file %s", args));
                return;
            }
            AgentConfig agentConfig = readYaml(configFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static AgentConfig readYaml(File configFile) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(configFile);
        Yaml yaml = new Yaml();
        Map<String, Object> config = yaml.load(inputStream);
        try{
            Map<String, Object> agentConfig = (Map<String, Object>) config.get("agent");
            String appId = agentConfig.get("appId").toString();
            Map<String, Object> collectorConfig = (Map<String, Object>) config.get("collector");
            String collectorServerAddress = collectorConfig.get("address").toString();
            int collectorServerPort = (int) collectorConfig.get("port");
            return new AgentConfig(appId, collectorServerAddress, collectorServerPort);
        }catch(Exception e){
            Log.log("failed to read yml file");
            throw e;
        }
    }
}
