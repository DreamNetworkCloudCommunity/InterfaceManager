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
import fr.joupi.im.utils.threading.MultiThreading;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Getter
@AllArgsConstructor
public class PlayerListener implements Listener {

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
        DNChannelManager channelManager = NetworkBaseAPI.getInstance().getChannelManager();
        DNChannel dnChannel = new DNChannel("IMChannel");

        channelManager.registerChannel(dnChannel, true, new RegisterListener() {@Override public void onNewDataReceived(LinkedTreeMap<String, Object> linkedTreeMap) {}});

        DNSpigotAPI.getInstance().getRequestManager().getBasicClientHandler().getResponses().add(new ClientResponse() {
            @Override
            protected void onResponse(Message message, ChannelHandlerContext ctx) {
                if (message.contains("instruction")) {
                    String ins = message.getString("instruction");

                    if (ins.equals("kickall")) {
                        if (message.get("serverTarget").equals(DNSpigotAPI.getInstance().getServerName() + "-" + DNSpigotAPI.getInstance().getID()))
                            Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(),
                                    () -> Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("")));
                    }
                }
            }
        });

        /*dnChannel.addInterceptor(channelPacket -> {
            if (channelPacket.getMessage().contains("instruction")) {
                String ins = channelPacket.getMessage().getString("instruction");

               if (ins.equals("kickall")) {
                   if (channelPacket.getMessage().get("serverTarget").equals(DNSpigotAPI.getInstance().getServerName() + "-" + DNSpigotAPI.getInstance().getID()))
                       Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(),
                               () -> Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("")));
               }
           }
        });*/

    }

}
