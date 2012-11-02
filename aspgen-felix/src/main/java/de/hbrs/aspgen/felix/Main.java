package de.hbrs.aspgen.felix;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public final class Main {
    private static Framework mFwk = null;

    private Main() { }


    public static void main(final String[] args) {
        System.out.println("\nWelcome to AspGen");
        System.out.println("======================\n");
        final Map<String, String> config = setupConfiguration(args);
        try {
            mFwk = getFrameworkFactory().newFramework(config);
            mFwk.init();
            AutoProcessor.process(config, mFwk.getBundleContext());
            mFwk.start();
            mFwk.waitForStop(0);
        } catch (final IOException | BundleException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
        System.exit(0);
    }


    private static Map<String, String> setupConfiguration(final String[] args) {
        final Map<String, String> config = new HashMap<String, String>();
        final String aspgenHome = readProperty("aspgen.home", args);
        final String projectDir = readProperty("project.dir", args);
        config.put("org.apache.felix.log.storeDebug", "true");
        config.put("org.apache.felix.log.maxSize", "-1");
        config.put("org.osgi.framework.storage", aspgenHome + "/cache");
        config.put("org.osgi.framework.startlevel.beginning", "10");
        config.put("org.osgi.framework.storage.clean", "onFirstInit");
        config.put(
                "felix.auto.start.1",
                "file:"+ aspgenHome +"/bundles/org.apache.felix.gogo.command-0.12.0.jar "
                        + "file:"+ aspgenHome +"/bundles/org.apache.felix.gogo.shell-0.10.0.jar "
                        + "file:"+ aspgenHome +"/bundles/org.apache.felix.gogo.runtime-0.10.0.jar "
                        + "file:"+ aspgenHome +"/bundles/org.apache.felix.log-1.0.1.jar");
        config.put("felix.auto.start.3",
                        "file:"+ aspgenHome +"/bundles/aspgen-aspectj-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-api-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-java-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-filemanager-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-generator-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-notification-server-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-history-0.0.1.jar "
                        + "file:"+ aspgenHome +"/bundles/aspgen-merger-0.0.1.jar ");
        config.put("felix.auto.start.6",
                "file:"+ aspgenHome +"/bundles/aspgen-core-0.0.1.jar");
        final String libFiles = readLibFiles(aspgenHome);
        config.put("felix.auto.start.2", libFiles);
        final String generatorFiles = readGeneratorFiles(aspgenHome);
        config.put("felix.auto.start.5", generatorFiles);
        config.put("project.dir", projectDir);
        return config;
    }

    private static String readProperty(final String key, final String[] args) {
        for (final String arg : args) {
            if (arg.startsWith("-D" + key)) {
                if (arg.split("=").length > 1) {
                    final String value = arg.split("=")[1];
                    return value.replace("\"", "");
                } else {
                    return getCurrentPath();
                }
            }
        }
        return getCurrentPath();
    }

    private static String getCurrentPath() {
        final String currentPath = new File(".").getAbsolutePath();
        if (currentPath.endsWith("\\.") ||currentPath.endsWith("/.")) {
            return currentPath.substring(0, currentPath.length() - 2);
        } else {
            return currentPath;
        }
    }

    // TODO refactor
    private static String readGeneratorFiles(final String aspgenHome) {
        String libFiles = "";
        final File libsDir = new File(aspgenHome +"/generators");
        for (final File file : libsDir.listFiles()) {
            if (file.getName().endsWith(".jar")) {
                libFiles += "file:"+ aspgenHome +"/generators/" + file.getName() + " ";
            }
        }
        return libFiles;
    }

    private static String readLibFiles(final String aspgenHome) {
        String libFiles = "";
        final File libsDir = new File(aspgenHome +"/libs");
        for (final File file : libsDir.listFiles()) {
            if (file.getName().endsWith(".jar")) {
                libFiles += "file:"+ aspgenHome +"/libs/" + file.getName() + " ";
            }
        }
        return libFiles;
    }

    private static FrameworkFactory getFrameworkFactory() throws IOException {
        final URL url = Main.class.getClassLoader().getResource(
                "META-INF/services/org.osgi.framework.launch.FrameworkFactory");
        if (url != null) {
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            try {
                for (String s = br.readLine(); s != null; s = br.readLine()) {
                    final String s2 = s.trim();
                    if ((s2.length() > 0) && (s2.charAt(0) != '#')) {
                        return (FrameworkFactory) Class.forName(s)
                                .newInstance();
                    }
                }
            } catch (InstantiationException
                    | IllegalAccessException
                    | ClassNotFoundException
                    | IOException e) {
                throw new RuntimeException("Could not find framework factory.");
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        }

        throw new RuntimeException("Could not find framework factory.");
    }
}
