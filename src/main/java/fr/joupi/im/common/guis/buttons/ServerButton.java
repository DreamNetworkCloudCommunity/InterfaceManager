package fr.joupi.im.common.guis.buttons;

import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.common.guis.edit.ServerEditGui;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ServerButton extends GuiButton {

    public ServerButton(InterfaceManager plugin, DNPlayer dnPlayer, DNServer server)  {
        setItemStack(getServerItem(dnPlayer, server));

        setClickEvent(event -> {
            Player player = (Player) event.getWhoClicked();

            if (event.getClick().isRightClick()) {
                new ServerEditGui(plugin, server).onOpen(player);
                return;
            }

            if (server.getPlayers().stream().map(DNPlayer::getName).noneMatch(s -> s.equals(player.getName())))
                DNSpigotAPI.getInstance().sendPlayerTo(player, server);
            else
                Utils.sendMessages(player, "&aVous êtes déjà connecté au serveur &b" + server.getFullName());
        });
    }

    public ItemStack getServerItem(DNPlayer dnPlayer, DNServer server) {
        return dnPlayer.getServer().getFullName().equals(server.getFullName()) ?
                new ItemBuilder(Material.PAPER).setAmount(server.getPlayers().size()).setName("&7» &a" + server.getFullName()).addLore("", "&7État: " + (server.getRemoteService().isStarted() ? "&aOn" : "&cOff"), "&7Version: &b" + Utils.getServerVersion(server), "&7Joueurs: &b" + server.getPlayers().size(), "", "&eClic gauche pour se téléporter", "&eClic droit pour modifier", "&7(Vous êtes connecté sur ce serveur)").build() :
                new ItemBuilder(Material.PAPER).setAmount(server.getPlayers().size()).setName("&7» &a" + server.getFullName()).addLore("", "&7État: " + (server.getRemoteService().isStarted() ? "&aOn" : "&cOff"), "&7Version: &b" + Utils.getServerVersion(server), "&7Joueurs: &b" + server.getPlayers().size(), "", "&eClic gauche pour se téléporter", "&eClic droit pour modifier").build();
    }

}
