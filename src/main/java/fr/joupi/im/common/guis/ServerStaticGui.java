package fr.joupi.im.common.guis;

import be.alexandre01.dnplugin.api.objects.RemoteService;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.common.guis.edit.ServerStaticEditGui;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.Gui;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class ServerStaticGui extends Gui<InterfaceManager> {

    private final RemoteService server;
    private final DNServer dnServer;

    public ServerStaticGui(InterfaceManager plugin, RemoteService server) {
        super(plugin, "&7» &a" + server.getName().split("/")[1], 5);
        this.server = server;
        this.dnServer = server.getServers().get(0);
    }

    @Override
    public void setup() {
        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        setItem(4, !getServer().isStarted() ?
                new GuiButton(new ItemBuilder(Material.PAPER).setAmount(0).setName("&7» &a" + getServer().getName().split("/")[1]).addLore("", "&7État: &cOff", "&7Version: &cN/A", "&7Joueurs: &b0", "", "&7(Serveur éteint)").build()) :
                new GuiButton(new ItemBuilder(Material.PAPER).setAmount(getDnServer().getPlayers().size()).setName("&a" + getServer().getName().split("/")[1]).addLore("", "&7État: &aOn", "&7Version: &b" + Utils.getServerVersion(getDnServer()), "&7Joueurs: &b" + getDnServer().getPlayers().size(), "", "&eClic gauche pour se téléporter").build(),
                        event -> {
                            if (Utils.findPlayer((Player) event.getWhoClicked()).getServer().getFullName().equals(getDnServer().getFullName()))
                                Utils.sendMessages((Player) event.getWhoClicked(), "&aVous êtes déjà sur le serveur &b" + getDnServer().getFullName());
                            else
                                DNSpigotAPI.getInstance().sendPlayerTo((Player) event.getWhoClicked(), getDnServer());
                        }));

        setItem(21, getServer().isStarted() ? new GuiButton(new ItemBuilder(Material.GOLD_BLOCK).setName("&7» &cÉteindre").addLore(" ", "&7Le serveur est &aallumer", "&7Clic pour &céteindre &7le serveur").build(),
                event -> {
                    getDnServer().stop();
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez éteint le serveur &b" + getServer().getName().split("/")[1]);
                })
                : new GuiButton(new ItemBuilder(Material.COAL_BLOCK).setName("&7» &aAllumer").addLore(" ", "&7Le serveur est &céteint", "&7Clic pour &aallumer &7le serveur").build(),
                event -> {
                    getPlugin().get().getMessageManager().sendStartServerMessage(getServer().getName());
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez démarrer le serveur &b" + getServer().getName());
                }));

        setItem(23, !getServer().isStarted() ? new GuiButton(new ItemBuilder(Material.ANVIL).setName("&7» &cModification impossible").build()) :
                                           new GuiButton(new ItemBuilder(Material.ANVIL).setName("&cModifier").build(), event -> new ServerStaticEditGui(getPlugin(), getDnServer()).onOpen((Player) event.getWhoClicked())));

        setItem(44, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour à liste des catégories").build(),
                event -> new ServerCategoryGui(getPlugin()).onOpen((Player) event.getWhoClicked())));
    }
}
