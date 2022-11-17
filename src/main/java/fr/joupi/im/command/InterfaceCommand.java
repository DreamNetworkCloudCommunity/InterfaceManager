package fr.joupi.im.command;

import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.ServerCategoryGui;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.command.annotation.Command;
import fr.joupi.im.utils.command.annotation.SubCommand;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Data
public class InterfaceCommand<P extends InterfaceManager> {

    private final P plugin;

    @Command(name = "im", aliases = {"interfacemanager"}, permission = "im.admin,im.command.menu")
    public void execute(Player player) {
        new ServerCategoryGui(plugin).onOpen(player);
        getPlugin().get().getPlayerInChatConfirmations().asMap().remove(player.getUniqueId());
    }

    @SubCommand(name = "list", parent = "im", permission = "im.admin,im.command.list")
    public void executeList(Player commandSender) {
        commandSender.sendMessage(Utils.coloredText("&7» &eListe des serveurs : "));

        commandSender.spigot().sendMessage();

        DNSpigotAPI.getInstance().getServices().values().forEach(
                service -> {
                    commandSender.sendMessage(Utils.coloredText(" "));
                    commandSender.sendMessage(Utils.coloredText(" &7• &6" + service.getName()));
                    service.getServers().values().forEach(
                            server -> commandSender.sendMessage(Utils.coloredText(" &7 &7- &a" + server.getFullName() + " &7| &b" + Utils.getServerVersion(server) + " &7(" + server.getPlayers().size() + ")")));
                });
    }

    /*@Command(name = "test")
    public void executeTest(Player player) {
        getPlugin().get().getMaintenanceManager().getWhitelists().forEach((s, maintenanceServer) -> player.sendMessage(s + " / " + maintenanceServer.getServerName() + " / " + maintenanceServer.isWhitelisted() + " / " + String.join(", ", maintenanceServer.getWhitelists())));
    }

    @Command(name = "test2")
    public void executeTest2(Player player) {
        getPlugin().get().getPlayerInChatConfirmations().asMap().forEach((uuid, server) -> player.sendMessage(uuid.toString() + " | " + server.getFullName()));
    }

    @Command(name = "forceupdate")
    public void executeTest3(Player player) {
        getPlugin().get().getMessageManager().sendMaintenanceUpdateMessage(new MaintenanceServer(Utils.getServerName(), Bukkit.getWhitelistedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.toList()), Bukkit.getServer().hasWhitelist()));
    }*/

}
