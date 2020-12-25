package ru.xlv.plugin;

public abstract class Plugin {

    private PluginManager pluginManager;
    private PluginDescription pluginDescription;

    private boolean isInited;

    /**
     * Init method marks the plugin as loaded and ready to go.
     * */
    protected final void init(PluginManager pluginManager, PluginDescription pluginDescription) {
        if (!this.isInited) {
            this.isInited = true;
            this.pluginManager = pluginManager;
            this.pluginDescription = pluginDescription;
        }
    }

    /**
     * Called when the plugin is loaded and ready to go.
     * */
    protected void onLoad() {}

    /**
     * Called by {@link PluginManager}.
     * */
    protected void onEnable() {}

    /**
     * Called by {@link PluginManager}.
     * */
    protected void onDisable() {}

    public boolean isInited() {
        return this.isInited;
    }

    public final PluginDescription getPluginDescription() {
        return this.pluginDescription;
    }

    public final PluginManager getPluginManager() {
        return this.pluginManager;
    }
}
