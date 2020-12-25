package ru.xlv.plugin;

import java.io.File;

public final class PluginLoadStrategies {

    private PluginLoadStrategies() {}

    /**
     * This strategy will load all plugins using the extended {@link java.net.URLClassLoader}.
     *
     * For interoperability between plugins, {@link PluginClassLoader} has been created, which allows plugins to refer to the classes of all plugins.
     * It is important to understand that in a situation where plugins have classes with the same classpath, only one of them will be loaded. Typically, this is the first class loaded.
     * Each plugin must contain a description file named `plugin.desc` at the root of the jar, with the specified path to the class extending {@link Plugin}.
     * Sample plugin.desc content: `main=ru.xlv.plugin.TestPlugin`
     *
     * @see PluginClassLoader
     * @see PluginClassLoaderStorage
     * @see PluginDescription
     * */
    public static final IPluginLoadStrategy<DefaultPluginLoadResult> DEFAULT_STRATEGY = new DefaultPluginLoadStrategy();

    private static class DefaultPluginLoadStrategy implements IPluginLoadStrategy<DefaultPluginLoadResult> {
        @Override
        public DefaultPluginLoadResult loadPlugin(File file, PluginClassLoader pluginClassLoader, PluginDescription pluginDescription) {
            DefaultPluginLoadResult defaultPluginLoadResult = new DefaultPluginLoadResult();
            defaultPluginLoadResult.pluginClassLoader = pluginClassLoader;
            try {
                Class<?> aClass = pluginClassLoader.findClass(pluginDescription.getMain());
                Object o = aClass.newInstance();
                defaultPluginLoadResult.plugin = (Plugin) o;
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return defaultPluginLoadResult;
        }
    }

    private static class DefaultPluginLoadResult implements IPluginLoadResult {

        private Plugin plugin;
        private PluginClassLoader pluginClassLoader;

        @Override
        public Plugin getPlugin() {
            return this.plugin;
        }

        @Override
        public PluginClassLoader getClassLoader() {
            return this.pluginClassLoader;
        }
    }
}
