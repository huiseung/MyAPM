package org.example.agent.config;


import java.util.List;

public class AgentConfig {
    private Agent agent;
    private Collector collector;

    // Getters and Setters
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    @Override
    public String toString() {
        return "AgentConfig{" +
                "agent=" + agent +
                ", collector=" + collector +
                '}';
    }

    // inner class Agent
    public static class Agent {
        private String appId;
        private List<String> plugin;
        private Metric metric;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public List<String> getPlugin() {
            return plugin;
        }

        public void setPlugin(List<String> plugin) {
            this.plugin = plugin;
        }

        public Metric getMetric() {
            return metric;
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }

        @Override
        public String toString() {
            return "Agent{" +
                    "appId='" + appId + '\'' +
                    ", metric=" + metric +
                    '}';
        }
    }

    public static class Metric{
        private int measureInterval;

        public int getMeasureInterval() {
            return measureInterval;
        }

        public void setMeasureInterval(int measureInterval) {
            this.measureInterval = measureInterval;
        }

        @Override
        public String toString() {
            return "Metric{" +
                    "measureInterval=" + measureInterval +
                    '}';
        }
    }

    // inner class Collector
    public static class Collector {
        private String address;
        private int port;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        public String toString() {
            return "Collector{" +
                    "address='" + address + '\'' +
                    ", port=" + port +
                    '}';
        }
    }
}
