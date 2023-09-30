package fr.joupi.im.common.guis;

import be.alexandre01.dnplugin.api.NetworkBaseAPI;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.buttons.ServerCategoryButton;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.gui.PageableGui;
import fr.joupi.im.utils.gui.ValidateGui;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import fr.joupi.im.utils.item.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerCategoryGui extends PageableGui<InterfaceManager, ServerCategoryButton> {

    public ServerCategoryGui(InterfaceManager plugin) {
        super(plugin, "&7» &eCatégories", 6, 10);

        NetworkBaseAPI.getInstance().getServices().values().stream()
                .filter(remoteService -> !remoteService.getRemoteBundle().isProxy())
                .forEach(service -> getPagination().addElement(new ServerCategoryButton(plugin, service)));
    }

    @Override
    public void setup() {
        for (int i = 0; i < getPage().countElements(); i++) {
            if (i <= 4)
                setItem(20 + i, getPage().getElements().get(i));
            else
                setItem(24 + i, getPage().getElements().get(i));
        }

        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        setItem(4, new GuiButton(new ItemBuilder(Material.PAPER).setName("&7» &bInformations").addLore("", "&7Catégories: &b" + Utils.getOnlineServiceCount(), "&7Serveurs: &b" + Utils.getOnlineServerCount(), "&7Joueurs: &b" + Utils.getOnlinePlayerCount()).build()));

        setItem(46, stopAllServerButton());

        setItem(48, previousPageButton());

        setItem(50, nextPageButton());

        setItem(53, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cFermer").build(),
                event -> close((Player) event.getWhoClicked())));

    }

    @Override
    public GuiButton nextPageButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getRightArrowSkull()).setName("&aSuivant").build(), event -> {
            if (getPagination().hasNext(getPage()))
                updatePage(getPagination().getNext(getPage()));
        });
    }

    @Override
    public GuiButton previousPageButton() {
        return new GuiButton(new ItemBuilder(SkullBuilder.getLeftArrowSkull()).setName("&cRetour").build(), event -> {
            if (getPagination().hasPrevious(getPage()))
                updatePage(getPagination().getPrevious(getPage()));
        });
    }

    private GuiButton stopAllServerButton() {
        return new GuiButton(new ItemBuilder(Material.BARRIER).setName("&7» &cÉteindre tout les serveurs").build(),
                event ->
                    new ValidateGui<>(getPlugin(), "&7» &cÉteindre tout les serveurs", XMaterial.CYAN_STAINED_GLASS_PANE.parseItem(), new ItemBuilder(Material.PAPER).setName("&7» &bInformations").addLore("", "&7Êtes vous sûrs de vouloir &céteindre", "&7tout les serveurs en ligne ?").build(), this,
                            () -> {
                                Utils.getServices().forEach(remoteService -> remoteService.getServers().values().forEach(DNServer::stop));
                                close((Player) event.getWhoClicked());
                    }).onOpen((Player) event.getWhoClicked()));
    }
/*
⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⢛⣋⣩⣭⣭⣭⣭⣭⣙⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⡁⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡜⣿⠟⣛⡛⣿⣿⣿⣿⣿
⣿⣿⠿⢿⢿⠿⣿⣿⢱⣿⣿⣿⣿⣿⣿⣿⡿⠿⠋⠉⠁⢠⣾⠟⣴⣿⣿⣿⣿⣿
⣿⠫⠢⣴⣮⣅⠲⣭⡘⠛⢁⠄⠄⠈⠉⢛⡄⣠⠶⠷⠦⡜⣣⢸⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣶⡙⢿⣷⣦⢱⠰⡫⢽⡍⠩⡆⢠⡄⢃⠺⠁⠗⠄⡟⣸⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣎⢻⣿⡸⣦⡙⠷⠾⢛⣡⣿⣿⣶⣌⣭⣤⢂⢠⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣷⣍⡱⢻⡿⠛⠛⠓⠛⢿⣿⡟⡈⠠⡄⢀⣾⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⡿⢋⡔⣰⡌⢧⣾⣦⣔⣃⠒⠐⢃⣡⣼⡟⣴⡝⢿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⡟⣰⣿⢰⣿⡇⡌⢿⣿⣿⣯⡉⠉⣹⣿⡿⡁⣿⡇⣮⡙⣿⣿⣿⣿⣿
⣿⣿⣿⡿⢱⣿⣿⢸⣿⡇⣿⣦⣝⡻⠿⠷⠾⠿⠿⣣⢃⣽⡇⣿⣧⢹⣿⣿⣿⣿
⣿⣿⣿⢣⣿⣿⣿⢸⣿⡇⠛⠩⠭⢭⣙⠻⢿⣿⣿⣿⢸⣿⢣⣿⣿⢸⣿⣿⣿⣿
⣿⡿⣡⣿⣭⣭⣍⣘⣫⢠⡶⠿⢛⣻⣿⣿⣦⣙⣛⠏⠾⡿⢸⣿⣿⢸⣿⣿⣿⣿
⣿⡇⣿⣿⣿⣿⣿⣿⣿⡆⠶⠟⣛⣍⣿⣿⣿⣿⣿⣿⣷⣦⣍⠻⣿⢸⣿⣿⣿⣿
 */
}
