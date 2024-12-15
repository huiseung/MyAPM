package org.example.agent.advice;

import java.lang.reflect.Method;
import net.bytebuddy.asm.Advice;
import org.example.common.context.TraceContext;

public class MySQLAdvice {
    @Advice.OnMethodEnter
    public static void onEnter(@Advice.Origin Method method) {
        long startTime = System.nanoTime();
        System.out.println("Enter MySQL Method: "+method.getName());
        TraceContext.startSpan(method.getName(), startTime);
    }

    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void onExit(
            @Advice.Thrown Throwable throwable,
            @Advice.Origin Method method
    ) {
        long endTime = System.currentTimeMillis();
        TraceContext.endSpan(endTime);
    }
}
