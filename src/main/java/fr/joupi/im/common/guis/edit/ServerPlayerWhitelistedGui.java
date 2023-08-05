package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.edit.buttons.PlayerWhitelistButton;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.gui.PageableGui;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class ServerPlayerWhitelistedGui extends PageableGui<InterfaceManager, PlayerWhitelistButton> {

    private final DNServer server;
    private final boolean fromStatic;

    public ServerPlayerWhitelistedGui(InterfaceManager plugin, DNServer server, boolean fromStatic) {
        super(plugin, "&7» &eJoueurs &7(" + plugin.get().getMaintenanceManager().getMaintenanceServer(server).getWhitelists().size() + ")", 6, 21);
        this.server = server;
        this.fromStatic = fromStatic;

        getPlugin().get().getMaintenanceManager()
                .getMaintenanceServer(server)
                .getWhitelists().forEach(s -> getPagination().addElement(new PlayerWhitelistButton(plugin, server, s, fromStatic)));
    }

    @Override
    public void setup() {
        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        getPage().getElements().forEach(this::addItem);

        setItem(48, previousPageButton());

        setItem(50, nextPageButton());

        setItem(53, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour à l'édition").build(),
                event -> new ServerWhitelistGui(getPlugin(), getServer(), isFromStatic()).onOpen((Player) event.getWhoClicked())));
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