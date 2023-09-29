package fr.joupi.im.common;

import be.alexandre01.dnplugin.api.connection.request.channels.DNChannel;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.command.InterfaceCommand;
import fr.joupi.im.common.maintenance.MaintenanceManager;
import fr.joupi.im.common.message.MessageManager;
import fr.joupi.im.listener.GlobalListener;
import fr.joupi.im.utils.AbstractHandler;
import fr.joupi.im.utils.command.CommandHandler;
import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public class Loader extends AbstractHandler<InterfaceManager> {

    private final DNChannel channel;

    private final CommandHandler commandHandler;

    private final MessageManager messageManager;
    private final MaintenanceManager maintenanceManager;

    private final Cache<UUID, DNServer> playerInChatConfirmations;

    public Loader(InterfaceManager plugin) {
        super(plugin);

        this.channel = new DNChannel("IMChannel");
        this.commandHandler = new CommandHandler(plugin, "im");
        this.messageManager = new MessageManager(plugin);
        this.maintenanceManager = new MaintenanceManager(plugin);
        this.playerInChatConfirmations = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).build();

        this.load();
    }

    @Override
    public void load() {
        registerListeners(getMessageManager(), new GlobalListener(getPlugin()));
        getCommandHandler().registerCommand(new InterfaceCommand<>(getPlugin()));
        getMaintenanceManager().load();
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
