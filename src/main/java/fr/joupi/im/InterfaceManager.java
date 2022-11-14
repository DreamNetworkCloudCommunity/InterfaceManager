package fr.joupi.im;

import fr.joupi.im.common.Loader;
import org.bukkit.plugin.java.JavaPlugin;

public class InterfaceManager extends JavaPlugin {

    private Loader loader;

    @Override
    public void onEnable() {
        this.loader = new Loader(this);
    }

    public Loader get() {
        return this.loader;
    }

    @Override
    public void onDisable() {
        get().unload();
    }

}
