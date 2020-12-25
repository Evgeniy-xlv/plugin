package ru.xlv.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginClassLoader extends URLClassLoader {

    private final PluginClassLoaderStorage pluginClassLoaderStorage;
    private final Map<String, Class<?>> classes = new HashMap<>();

    protected PluginClassLoader(PluginClassLoaderStorage pluginClassLoaderStorage, ClassLoader parent, File file) throws MalformedURLException {
        super(new URL[] {file.toURI().toURL()}, parent);
        this.pluginClassLoaderStorage = pluginClassLoaderStorage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    /**
     * Finds a class by its name
     * @param name the name of the class
     * @param useAllLoaders if true, search will use classloaders of other plugins
     * @throws ClassNotFoundException if {@link URLClassLoader#findClass(String)} throws it
     * */
    protected Class<?> findClass(String name, boolean useAllLoaders) throws ClassNotFoundException {
        Class<?> result = this.classes.get(name);
        if (!this.classes.containsKey(name)) {
            if (useAllLoaders)
                result = this.pluginClassLoaderStorage.getClassByName(name);
            if (result == null) {
                result = super.findClass(name);
                if (result != null)
                    this.pluginClassLoaderStorage.putClass(name, result);
            }
            this.classes.put(name, result);
        }
        return result;
    }
}