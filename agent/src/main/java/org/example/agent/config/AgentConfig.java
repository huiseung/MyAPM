package org.example.agent.config;


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
    protected static class Agent {
        private String appId;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        @Override
        public String toString() {
            return "Agent{" +
                    "appId='" + appId + '\'' +
                    '}';
        }
    }

    // inner class Collector
    protected static class Collector {
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
