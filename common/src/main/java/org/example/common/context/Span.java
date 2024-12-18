package org.example.common.context;


import java.util.ArrayList;
import java.util.List;

public class Span {
    private String methodName;
    private long startTime;
    private long endTime;
    private long duration;
    private final List<Span> childs = new ArrayList<Span>();

    public Span() {
    }

    public Span(String methodName, long startTime) {
        this.methodName = methodName;
        this.startTime = startTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }

    public List<Span> getChilds() {
        return childs;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
                .append(", \nchilds=").append(childs)
                .append('}');
        return sb.toString();
    }
}
