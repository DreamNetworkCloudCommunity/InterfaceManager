package fr.joupi.im.utils.config;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public abstract class AbstractConfig<P extends JavaPlugin> extends YamlConfiguration {

    private final P plugin;

    private final String configName;

    public AbstractConfig(P plugin, String configName) {
        this.plugin = plugin;
        this.configName = configName + ".yml";
        this.load();
    }

    private void load() {
        try {
            create();
            load(getFile());
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    private void create() {
        if (!getFile().exists()) {
            getFile().getParentFile().mkdirs();
            getPlugin().saveResource(configName, false);
        }
    }

    private File getFile() {
        return new File(getPlugin().getDataFolder(), getConfigName());
    }

}
