package org.example.collector.message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Metric implements Serializable {
    private LocalDateTime startTime;
    private String path;
    private long responseTimeMs;
    private String stackTraceString;

    public Metric() {
    }

    public Metric(String path, long responseTimeMs, StackTraceElement[] stackTrace) {
        this.startTime = LocalDateTime.now();
        this.path = path;
        this.responseTimeMs = responseTimeMs;
        this.stackTraceString = Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .reduce((line1, line2) -> line1 + "\n" + line2) // 각 라인을 줄바꿈으로 연결
                .orElse("No StackTrace");
    }

    @Override
    public String toString() {
        return "Metric{\n" +
                "startTime=" + startTime +
                ", path='" + path + '\'' +
                ", responseTimeMs=" + responseTimeMs +
                ", \nstackTrace='" + stackTraceString + '\'' + "\n"+
                '}';
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getPath() {
        return path;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }

    public String getStackTraceString() {
        return stackTraceString;
    }
}
