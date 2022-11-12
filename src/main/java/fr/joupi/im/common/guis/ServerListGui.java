package fr.joupi.im.common.guis;

import be.alexandre01.dnplugin.api.objects.RemoteService;
import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.api.request.RequestType;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.buttons.ServerButton;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.gui.PageableGui;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerListGui extends PageableGui<InterfaceManager, GuiButton> {

    @Getter
    private final RemoteService category;

    public ServerListGui(InterfaceManager plugin, DNPlayer player, RemoteService category) {
        super(plugin, "&7» &e" + StringUtils.capitalize(category.getName()), 6, 21);
        this.category = category;

        DNSpigotAPI.getInstance().getServices()
                .get(category.getName())
                .getServers()
                .values()
                .forEach(server -> getPagination().addElement(new ServerButton(plugin, player, server)));
    }

    @Override
    public void setup() {
        setItems(getBorders(), new ItemBuilder(Material.STAINED_GLASS_PANE).setName("&a").setDyeColor(DyeColor.CYAN).build());

        getPage().getElements().forEach(this::addItem);

        setItem(46, stopAllServerButton());

        setItem(48, previousPageButton());

        setItem(49, addNewServerButton());

        setItem(50, nextPageButton());

        setItem(53, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&cFermer").build(),
                event -> close((Player) event.getWhoClicked())));
    }

    @Override
    public GuiButton nextPageButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getRightArrowSkull()).setName("&aSuivant").build(), event -> {
            if (!getPagination().hasNext(getPage())) return;

            updatePage(getPagination().getNext(getPage()));
        });
    }

    @Override
    public GuiButton previousPageButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getLeftArrowSkull()).setName("&cRetour").build(), event -> {
            if (!getPagination().hasPrevious(getPage())) return;

            updatePage(getPagination().getPrevious(getPage()));
        });
    }

    private GuiButton stopAllServerButton() {
        return new GuiButton(new ItemBuilder(Material.BARRIER).setName("&cÉteindre tout les serveurs").build(), event -> {
            getCategory().getServers().values().forEach(DNServer::stop);
            close((Player) event.getWhoClicked());
            Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez éteint tout les serveurs de la catégorie &b" + getCategory().getName());
        });
    }

    private GuiButton addNewServerButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getPlusSkull()).setName("&eDémarrer un nouveau serveur").build(), event -> {
            DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.CORE_START_SERVER, getCategory().getName());
            close((Player) event.getWhoClicked());
            Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez démarrer un nouveau serveur de type &b" + getCategory().getName());
        });
    }

}
