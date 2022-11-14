package fr.joupi.im.common.guis.edit.buttons;

import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.edit.ServerPlayerEditGui;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import org.bukkit.entity.Player;

public class PlayerButton extends GuiButton {

    public PlayerButton(InterfaceManager plugin, DNServer dnServer, DNPlayer player, boolean fromStatic) {
        super(new ItemBuilder(SkullBuilder.withSkullOwner(player.getName())).setName("&7» &b" + player.getName()).build(),
                event -> {
                    if (!event.getWhoClicked().getName().equals(player.getName()))
                        new ServerPlayerEditGui(plugin, dnServer, player, fromStatic).onOpen((Player) event.getWhoClicked());
        });
    }

}
