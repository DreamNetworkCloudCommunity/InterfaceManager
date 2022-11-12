package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.api.request.RequestType;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dnplugin.utils.messages.Message;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.edit.buttons.PlayerButton;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.gui.PageableGui;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerPlayerListGui extends PageableGui<InterfaceManager, PlayerButton> {

    @Getter
    private final DNServer server;

    protected ServerPlayerListGui(InterfaceManager plugin, DNServer server) {
        super(plugin, "&7» &eJoueurs &7(" + server.getPlayers().size() + "&7)", 6, 21);
        this.server = server;

        server.getPlayers()
                .forEach(dnPlayer -> getPagination().addElement(new PlayerButton(dnPlayer)));
    }

    @Override
    public void setup() {
        setItems(getBorders(), new ItemBuilder(Material.STAINED_GLASS_PANE).setName("&a").setDyeColor(DyeColor.CYAN).build());

        getPage().getElements().forEach(this::addItem);

        setItem(46, kickAllPlayersButton());

        setItem(48, previousPageButton());

        setItem(50, nextPageButton());

        setItem(53, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&cRetour à l'édition").build(),
                event -> new ServerEditGui(getPlugin(), getServer()).onOpen((Player) event.getWhoClicked())));
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

    private GuiButton kickAllPlayersButton() {
        return new GuiButton(new ItemBuilder(Material.BARRIER).setName("&cKick tout les joueurs").build(), event -> {
            //getPlugin().get().getDnChannel().sendMessage(new Message().set("instruction", "kickall").set("serverTarget", getServer().getFullName()));
            //getServer().sendMessage(new Message().set("instruction", "kickall").set("serverTarget", getServer().getFullName()));
            DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.CORE_RETRANSMISSION, new Message().set("instruction", "kickall").set("serverTarget", getServer().getFullName()), getServer().getFullName());

            close((Player) event.getWhoClicked());
            Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez kick tout les joueurs du serveur &b" + getServer().getFullName());
        });
    }

}
