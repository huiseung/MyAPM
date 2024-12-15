package org.example.common.context;


import java.util.ArrayList;
import java.util.List;

public class Span {
    private final String methodName;
    private final long startTime;
    private long endTime;
    private long duration;
    private final List<Span> childs = new ArrayList<Span>();

    public Span(String methodName, long startTime) {
        this.methodName = methodName;
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long durationNano) {
        this.duration = durationNano;
    }

    public void addChild(Span child) {
        childs.add(child);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Span{methodName='").append(methodName).append('\'')
                .append(", startTime=").append(startTime)
                .append(", endTime=").append(endTime)
                .append(", duration=").append(duration).append(" ms")
                .append(", childs=").append(childs)
                .append('}');
        return sb.toString();
    }
}
