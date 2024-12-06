package org.example.agent.advice;


import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import net.bytebuddy.asm.Advice;
import org.example.agent.sender.Sender;
import org.example.agent.sender.factory.MetricSenderFactory;
import org.example.common.message.Metric;

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
                e.printStackTrace();
                path = "unknown";
            }
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (throwable != null) {
            stackTrace = throwable.getStackTrace();
        }

        Metric metric = new Metric();
        Sender<Metric> sender = MetricSenderFactory.getSender();
        sender.send(metric);
    }
}
