package ru.xlv.plugin;

import java.util.HashMap;
import java.util.Map;

public class PluginClassLoaderStorage {

    private final Map<String, PluginClassLoader> classLoaderMap = new HashMap<>();
    private final Map<String, Class<?>> classes = new HashMap<>();

    protected void putClassLoader(String pluginClasspath, PluginClassLoader pluginClassLoader) {
        this.classLoaderMap.put(pluginClasspath, pluginClassLoader);
    }

    protected Class<?> getClassByName(String name) {
        Class<?> aClass = this.classes.get(name);
        if (aClass != null)
            return aClass;
        for (String classpath : this.classLoaderMap.keySet()) {
            PluginClassLoader pluginClassLoader = this.classLoaderMap.get(classpath);
            try {
                aClass = pluginClassLoader.findClass(name, false);
            } catch (ClassNotFoundException ignored) {}
        }
        return aClass;
    }

    protected void putClass(String name, Class<?> aClass) {
        this.classes.putIfAbsent(name, aClass);
    }
}
