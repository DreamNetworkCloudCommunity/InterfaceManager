package fr.joupi.im.command;

import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.plugins.spigot.DNSpigot;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.ServerCategoryGui;
import fr.joupi.im.utils.command.annotation.Command;
import fr.joupi.im.utils.threading.MultiThreading;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Data
public class InterfaceCommand<P extends InterfaceManager> {

    private final P plugin;

    @Command(name = "im", permission = "im.admin")
    public void execute(Player player) {
        new ServerCategoryGui(plugin).onOpen(player);
    }

    @Command(name = "info")
    public void executeInfo(Player player) {
        player.sendMessage(DNSpigotAPI.getInstance().hasAlreadyPlayerRefreshed() + "");

        DNSpigotAPI.getInstance().getServices().values()
                .forEach(service -> service.getServers().values()
                        .forEach(server -> player.sendMessage(server.getName() + "-" + server.getId() + " : " + server.getPlayers().size() + " | " + server.getRemoteService().getPlayers().size())));

        player.sendMessage(">" + DNSpigotAPI.getInstance().getServices().get("lobby").getServers().get(0).getPlayers().stream().map(DNPlayer::getName).collect(Collectors.joining(", ")));
    }

    @Command(name = "autoref")
    public void executeAutoRefresh(Player player) {
        player.sendMessage(DNSpigotAPI.getInstance().hasAlreadyPlayerRefreshed() + "");
    }

    @Command(name = "test")
    public void executeTest(Player player) {
        if (DNSpigotAPI.getInstance().hasAlreadyPlayerRefreshed()) {
            MultiThreading.schedule(() -> getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), () ->
                DNSpigotAPI.getInstance().getServices().get("lobby").getServers().forEach((s, remoteService) -> Bukkit.broadcastMessage("> " + s + " | " + remoteService.getPlayers().size()))
                    , 3L, 3L), 3, TimeUnit.SECONDS);
        }
    }

}
