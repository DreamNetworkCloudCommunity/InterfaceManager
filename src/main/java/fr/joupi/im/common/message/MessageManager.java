package fr.joupi.im.common.message;

import be.alexandre01.dnplugin.api.NetworkBaseAPI;
import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.api.request.RequestType;
import be.alexandre01.dnplugin.api.request.channels.DNChannel;
import be.alexandre01.dnplugin.api.request.communication.ClientResponse;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dnplugin.plugins.spigot.api.events.server.ServerAttachedEvent;
import be.alexandre01.dnplugin.utils.messages.Message;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.maintenance.MaintenanceServer;
import fr.joupi.im.utils.AbstractHandler;
import fr.joupi.im.utils.MaintenanceServerAdapter;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.threading.MultiThreading;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
public class MessageManager extends AbstractHandler<InterfaceManager> implements Listener {

    private final DNChannel dnChannel;
    private final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapter(MaintenanceServer.class, new MaintenanceServerAdapter())
            .serializeNulls()
            .create();

    public MessageManager(InterfaceManager plugin) {
        super(plugin);
        this.dnChannel = new DNChannel("IMChannel");
    }

    public String getOrderID() {
        return "IMOrder";
    }

    public void sendCommandToServer(String serverName, String command) {
        DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.SERVER_EXECUTE_COMMAND, serverName, command);
    }

    public void sendStartServerMessage(String serverName) {
        DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.CORE_START_SERVER, serverName);
    }

    public void sendMaintenanceNewServerMessage(DNServer server) {
        if (!Utils.getServerName().equals(server.getFullName()))
            server.sendMessage(new Message().set(getOrderID(), Messages.NEW_SERVER.getName()).set("list", new ArrayList<>(getPlugin().get().getMaintenanceManager().getWhitelists().values())));
    }

    public void sendMaintenanceUpdateMessage(MaintenanceServer maintenanceServer) {
        NetworkBaseAPI.getInstance().getServices().values().forEach(remoteService ->
                remoteService.getServers().values().forEach(server -> sendMessage(server, new Message().set(getOrderID(), Messages.UPDATE_MAINTENANCE_DATA.getName()).set("object", maintenanceServer))));
    }

    public void sendMaintenanceUpdateStatusMessage(MaintenanceServer maintenanceServer) {
        NetworkBaseAPI.getInstance().getServices().values().forEach(remoteService ->
                remoteService.getServers().values().forEach(server -> sendMessage(server, new Message().set(getOrderID(), Messages.UPDATE_MAINTENANCE_DATA.getName()).set(Messages.UPDATE_MAINTENANCE_STATUS.getName(), maintenanceServer.isWhitelisted()).set("object", maintenanceServer))));
    }

    public void sendMaintenanceAddListMessage(MaintenanceServer maintenanceServer, String playerName) {
        NetworkBaseAPI.getInstance().getServices().values().forEach(remoteService ->
                remoteService.getServers().values().forEach(server -> sendMessage(server, new Message().set(getOrderID(), Messages.UPDATE_MAINTENANCE_DATA.getName()).set(Messages.UPDATE_MAINTENANCE_LIST_ADD.getName(), playerName).set("object", maintenanceServer))));
    }

    public void sendMaintenanceRemoveListMessage(MaintenanceServer maintenanceServer, String playerName) {
        NetworkBaseAPI.getInstance().getServices().values().forEach(remoteService ->
                remoteService.getServers().values().forEach(server -> sendMessage(server, new Message().set(getOrderID(), Messages.UPDATE_MAINTENANCE_DATA.getName()).set(Messages.UPDATE_MAINTENANCE_LIST_REMOVE.getName(), playerName).set("object", maintenanceServer))));
    }

    public void sendKickAllPlayerMessage(DNServer server) {
        sendMessage(server, new Message().set(getOrderID(), Messages.KICK_ALL.getName()));
    }

    public void sendKickPlayerMessage(DNServer server, DNPlayer player) {
        sendMessage(server, new Message().set(getOrderID(), Messages.KICK_PLAYER.getName()).set("playerName", player.getName()));
    }

    public void sendTeleportPlayerMessage(DNServer server, Player player, DNPlayer targetPlayer) {
        sendMessage(server, new Message().set(getOrderID(), Messages.TELEPORT_PLAYER.getName()).set("playerName", player.getName()).set("targetPlayerName", targetPlayer.getName()));
    }

    public void sendMessage(DNServer server, Message message) {
        server.sendMessage(message);
    }

    private void executeCommand(String serverName, String command) {
        if (Utils.getServerName().equals(serverName))
            Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }

    @EventHandler
    public void onServerAttached(ServerAttachedEvent event) {
        DNSpigotAPI.getInstance().autoRefreshPlayers();

        DNSpigotAPI.getInstance().getRequestManager().getClientHandler().getResponses().add(new ClientResponse() {
            @Override
            protected void onResponse(Message message, ChannelHandlerContext ctx) {
                if (message.contains(getOrderID())) {

                    if (message.getString(getOrderID()).equals(Messages.NEW_SERVER.getName())) {
                        ArrayList<LinkedTreeMap<String, Object>> treeMapArrayList = (ArrayList<LinkedTreeMap<String, Object>>) message.get("list");
                        List<MaintenanceServer> list = getGson().fromJson(getGson().toJson(treeMapArrayList), new TypeToken<List<MaintenanceServer>>() {}.getType());list.forEach(maintenanceServer -> getPlugin().get().getMaintenanceManager().getWhitelists().putIfAbsent(maintenanceServer.getServerName(), maintenanceServer));
                    }

                    if (message.getString(getOrderID()).equals(Messages.UPDATE_MAINTENANCE_DATA.getName())) {
                        LinkedTreeMap<String, Object> objectLinkedTreeMap = (LinkedTreeMap<String, Object>) message.get("object");
                        MaintenanceServer maintenanceServer = getGson().fromJson(getGson().toJson(objectLinkedTreeMap), MaintenanceServer.class);

                        if (message.contains(Messages.UPDATE_MAINTENANCE_STATUS.getName()))
                            executeCommand(maintenanceServer.getServerName(), "whitelist " + (maintenanceServer.isWhitelisted() ? "on" : "off"));
                        if (message.contains(Messages.UPDATE_MAINTENANCE_LIST_ADD.getName()))
                            executeCommand(maintenanceServer.getServerName(), "whitelist add " + message.getString(Messages.UPDATE_MAINTENANCE_LIST_ADD.getName()));
                        if (message.contains(Messages.UPDATE_MAINTENANCE_LIST_REMOVE.getName()))
                            executeCommand(maintenanceServer.getServerName(), "whitelist remove " + message.getString(Messages.UPDATE_MAINTENANCE_LIST_REMOVE.getName()));

                        getPlugin().get().getMaintenanceManager().getWhitelists().put(maintenanceServer.getServerName(), maintenanceServer);
                    }

                    if (message.getString(getOrderID()).equals(Messages.KICK_ALL.getName()))
                        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("")));

                    if (message.getString(getOrderID()).equals(Messages.KICK_PLAYER.getName()))
                        if (Bukkit.getPlayer(message.getString("playerName")).isOnline())
                            Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> Bukkit.getPlayer(message.getString("playerName")).kickPlayer(""));

                    if (message.getString(getOrderID()).equals(Messages.TELEPORT_PLAYER.getName()))
                        if (Bukkit.getPlayer(message.getString("targetPlayerName")).isOnline())
                            MultiThreading.schedule(() -> Bukkit.getPlayer(message.getString("playerName")).teleport(Bukkit.getPlayer(message.getString("targetPlayerName"))), 1L, TimeUnit.SECONDS);
                }
            }
        });
    }
}