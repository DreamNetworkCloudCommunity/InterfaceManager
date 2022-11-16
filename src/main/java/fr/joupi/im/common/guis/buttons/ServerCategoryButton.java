package fr.joupi.im.common.guis.buttons;

import be.alexandre01.dnplugin.api.objects.RemoteService;
import be.alexandre01.dnplugin.utils.Mods;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.ServerListGui;
import fr.joupi.im.common.guis.ServerStaticGui;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerCategoryButton extends GuiButton {

    public ServerCategoryButton(InterfaceManager plugin, RemoteService category)  {
        super(new ItemBuilder(Material.ENDER_PEARL).setName("&7» &b" + StringUtils.capitalize(category.getName()))
                .addLore("", "&7Serveurs: &b" + category.getServers().values().size(), "&7Joueurs: &b" + Utils.getOnlinePlayerCount(category), "&7Mode: &b" + StringUtils.capitalize(category.getMods().name().toLowerCase())).build());

        setClickEvent(event -> {
            Player player = (Player) event.getWhoClicked();

            if (category.getMods().equals(Mods.STATIC))
                new ServerStaticGui(plugin, category).onOpen(player);
            else
                new ServerListGui(plugin, Utils.findPlayer(player), category).onOpen(player);
        });
    }

}
