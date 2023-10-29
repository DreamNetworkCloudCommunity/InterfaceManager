package fr.joupi.im.common.guis;

import be.alexandre01.dnplugin.api.objects.RemoteExecutor;
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

    private final RemoteExecutor service;
    private final DNServer dnServer;

    public ServerStaticGui(InterfaceManager plugin, RemoteExecutor service) {
        super(plugin, "&7» &a" + service.getName().split("/")[1], 5);
        this.service = service;
        this.dnServer = service.getServers().get(1);
    }

    @Override
    public void setup() {
        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        setItem(4, !getService().isStarted() ?
                new GuiButton(new ItemBuilder(Material.PAPER).setAmount(0).setName("&7» &a" + getService().getName().split("/")[1]).addLore("", "&7État: &cOff", "&7Version: &cN/A", "&7Joueurs: &b0", "", "&7(Serveur éteint)").build()) :
                new GuiButton(new ItemBuilder(Material.PAPER).setAmount(getDnServer().getPlayers().size()).setName("&a" + getService().getName().split("/")[1]).addLore("", "&7État: &aOn", "&7Version: &b" + Utils.getServerVersion(getDnServer()), "&7Joueurs: &b" + getDnServer().getPlayers().size(), "", "&eClic gauche pour se téléporter").build(),
                        event -> {
                            if (Utils.findPlayer((Player) event.getWhoClicked()).getServer().getName().equals(getDnServer().getName()))
                                Utils.sendMessages((Player) event.getWhoClicked(), "&aVous êtes déjà sur le serveur &b" + getDnServer().getName().split("/")[1]);
                            else
                                DNSpigotAPI.getInstance().sendPlayerTo((Player) event.getWhoClicked(), getDnServer());
                        }));

        setItem(21, getService().isStarted() ? new GuiButton(new ItemBuilder(Material.GOLD_BLOCK).setName("&7» &cÉteindre").addLore(" ", "&7Le serveur est &aallumer", "&7Clic pour &céteindre &7le serveur").build(),
                event -> {
                    getDnServer().stop();
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez éteint le serveur &b" + getService().getName().split("/")[1]);
                })
                : new GuiButton(new ItemBuilder(Material.COAL_BLOCK).setName("&7» &aAllumer").addLore(" ", "&7Le serveur est &céteint", "&7Clic pour &aallumer &7le serveur").build(),
                event -> {
                    getPlugin().get().getMessageManager().sendStartServerMessage(getService().getName());
                    close((Player) event.getWhoClicked());
                    Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez démarrer le serveur &b" + getService().getName().split("/")[1]);
                }));

        setItem(23, !getService().isStarted() ? new GuiButton(new ItemBuilder(Material.ANVIL).setName("&7» &cModification impossible").build()) :
                                           new GuiButton(new ItemBuilder(Material.ANVIL).setName("&7» &cModifier").build(), event -> new ServerStaticEditGui(getPlugin(), getDnServer()).onOpen((Player) event.getWhoClicked())));

        setItem(44, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour à liste des catégories").build(),
                event -> new ServerCategoryGui(getPlugin()).onOpen((Player) event.getWhoClicked())));
    }
/*
⡀⡀⡀⣸⡿⠛⠁⡀⡀⠰⠛⢋⣩⣵⣶⣦⠂⢾⣿⣿⣮⡻⣿⣿⣿⣿⠛⣿⣿⣿
⡀⡀⢠⣿⠏⡀⡀⡀⡀⡀⠰⢿⡿⠟⣋⣤⣤⣀⠙⣿⣿⣿⣿⣻⢛⠁⡀⢹⣿⣿
⡀⡀⣾⣿⡀⢀⡀⡀⡀⡀⠐⢁⣴⣿⣿⣿⣿⣿⣷⣌⠻⠋⠫⠛⠎⢇⡀⠈⣿⣿
⡀⣼⣿⣿⡀⠸⡀⡀⡀⡀⣰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆⡀⡀⠡⡀⡀⡀⡀⢹⣿
⣰⣿⣿⡟⡀⢠⣄⡀⡀⣰⣯⣭⣝⡻⠿⣿⣿⣿⣿⣿⣿⡆⡀⡀⡀⡀⡀⡀⡀⢿
⣿⣿⣿⠁⡀⡞⣴⡅⡀⣉⢁⢒⡂⠢⢕⠪⠛⠿⠋⣁⣐⣚⡀⡀⡀⡀⡀⡀⡀⢸
⠛⣿⡏⡀⡀⢡⡇⢴⣸⣿⠘⣷⣦⣶⡷⢠⣿⡀⣿⣄⣠⣖⡑⡀⡀⡀⡀⡀⡀⡀
⠾⣿⡇⡀⢀⠤⠵⣾⣿⣿⣧⣭⣛⣛⣵⣿⣿⡆⠻⣿⣿⠟⣁⢄⡀⡀⡀⡀⡀⡀
⡀⣿⡇⡀⢎⢾⣿⢼⢹⣿⣿⣿⣿⣿⣿⣛⡿⢇⡱⣶⣶⣾⠏⣬⡳⡀⡀⡀⡀⡀
⡀⠸⡇⡀⠈⠓⠒⠋⡀⡿⣿⣏⣤⠤⠒⠦⣚⡒⢄⠛⢿⠏⡸⠿⢣⣧⡀⡀⢀⣼
⡀⡀⡀⡀⡀⡀⡀⡀⡀⡇⠈⢿⣿⣿⣮⣛⣒⣲⣿⡷⢪⣾⣿⣿⣿⣿⡀⢠⣿⣿
⡀⡀⡀⡀⡀⡀⡀⡀⢰⡇⡀⡀⠙⠿⠋⠉⠋⠿⣋⣴⣿⣿⣿⣿⣿⣿⢠⣿⣿⣿
⡀⡀⡀⡀⡀⡀⣀⣴⣿⡇⡀⡀⡀⡀⠈⠢⠔⠁⢿⣿⣿⣿⣿⣿⣿⢇⣿⣿⣿⣿
⣀⣀⣀⣀⣤⣾⣿⠟⠁⠳⣄⡀⡀⡀⡀⡀⡀⡀⠈⢿⣿⣿⣿⣿⣿⢸⣿⣿⣿⣿
 */
}
