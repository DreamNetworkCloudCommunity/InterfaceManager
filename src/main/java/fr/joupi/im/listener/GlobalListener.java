package fr.joupi.im.listener;

import be.alexandre01.dnplugin.api.NetworkBaseAPI;
import be.alexandre01.dnplugin.api.request.channels.*;
import be.alexandre01.dnplugin.api.request.communication.ClientResponse;
import be.alexandre01.dnplugin.gson.internal.LinkedTreeMap;
import be.alexandre01.dnplugin.netty.channel.ChannelHandlerContext;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dnplugin.plugins.spigot.api.events.player.NetworkDisconnectEvent;
import be.alexandre01.dnplugin.plugins.spigot.api.events.player.NetworkSwitchServerEvent;
import be.alexandre01.dnplugin.plugins.spigot.api.events.server.ServerAttachedEvent;
import be.alexandre01.dnplugin.utils.messages.Message;
import fr.joupi.im.InterfaceManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Getter
@AllArgsConstructor
public class GlobalListener implements Listener {

    private final InterfaceManager plugin;

    @EventHandler
    public void onPlayerDisconnect(NetworkDisconnectEvent event) {
        if (DNSpigotAPI.getInstance().getServices().get(event.getServer().getRemoteService().getName()).getServers().containsValue(event.getServer()))
            DNSpigotAPI.getInstance().getServices().get(event.getServer().getRemoteService().getName()).getServers().get(event.getServer().getId()).getPlayers().remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSwitchServer(NetworkSwitchServerEvent event) {
        DNSpigotAPI.getInstance().getServices().get(event.getFrom().getRemoteService().getName()).getServers().get(event.getFrom().getId()).getPlayers().remove(event.getPlayer());
    }

    @EventHandler
    public void onServerAttached(ServerAttachedEvent event) {
        DNSpigotAPI.getInstance().autoRefreshPlayers();

        DNSpigotAPI.getInstance().getRequestManager().getBasicClientHandler().getResponses().add(new ClientResponse() {
            @Override
            protected void onResponse(Message message, ChannelHandlerContext ctx) {
                if (message.contains("IMOrder")) {
                    if (message.getString("IMOrder").equals("kickall"))
                        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("")));

                    if (message.getString("IMOrder").equals("kickPlayer"))
                        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> Bukkit.getPlayer(message.getString("playerName")).kickPlayer(""));
                }
            }
        });
    }

}
