package fr.joupi.im.listener;

import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dnplugin.plugins.spigot.api.events.player.NetworkDisconnectEvent;
import be.alexandre01.dnplugin.plugins.spigot.api.events.player.NetworkSwitchServerEvent;
import be.alexandre01.dnplugin.plugins.spigot.api.events.server.ServerStartedEvent;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.threading.MultiThreading;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public class GlobalListener implements Listener {

    private final InterfaceManager plugin;

    @EventHandler
    public void onPlayerDisconnect(NetworkDisconnectEvent event) {
        if (Utils.getServerName().equals(event.getServer().getFullName()))
            Utils.getServer().getPlayers().remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSwitchServer(NetworkSwitchServerEvent event) {
        DNSpigotAPI.getInstance().getServices().get(event.getFrom().getRemoteService().getName()).getServers().get(event.getFrom().getId()).getPlayers().remove(event.getPlayer());
    }

    @EventHandler
    public void onServerStart(ServerStartedEvent event) {
        MultiThreading.schedule(() -> getPlugin().get().getMessageManager().sendMaintenanceNewServerMessage(event.getServer()), 2L, TimeUnit.SECONDS);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (getPlugin().get().getPlayerInChatConfirmations().asMap().containsKey(player.getUniqueId())) {
            String[] message = event.getMessage().split(" ");

            if (!message[0].equalsIgnoreCase("!cancel"))
                if (message[0].matches("[a-zA-Z0-9]*"))
                    if (getPlugin().get().getMaintenanceManager().getMaintenanceServer(getPlugin().get().getPlayerInChatConfirmations().asMap().get(player.getUniqueId())).getWhitelists().stream().noneMatch(s -> s.equals(message[0]))) {
                        getPlugin().get().getMaintenanceManager().addPlayerInWhitelist(getPlugin().get().getPlayerInChatConfirmations().asMap().get(player.getUniqueId()), message[0]);
                        Utils.sendMessages(player, "&aVous avez ajout?? &e" + message[0] + " &aa la maintenance du serveur &b" + getPlugin().get().getPlayerInChatConfirmations().asMap().get(player.getUniqueId()).getFullName());
                    } else
                        Utils.sendMessages(player, "&cLe joueur &e" + message[0] + " &cest d??j?? dans la maintenance du serveur &b" + getPlugin().get().getPlayerInChatConfirmations().asMap().get(player.getUniqueId()).getFullName());
                else
                    Utils.sendMessages(player, "&cLe nom du joueur est incorrect");
            else
                Utils.sendMessages(player, "&cVous avez annuler cette op??ration");

            getPlugin().get().getPlayerInChatConfirmations().asMap().remove(player.getUniqueId());
            event.setCancelled(true);
        }
    }

}
