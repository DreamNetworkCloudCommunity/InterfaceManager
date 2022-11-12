package fr.joupi.im.common.guis;

import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.buttons.ServerCategoryButton;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.gui.PageableGui;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerCategoryGui extends PageableGui<InterfaceManager, ServerCategoryButton> {

    public ServerCategoryGui(InterfaceManager plugin) {
        super(plugin, "&7» &eCatégories", 6, 10);

        DNSpigotAPI.getInstance().getServices()
                .values()
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

        setItems(getBorders(), new ItemBuilder(Material.STAINED_GLASS_PANE).setName("&a").setDyeColor(DyeColor.CYAN).build());

        setItem(48, previousPageButton());

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

}
