package fr.joupi.im.common.maintenance;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.List;

@Getter
@Setter
public class MaintenanceServer {

    private final String serverName;
    private List<String> whitelists;
    private boolean isWhitelisted;

    public MaintenanceServer(String serverName, List<String> whitelists, boolean isWhitelisted) {
        this.serverName = serverName;
        this.whitelists = whitelists;
        this.isWhitelisted = isWhitelisted;
    }

    public void setWhitelisted(boolean whitelisted) {
        isWhitelisted = whitelisted;
        Bukkit.setWhitelist(whitelisted);
    }
}
