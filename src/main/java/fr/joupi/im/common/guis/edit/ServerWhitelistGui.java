package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.api.request.RequestType;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.Gui;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerWhitelistGui extends Gui<InterfaceManager> {

    @Getter
    private final DNServer server;

    @Getter
    private boolean fromStatic;

    public ServerWhitelistGui(InterfaceManager plugin, DNServer server) {
        super(plugin, "&7» &eMaintenance", 5);
        this.fromStatic = false;
        this.server = server;
    }

    public ServerWhitelistGui(InterfaceManager plugin, DNServer server, boolean fromStatic) {
        this(plugin, server);
        this.fromStatic = fromStatic;
    }

    @Override
    public void setup() {
        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        setItem(20, new GuiButton(new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem()).setName("&7» &eJoueurs").addLore(" ", "&7Clic pour voir la liste", "&7joueurs dans la maintenance").build()));

        setItem(22, getServer().getRemoteService().getPlayers().isEmpty() ? new GuiButton(new ItemBuilder(Material.GOLD_BLOCK).setName("&7» &cDésactiver").addLore(" ", "&7La maintenance est &aactivé", "&7Clic pour &cdésactiver &7la maintenance").build(),
                event -> {
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez désactiver la maintenance du serveur &b" + getServer().getName());
                })
                : new GuiButton(new ItemBuilder(Material.COAL_BLOCK).setName("&7» &aActiver").addLore(" ", "&7La maintenance est &cdésactiver", "&7Clic pour &aactiver &7la maintenance").build(),
                event -> {
                    //DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.CORE_START_SERVER, getServer().getName());
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez activer la maintenance du serveur &b" + getServer().getName());
                }));

        setItem(24, new GuiButton(new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7» &aAjouter un joueur").build()));

        setItem(44, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour à l'édition").build(),
                event -> {
                    if (isFromStatic()) new ServerStaticEditGui(getPlugin(), getServer()).onOpen((Player) event.getWhoClicked());
                    else new ServerEditGui(getPlugin(), getServer()).onOpen((Player) event.getWhoClicked());
                }));
    }

}
