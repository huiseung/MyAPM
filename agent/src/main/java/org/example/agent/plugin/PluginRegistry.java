package org.example.agent.plugin;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

public class PluginRegistry {
    private final List<Plugin> plugins = new ArrayList<Plugin>();

    public void register(Plugin plugin) {
        plugins.add(plugin);
    }

    public void setupAll(Instrumentation inst){
        for(Plugin plugin : plugins){
            plugin.setUp(inst);
        }
    }
}
