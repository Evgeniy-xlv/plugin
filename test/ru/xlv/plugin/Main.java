package ru.xlv.plugin;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        PluginManager pluginManager = new PluginManager();
        pluginManager.loadPlugins(new File("plugins"), PluginLoadStrategies.DEFAULT_STRATEGY);
        pluginManager.enableAll();
        System.out.println(pluginManager.getPlugins().size());
        System.out.println(pluginManager.getPlugins().get(0).getClass().getClassLoader());
        pluginManager.disableAll();
    }
}
