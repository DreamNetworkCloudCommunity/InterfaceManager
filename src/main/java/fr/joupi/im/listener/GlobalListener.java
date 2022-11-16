package fr.joupi.im.listener;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
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

    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (getPlugin().get().getPlayerInChatConfirmations().asMap().containsKey(player.getUniqueId())) {
            String[] message = event.getMessage().split(" ");

            if (message[0].equalsIgnoreCase("!cancel")) {
                Utils.sendMessages(player, "&cVous avez annuler cette opération");
                getPlugin().get().getPlayerInChatConfirmations().asMap().remove(player.getUniqueId());
                return;
            }

            if (message[0].matches("[a-zA-Z0-9]*")) {

                getPlugin().get().getMaintenanceManager().addPlayerInWhitelist(getPlugin().get().getPlayerInChatConfirmations().asMap().get(player.getUniqueId()), message[0]);
                getPlugin().get().getPlayerInChatConfirmations().asMap().remove(player.getUniqueId());

                Utils.sendMessages(player, "&aVous avez ajouté &e" + message[0]);
            } else
                Utils.sendMessages(player, "&cLe nom du joueur est incorrect");

            getPlugin().get().getPlayerInChatConfirmations().asMap().remove(player.getUniqueId());
            event.setCancelled(true);
        }
    }

}
