package com.example.agent.advice;

import com.example.agent.Sender;
import com.example.agent.message.Metric;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import net.bytebuddy.asm.Advice;

public class DispatcherServletAdvice {

    @Advice.OnMethodEnter
    public static long onEnter() {
        return System.nanoTime();
    }

    // @Advice.Thrown 을 파라미터로 쓰려면 onThrowable = Throwable.class 추가
    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void onExit(@Advice.Enter long startTime,
                              @Advice.Thrown Throwable throwable,
                              @Advice.Argument(0) Object request
    ) {
        long duration = System.nanoTime() - startTime;
        duration = TimeUnit.NANOSECONDS.toMillis(duration);

        String path = null;
        if (request != null && "org.apache.catalina.connector.RequestFacade".equals(request.getClass().getName())) {
            try {
                Object uri = request.getClass().getMethod("getRequestURI").invoke(request);
                path = uri != null ? uri.toString() : null;
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (throwable != null) {
            stackTrace = throwable.getStackTrace();
        }

        Metric metric = new Metric(path, duration, stackTrace);
        Sender.sendMetric(metric);
    }
}
