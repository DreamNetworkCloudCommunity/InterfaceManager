package fr.joupi.im.common.maintenance;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import com.google.common.collect.Maps;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.AbstractHandler;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.threading.MultiThreading;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
public class MaintenanceManager extends AbstractHandler<InterfaceManager> {

    private final Map<String, MaintenanceServer> whitelists;

    public MaintenanceManager(InterfaceManager plugin) {
        super(plugin);
        this.whitelists = Maps.newConcurrentMap();
    }

    @Override
    public void load() {
        MultiThreading.schedule(() ->
                getPlugin().get().getMessageManager().sendMaintenanceUpdateMessage(new MaintenanceServer(Utils.getServerName(), Bukkit.getWhitelistedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.toList()), Bukkit.getServer().hasWhitelist())), 2L, TimeUnit.SECONDS);
    }

    public void updateServerWhitelistStatus(String serverName, boolean state) {
        getWhitelists().get(serverName).setWhitelisted(state);
        getPlugin().get().getMessageManager().sendMaintenanceUpdateStatusMessage(getWhitelists().get(serverName));
    }

    public void removePlayerInWhitelist(DNServer server, String playerName) {
        getMaintenanceServer(server).getWhitelists().remove(playerName);
        getPlugin().get().getMessageManager().sendMaintenanceRemoveListMessage(getWhitelists().get(server.getName()), playerName);
    }

    public void addPlayerInWhitelist(DNServer server, String playerName) {
        getMaintenanceServer(server).getWhitelists().add(playerName);
        getPlugin().get().getMessageManager().sendMaintenanceAddListMessage(getWhitelists().get(server.getName()), playerName);
    }

    public boolean contains(DNServer server, String playerName) {
        return getMaintenanceServer(server).getWhitelists().contains(playerName);
    }

    public MaintenanceServer getMaintenanceServer(DNServer server) {
        return getWhitelists().get(server.getName());
    }

}
