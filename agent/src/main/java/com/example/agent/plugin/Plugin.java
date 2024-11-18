package com.example.agent.plugin;

import java.lang.instrument.Instrumentation;

public interface Plugin {
    void setUp(Instrumentation inst);
}
