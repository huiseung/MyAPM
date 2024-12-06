package org.example.agent.config;

public class AgentConfig {
    private final String appId;
    private final String collectorServerAddress;
    private final int collectorServerPort;

    public AgentConfig(String appId, String collectorServerAddress, int collectorServerPort) {
        this.appId = appId;
        this.collectorServerAddress = collectorServerAddress;
        this.collectorServerPort = collectorServerPort;
    }

    public String getAppId() {
        return appId;
    }

    public String getCollectorServerIp() {
        return collectorServerAddress;
    }

    public int getCollectorServerPort() {
        return collectorServerPort;
    }

    @Override
    public String toString() {
        return "AgentConfig{" +
                "appId='" + appId + '\'' +
                ", collectorServerAddress='" + collectorServerAddress + '\'' +
                ", collectorServerPort=" + collectorServerPort +
                '}';
    }
}
