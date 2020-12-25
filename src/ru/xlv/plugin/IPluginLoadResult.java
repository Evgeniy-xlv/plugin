package ru.xlv.plugin;

public interface IPluginLoadResult {

    Plugin getPlugin();

    PluginClassLoader getClassLoader();
}
