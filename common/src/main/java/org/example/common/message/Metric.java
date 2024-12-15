package org.example.common.message;

public class Metric {
    private final long time;
    private final long useHeapMemory;
    private final long maxHeapMemory;
    private final double processCpuLoad;
    private final double systemCpuLoad;

    public Metric(long useHeapMemory, long maxHeapMemory, double processCpuLoad, double systemCpuLoad) {
        this.time = System.currentTimeMillis();
        this.useHeapMemory = useHeapMemory;
        this.maxHeapMemory = maxHeapMemory;
        this.processCpuLoad = processCpuLoad;
        this.systemCpuLoad = systemCpuLoad;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "time=" + time +
                ", useHeapMemory=" + useHeapMemory +
                ", maxHeapMemory=" + maxHeapMemory +
                ", processCpuLoad=" + processCpuLoad +
                ", systemCpuLoad=" + systemCpuLoad +
                '}';
    }
}
