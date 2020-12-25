package ru.xlv.plugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

public class PluginDescription {

    private final Map<String, String> metadata;

    private final String main;

    public PluginDescription(File file) {
        this.metadata = readFrom(file);
        this.main = this.metadata.remove("main");
    }

    /**
     * @return plugin's main classpath
     * */
    public String getMain() {
        return this.main;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    /**
     * Reads the description file
     * @param file description file
     * @return map with keys and values read from the description file
     * */
    private Map<String, String> readFrom(File file) {
        Map<String, String> map = new HashMap<>();
        try (
                ZipFile zipFile = new ZipFile(file);
                InputStream inputStream = zipFile.getInputStream(zipFile.getEntry("plugin.desc"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("=");
                map.put(split[0], split[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
