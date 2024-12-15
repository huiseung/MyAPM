package org.example.agent.plugin;

import static net.bytebuddy.matcher.ElementMatchers.any;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationDescription;
import org.example.agent.advice.SpringMvcAdvice;

public class SpringMvcMonitoring implements Plugin {
    @Override
    public void setUp(Instrumentation inst) {
        new Default()
                .with(RedefinitionStrategy.RETRANSFORMATION)
                .type(typeDescription ->
                        typeDescription.getDeclaredAnnotations()
                                .stream()
                                .anyMatch(annotation -> isSpringAnnotation(annotation))
                )
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                            return builder.method(any())
                                    .intercept(Advice.to(SpringMvcAdvice.class));
                        }
                )
                .installOn(inst);
    }

    private static boolean isSpringAnnotation(AnnotationDescription annotation) {
        String annotationName = annotation.getAnnotationType().getTypeName();
        return annotationName.equals("org.springframework.web.bind.annotation.RestController")
                || annotationName.equals("org.springframework.web.bind.annotation.Controller")
                || annotationName.equals("org.springframework.stereotype.Service")
                ;
    }
}
