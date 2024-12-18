package org.example.agent.advice;


import java.lang.reflect.Method;
import net.bytebuddy.asm.Advice;
import org.example.agent.sender.Sender;
import org.example.agent.sender.factory.SpanSenderFactory;
import org.example.common.context.Span;
import org.example.common.context.TraceContext;

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
        Sender<Span> sender = SpanSenderFactory.getSender();
        sender.send(span);
    }
}
