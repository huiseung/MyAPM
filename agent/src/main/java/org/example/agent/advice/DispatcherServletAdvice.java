package org.example.agent.advice;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import net.bytebuddy.asm.Advice;
import org.example.agent.sender.Sender;
import org.example.agent.sender.factory.MetricSenderFactory;
import org.example.common.context.Span;
import org.example.common.context.TraceContext;
import org.example.common.message.Metric;

public class DispatcherServletAdvice {
    @Advice.OnMethodEnter
    public static long onEnter(@Advice.Origin Method method) {
        long startTime = System.currentTimeMillis();
        TraceContext.startSpan(method.getName(), startTime);
        return startTime;
    }

    // @Advice.Thrown 을 파라미터로 쓰려면 onThrowable = Throwable.class 추가
    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void onExit(@Advice.Enter long startTime,
                              @Advice.Thrown Throwable throwable,
                              @Advice.Argument(0) Object request
    ) {
        long endTime = System.currentTimeMillis();
        Span span = TraceContext.endSpan(endTime);
        System.out.println(span);
    }
}
