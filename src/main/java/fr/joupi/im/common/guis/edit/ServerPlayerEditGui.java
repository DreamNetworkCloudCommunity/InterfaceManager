package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.api.request.RequestType;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import be.alexandre01.dnplugin.utils.messages.Message;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.Utils;
import fr.joupi.im.utils.gui.Gui;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class ServerPlayerEditGui extends Gui<InterfaceManager> {

    private final DNServer server;
    private final DNPlayer dnPlayer;

    private boolean fromStatic;

    public ServerPlayerEditGui(InterfaceManager plugin, DNServer server, DNPlayer dnPlayer) {
        super(plugin, "&7» &e" + dnPlayer.getName(), 6);
        this.server = server;
        this.dnPlayer = dnPlayer;
        this.fromStatic = false;
    }

    public ServerPlayerEditGui(InterfaceManager plugin, DNServer server, DNPlayer dnPlayer, boolean fromStatic) {
        this(plugin, server, dnPlayer);
        this.fromStatic = fromStatic;
    }

    @Override
    public void setup() {
        setItems(getBorders(), new GuiButton(XMaterial.CYAN_STAINED_GLASS_PANE.parseItem()));

        setItem(4, new GuiButton(new ItemBuilder(SkullBuilder.withSkullOwner(getDnPlayer().getName())).setName("&7» &b" + getDnPlayer().getName()).addLore(" ", "&7ID: &b" + getDnPlayer().getId()).build()));

        setItem(20, kickPlayerButton());

        setItem(22, teleportToPlayerButton());

        setItem(53, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour à la liste de joueurs").build(),
                event -> {
                    if (isFromStatic()) new ServerPlayerListGui(getPlugin(), getServer(), isFromStatic()).onOpen((Player) event.getWhoClicked());
                    else new ServerPlayerListGui(getPlugin(), getServer(), false).onOpen((Player) event.getWhoClicked());
                }));
    }

    private GuiButton kickPlayerButton() {
        return new GuiButton(new ItemBuilder(Material.BARRIER).setName("&7» &cKick le joueur").build(), event -> {
            DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.CORE_RETRANSMISSION, new Message().set("IMOrder", "kickPlayer").set("playerName", getDnPlayer().getName()), getServer().getFullName());
            close((Player) event.getWhoClicked());
            Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez kick " + getDnPlayer().getName() + " du serveur &b" + getServer().getFullName());
        });
    }

    private GuiButton teleportToPlayerButton() {
        return new GuiButton(new ItemBuilder(Material.COMPASS).setName("&7» &eTéléportation vers ce joueur").build(), event -> {
            if (Utils.findPlayer((Player) event.getWhoClicked()).getServer().getFullName().equals(getDnPlayer().getServer().getFullName())) {
                close((Player) event.getWhoClicked());
                event.getWhoClicked().teleport(Bukkit.getPlayer(getDnPlayer().getName()));
            } else {
                DNSpigotAPI.getInstance().sendPlayerTo((Player) event.getWhoClicked(), getDnPlayer().getServer().getFullName());
                DNSpigotAPI.getInstance().getRequestManager().sendRequest(RequestType.CORE_RETRANSMISSION, new Message().set("IMOrder", "teleportPlayer").set("playerName", event.getWhoClicked().getName()).set("targetPlayerName", getDnPlayer().getName()), getServer().getFullName());
            }

            Utils.sendMessages((Player) event.getWhoClicked(), "&aVous avez été téléporter vers &e" + getDnPlayer().getName() + " &asur le serveur &b" + getServer().getFullName());
        });
    }

}
