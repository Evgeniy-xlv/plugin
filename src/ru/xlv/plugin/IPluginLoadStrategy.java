package ru.xlv.plugin;

import java.io.File;

public interface IPluginLoadStrategy<RESULT extends IPluginLoadResult> {

    RESULT loadPlugin(File file, PluginClassLoader pluginClassLoader, PluginDescription pluginDescription);
}
