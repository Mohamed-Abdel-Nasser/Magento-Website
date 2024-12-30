package engine.BrowserFactory.driverSetupAndOptions;

import engine.constants.FrameWorkConstants;

import java.util.ArrayList;
import java.util.List;

public class Options {
    public static List<String> getBrowserOptions() {
        List<String> options = new ArrayList<>();
        addOptionIfTrue(options, FrameWorkConstants.HEADLESS_MODE, "--headless");
        addOptionIfTrue(options, FrameWorkConstants.MAXIMIZED, "--start-maximized");
        addOptionIfTrue(options, FrameWorkConstants.EXTENSIONS, "--disable-extensions");
        addOptionIfTrue(options, FrameWorkConstants.NO_SANDBOX, "--no-sandbox");
        addOptionIfTrue(options, FrameWorkConstants.DISABLE_DEV_SHM, "--disable-dev-shm-usage");
        return options;
    }

    private static void addOptionIfTrue(List<String> options, String configValue, String option) {
        if ("true".equalsIgnoreCase(configValue)) {
            options.add(option);
        }
    }
}
