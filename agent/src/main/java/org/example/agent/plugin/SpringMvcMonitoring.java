package org.example.agent.plugin;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.hasAnnotation;
import static net.bytebuddy.matcher.ElementMatchers.named;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.annotation.AnnotationDescription;
import org.example.agent.advice.DispatcherServletAdvice;
import org.example.agent.advice.SpringMvcAdvice;

public class SpringMvcMonitoring implements Plugin {
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
