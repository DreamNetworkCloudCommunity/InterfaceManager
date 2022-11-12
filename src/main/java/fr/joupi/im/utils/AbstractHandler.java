package fr.joupi.im.utils;

import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public abstract class AbstractHandler<P extends JavaPlugin> {

    private final P plugin;

    public void load() {}

    public void unload() {}

}
