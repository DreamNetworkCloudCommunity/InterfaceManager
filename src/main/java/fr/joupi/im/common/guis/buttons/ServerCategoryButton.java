package fr.joupi.im.common.guis.buttons;

import be.alexandre01.dnplugin.api.objects.RemoteService;
import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.plugins.spigot.DNSpigot;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.ServerListGui;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ServerCategoryButton extends GuiButton {

    public ServerCategoryButton(InterfaceManager plugin, RemoteService category)  {
        super(new ItemBuilder(Material.ENDER_PEARL).setName("&b" + StringUtils.capitalize(category.getName()))
                .addLore("", "&7Serveurs: &b" + category.getServers().values().size(), "&7Joueurs: &b" + Utils.getOnlinePlayerCount(category)).build());

        setClickEvent(event -> {
            Player player = (Player) event.getWhoClicked();

            new ServerListGui(plugin, Utils.findPlayer(player), category).onOpen(player);
        });
    }

}
