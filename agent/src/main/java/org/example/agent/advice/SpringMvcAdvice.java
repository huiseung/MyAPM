package org.example.agent.advice;

import java.lang.reflect.Method;
import net.bytebuddy.asm.Advice;
import org.example.common.context.Span;
import org.example.common.context.TraceContext;

public class SpringMvcAdvice {
    @Advice.OnMethodEnter
    public static long onEnter(@Advice.Origin Method method) {
        long startTime = System.currentTimeMillis();
        System.out.println("enter: "+method.getName());
        TraceContext.startSpan(method.getName(), startTime);
        return startTime;
    }

    @Advice.OnMethodExit
    public static void onExit(@Advice.Origin Method method,
                              @Advice.Enter long startTime) {
        long endTime = System.currentTimeMillis();
        System.out.println("exit: "+method.getName());
        TraceContext.endSpan(endTime);
    }
}
