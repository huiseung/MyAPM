package org.example.common.context;


import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class TraceContext {
    private static final ThreadLocal<Stack<Span>> spanStack = ThreadLocal.withInitial(Stack::new);

    public static void startSpan(String methodName, long startTime) {
        Span span = new Span(methodName, startTime);
        Stack<Span> stack = spanStack.get();

        if (!stack.isEmpty()) {
            Span parentSpan = stack.peek();
            parentSpan.addChild(span);
        }

        stack.push(span);
    }

    public static Span endSpan(long endTime) {
        Stack<Span> stack = spanStack.get();

        if (!stack.isEmpty()) {
            Span span = stack.pop();
            span.setEndTime(endTime);
            span.setDuration(endTime - span.getStartTime());

            if (stack.isEmpty()) {
                spanStack.remove();
            }
            return span;
        }
        return null;
    }


    public static void clear() {
        spanStack.remove();
    }
}
