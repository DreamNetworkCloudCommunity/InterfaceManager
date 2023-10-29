package fr.joupi.im.common.guis.buttons;

import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.edit.ServerEditGui;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerButton extends GuiButton {

    public ServerButton(InterfaceManager plugin, DNServer server)  {
        setItemStack(new ItemBuilder(Material.PAPER).setAmount(server.getPlayers().size()).setName("&7» &a" + server.getName().split("/")[1])
                .addLore("", "&7État: " + (server.getRemoteExecutor().isStarted() ? "&aOn" : "&cOff"), "&7Version: &b" + Utils.getServerVersion(server), "&7Joueurs: &b" + server.getPlayers().size(), "", "&eClic gauche pour se téléporter", "&eClic droit pour modifier").build());

        setClickEvent(event -> {
            Player player = (Player) event.getWhoClicked();

            if (event.getClick().isRightClick()) {
                new ServerEditGui(plugin, server).onOpen(player);
                return;
            }

            if (server.getPlayers().stream().map(DNPlayer::getName).noneMatch(s -> s.equals(player.getName())))
                DNSpigotAPI.getInstance().sendPlayerTo(player, server);
            else
                Utils.sendMessages(player, "&aVous êtes déjà connecté au serveur &b" + StringUtils.capitalize(server.getName().split("/")[1]));
        });
    }

}
