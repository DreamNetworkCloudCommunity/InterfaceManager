package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.Gui;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class ServerWhitelistGui extends Gui<InterfaceManager> {

    private final DNServer server;
    private final boolean fromStatic;

    public ServerWhitelistGui(InterfaceManager plugin, DNServer server, boolean fromStatic) {
        super(plugin, "&7» &eMaintenance", 5);
        this.fromStatic = fromStatic;
        this.server = server;
    }

    @Override
    public void setup() {

        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        setItem(20, new GuiButton(new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem()).setName("&7» &eJoueurs").addLore(" ", "&7Clic pour voir la liste des", "&7joueurs dans la maintenance").build(),
                event -> new ServerPlayerWhitelistedGui(getPlugin(), getServer(), isFromStatic()).onOpen((Player) event.getWhoClicked())));

        setItem(22, getPlugin().get().getMaintenanceManager().getMaintenanceServer(getServer()).isWhitelisted() ? new GuiButton(new ItemBuilder(Material.GOLD_BLOCK).setName("&7» &cDésactiver").addLore(" ", "&7La maintenance est &aactivé", "&7Clic pour &cdésactiver &7la maintenance").build(),
                event -> {
                    getPlugin().get().getMaintenanceManager().updateServerWhitelistStatus(getServer().getName(), false);
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez désactiver la maintenance du serveur &b" + getServer().getName().split("/")[1]);
                    refresh();
                })
                : new GuiButton(new ItemBuilder(Material.COAL_BLOCK).setName("&7» &aActiver").addLore(" ", "&7La maintenance est &cdésactiver", "&7Clic pour &aactiver &7la maintenance").build(),
                event -> {
                    getPlugin().get().getMaintenanceManager().updateServerWhitelistStatus(getServer().getName(), true);
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez activer la maintenance du serveur &b" + getServer().getName().split("/")[1]);
                    refresh();
                }));

        setItem(24, new GuiButton(new ItemBuilder(Material.BOOK_AND_QUILL).setName("&7» &aAjouter un joueur").build(),
                event -> {
                    getPlugin().get().getChatCache().put(event.getWhoClicked().getUniqueId(), getServer());
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVeuillez écrire dans le chat le nom du joueur a ajouter dans la maintenance du serveur &b" + getServer().getName().split("/")[1] + " &a pour annuler cette opération tapez &c!cancel");
                }));

        setItem(44, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour à l'édition").build(),
                event -> {
                    if (isFromStatic())
                        new ServerStaticEditGui(getPlugin(), getServer()).onOpen((Player) event.getWhoClicked());
                    else
                        new ServerEditGui(getPlugin(), getServer()).onOpen((Player) event.getWhoClicked());
                }));
    }

}
