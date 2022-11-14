package fr.joupi.im.common;

import be.alexandre01.dnplugin.api.request.channels.DNChannel;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.command.InterfaceCommand;
import fr.joupi.im.listener.GlobalListener;
import fr.joupi.im.utils.AbstractHandler;
import fr.joupi.im.utils.command.CommandHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

import java.util.Arrays;

@Getter
public class Loader extends AbstractHandler<InterfaceManager> {

    private final CommandHandler commandHandler;
    private final DNChannel dnChannel;

    public Loader(InterfaceManager plugin) {
        super(plugin);
        this.commandHandler = new CommandHandler(plugin, "im");
        this.dnChannel = new DNChannel("IMChannel");
        this.load();
    }

    @Override
    public void load() {
        getCommandHandler().registerCommand(new InterfaceCommand<>(getPlugin()));
        registerListener(new GlobalListener(getPlugin()));
    }

    @Override
    public void unload() {
        super.unload();
    }

    public void registerListeners(Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(this::registerListener);
    }

    public void registerListener(Listener listener) {
        getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());
    }

}
