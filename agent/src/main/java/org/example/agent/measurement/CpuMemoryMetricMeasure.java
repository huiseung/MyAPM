package org.example.agent.measurement;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import org.example.agent.sender.Sender;
import org.example.agent.sender.factory.MetricSenderFactory;
import org.example.common.message.Metric;

public class CpuMemoryMetricMeasure {
    private static final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private static final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

    public static void measureMetric(){
        double processCpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;
        double systemCpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad() * 100;

        long usedHeapMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxHeapMemory = memoryBean.getHeapMemoryUsage().getMax();

        Sender<Metric> sender = MetricSenderFactory.getSender();
        sender.send(new Metric(usedHeapMemory, maxHeapMemory, processCpuLoad, systemCpuLoad));
    }
}
