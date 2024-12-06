package org.example.agent.plugin;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.asm.Advice;
import org.example.agent.advice.DispatcherServletAdvice;

public class DispatcherServletMonitoring implements Plugin{
    @Override
    public void setUp(Instrumentation inst) {
        // Spring MVC DispatcherServlet
        new Default()
                .with(RedefinitionStrategy.RETRANSFORMATION) // .class 파일 재 정의
                .type(named("org.springframework.web.servlet.DispatcherServlet"))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                            return builder.method(named("doDispatch"))
                                    .intercept(Advice.to(DispatcherServletAdvice.class));
                        }
                )
                .installOn(inst);
    }
}
