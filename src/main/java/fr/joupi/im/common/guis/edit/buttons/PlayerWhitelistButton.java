package fr.joupi.im.common.guis.edit.buttons;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.common.guis.edit.ServerPlayerWhitelistedGui;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.XMaterial;
import org.bukkit.entity.Player;

public class PlayerWhitelistButton extends GuiButton {

    public PlayerWhitelistButton(InterfaceManager plugin, DNServer server, String playerName, boolean fromStatic) {
        super(new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem()).setName("&7» &b" + playerName).addLore("", "&7Clic pour supprimer ce", "&7joueur de la maintenance").build(),
                event -> {
                    plugin.get().getMaintenanceManager().removePlayerInWhitelist(server, playerName);
                    new ServerPlayerWhitelistedGui(plugin, server, fromStatic).onOpen((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez supprimer &e" + playerName + " &ade la maintenance du serveur &b" + server.getFullName());
        });
    }

}
