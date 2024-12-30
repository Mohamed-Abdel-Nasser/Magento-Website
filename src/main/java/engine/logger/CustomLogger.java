package engine.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class CustomLogger {

    private static final String LOG_CONFIG_PATH = System.getProperty("log.config.path", "src/main/resources/properties/log4j2.xml");
    private static final Logger LOGGER;

    static {
        configureLogger();
        LOGGER = LogManager.getLogger(CustomLogger.class);
        LOGGER.info("Logger initialized successfully with configuration from: {}", LOG_CONFIG_PATH);
    }


    private CustomLogger() {
        throw new UnsupportedOperationException("CustomLogger is a utility class and cannot be instantiated.");
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private static void configureLogger() {
        try {
            Configurator.initialize(null, LOG_CONFIG_PATH);
        } catch (Exception e) {
            System.err.println("Failed to initialize logger configuration: " + e.getMessage());
            throw new IllegalStateException("Error initializing logger with configuration file: " + LOG_CONFIG_PATH, e);
        }
    }
}

