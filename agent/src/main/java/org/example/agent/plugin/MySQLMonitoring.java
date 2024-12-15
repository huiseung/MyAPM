package org.example.agent.plugin;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.agent.advice.MySQLAdvice;

public class MySQLMonitoring implements Plugin {
    @Override
    public void setUp(Instrumentation inst) {
        new Default()
                .with(RedefinitionStrategy.RETRANSFORMATION) // .class 파일 재 정의
                .type(ElementMatchers.nameStartsWith("com.mysql.jdbc")
                        .or(ElementMatchers.nameStartsWith("com.mysql.cj.jdbc")))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                            return builder.method(ElementMatchers.nameStartsWith("execute"))
                                    .intercept(Advice.to(MySQLAdvice.class));
                        }
                )
                .installOn(inst);
    }
}
