package engine.listeners;

import engine.logger.CustomLogger;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class HelperListeners {

    public static String[] getTestMethodNames(ITestNGMethod[] context) {
        String[] methodNames = new String[context.length];
        for (int i = 0; i < context.length; i++) {
            methodNames[i] = context[i].getMethodName();
        }
        return methodNames;
    }

    public static String[] getAllTestMethodNames(ITestContext context) {
        ITestNGMethod[] li = context.getAllTestMethods();
        String[] methodNames = new String[li.length];
        int i = 0;
        for (ITestNGMethod method : li) {
            methodNames[i] = method.getMethodName();
            i++;
        }
        return methodNames;
    }

    public static String[] getFailedTestMethodNames(ITestContext context) {
        Set<ITestNGMethod> li = (Set<ITestNGMethod>) context.getFailedTests().getAllMethods();
        String[] methodNames = new String[li.size()];
        int i = 0;
        for (ITestNGMethod method : li) {
            methodNames[i] = method.getMethodName();
            i++;
        }
        return methodNames;
    }

    public static String[] getSkippedTestMethodNames(ITestContext context) {
        Set<ITestNGMethod> li = (Set<ITestNGMethod>) context.getSkippedTests().getAllMethods();
        String[] methodNames = new String[li.size()];
        int i = 0;
        for (ITestNGMethod method : li) {
            methodNames[i] = method.getMethodName();
            i++;
        }
        return methodNames;
    }


    public static String[] getTestMethodNames(Set<ITestNGMethod> context) {
        String[] methodNames = new String[context.size()];
        int i = 0;
        for (ITestNGMethod method : context) {
            methodNames[i] = method.getMethodName();
            i++;
        }
        return methodNames;
    }


    public static void deleteDirectory(String path) {
        File directory = new File(path);
        try {
            FileUtils.forceDelete(directory);
            CustomLogger.getLogger().info("Deleted the directory {}", path);
        } catch (Exception e) {
            CustomLogger.getLogger().info("couldn't delete directory {}", path);
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        try {
            FileOutputStream fis = new FileOutputStream(path);
            fis.close();
            FileUtils.forceDelete(file);
            CustomLogger.getLogger().info("Deleted the file {}", path);
        } catch (Exception e) {
        }
    }

    public static void runFile(String path) {
        File file = new File(path);
        file.setExecutable(true);
        if (file.canExecute()) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(file.getAbsolutePath());
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                CustomLogger.getLogger().info("File executed");
            }
        } else {
            CustomLogger.getLogger().info("cannot execute file");
        }
    }
}
