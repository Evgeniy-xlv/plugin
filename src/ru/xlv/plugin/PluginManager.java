package ru.xlv.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PluginManager {

    private static int pluginManagerCounter = 0;

    private final Logger logger = Logger.getLogger(PluginManager.class.getSimpleName() + (pluginManagerCounter++));

    private final List<Plugin> plugins = new ArrayList<>();

    private final PluginClassLoaderStorage pluginClassLoaderStorage = new PluginClassLoaderStorage();

    /**
     * Loads all plugins
     * @param directory plugins directory
     * @throws RuntimeException if {@link File#mkdirs()} failed; or if input file isn't a directory
     * */
    public void loadPlugins(File directory) {
        loadPlugins(directory, PluginLoadStrategies.DEFAULT_STRATEGY);
    }

    /**
     * Loads all plugins
     * @param directory plugins directory
     * @param pluginLoadStrategy plugin loading strategy
     * @throws RuntimeException if {@link File#mkdirs()} failed; or if input file isn't a directory
     * */
    public <T extends IPluginLoadResult> void loadPlugins(File directory, IPluginLoadStrategy<T> pluginLoadStrategy) {
        if (!directory.exists() && !directory.mkdirs())
            throw new RuntimeException("Unexpected error during mkdirs");
        if (!directory.isDirectory())
            throw new RuntimeException(directory + " isn't a directory");
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files)
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    try {
                        PluginDescription pluginDescription = new PluginDescription(file);
                        PluginClassLoader pluginClassLoader = new PluginClassLoader(this.pluginClassLoaderStorage, getClass().getClassLoader(), file);
                        T result = pluginLoadStrategy.loadPlugin(file, pluginClassLoader, pluginDescription);
                        if (result.getPlugin() != null) {
                            result.getPlugin().init(this, pluginDescription);
                            result.getPlugin().onLoad();
                            this.plugins.add(result.getPlugin());
                            this.pluginClassLoaderStorage.putClassLoader(result.getPlugin().getClass().getName(), result.getClassLoader());
                            this.logger.info(result.getPlugin() + " loaded successfully");
                        } else {
                            this.logger.warning(file.getName() + " isn't a plugin! skipping...");
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
    }

    /**
     * Calls {@link Plugin#onEnable()} of all plugins
     * */
    public void enableAll() {
        this.plugins.forEach(Plugin::onEnable);
    }

    /**
     * Calls {@link Plugin#onDisable()} of all plugins
     * */
    public void disableAll() {
        this.plugins.forEach(Plugin::onDisable);
    }

    /**
     * @return all loaded plugins
     * */
    public List<Plugin> getPlugins() {
        return this.plugins;
    }

    protected PluginClassLoaderStorage getPluginClassLoaderStorage() {
        return this.pluginClassLoaderStorage;
    }
}
