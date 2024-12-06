package org.example.agent.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private static final Logger logger = java.util.logging.Logger.getLogger(Log.class.getName());

    public static void log(String msg){
        logger.log(Level.INFO, msg);
    }
}
