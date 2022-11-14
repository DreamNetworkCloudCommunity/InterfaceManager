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

    @Command(name = "im", aliases = {"interfacemanager"}, permission = "im.command.menu")
    public void execute(Player player) {
        new ServerCategoryGui(plugin).onOpen(player);
    }

    @SubCommand(name = "list", parent = "im", permission = "im.command.list")
    public void executeList(CommandSender commandSender) {
        commandSender.sendMessage(Utils.coloredText("&7» &eListe des serveurs : "));

        DNSpigotAPI.getInstance().getServices().values().forEach(
                service -> {
                    commandSender.sendMessage(Utils.coloredText(" "));
                    commandSender.sendMessage(Utils.coloredText(" &7• &6" + service.getName()));
                    service.getServers().values().forEach(
                            server -> commandSender.sendMessage(Utils.coloredText(" &7 &7- &a" + server.getFullName() + " &7| &b" + Utils.getServerVersion(server) + " &7(" + server.getPlayers().size() + ")")));
                });
    }

}
