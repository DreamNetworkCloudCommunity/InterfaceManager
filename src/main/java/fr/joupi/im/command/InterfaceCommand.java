package fr.joupi.im.command;

import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.ServerCategoryGui;
import fr.joupi.im.utils.command.annotation.Command;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class InterfaceCommand<P extends InterfaceManager> {

    private final P plugin;

    @Command(name = "im", permission = "im.admin")
    public void execute(Player player) {
        new ServerCategoryGui(plugin).onOpen(player);
    }

}
