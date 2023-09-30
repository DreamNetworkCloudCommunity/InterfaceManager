package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.common.guis.ServerStaticGui;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.gui.Gui;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class ServerStaticEditGui extends Gui<InterfaceManager> {

    private final DNServer server;

    public ServerStaticEditGui(InterfaceManager plugin, DNServer server) {
        super(plugin, "&7» &a" + server.getFullName().split("/")[1], 5);
        this.server = server;
    }

    @Override
    public void setup() {
        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());

        setItem(21, new GuiButton(new ItemBuilder(Material.PAINTING).setName("&7» &eMaintenance").build(),
                event -> new ServerWhitelistGui(getPlugin(), getServer(), true).onOpen((Player) event.getWhoClicked())));

        setItem(23, new GuiButton(new ItemBuilder(Material.CHEST).setName("&7» &6Joueurs").build(),
                event -> new ServerPlayerListGui(getPlugin(), getServer(), true).onOpen((Player) event.getWhoClicked())));

        setItem(40, new GuiButton(new ItemBuilder(Material.WOOD_DOOR).setName("&7» &cRetour").build(),
                event -> new ServerStaticGui(getPlugin(), getServer().getRemoteService()).onOpen((Player) event.getWhoClicked())));
    }
/*
⣿⣿⣿⡇⣼⣿⣿⡙⢇⡀⡀⡀⡀⢋⠥⢒⣛⣉⠉⠙⠛⣿⣧⡩⡘⣿⣿⣿⣿⣿
⣿⣿⣿⢠⣿⡿⣱⣬⣌⡂⢀⣼⣠⣴⣾⣿⣿⣿⣿⡄⡀⣿⡟⢳⡁⢻⣿⣿⣿⣿
⣿⣿⣿⠘⣿⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡀⢫⡟⡀⠑⢸⣿⣿⣿⣿
⣿⣿⣿⠇⢿⣧⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⣿⣆⠘⢀⣤⣆⠈⣿⣿⣿⣿
⣿⣿⡇⢰⣿⡏⠤⠙⠿⣿⣿⣿⣿⣿⡿⠛⢋⣁⡐⠒⠬⠆⣾⢻⡿⡀⢸⣿⣿⣿
⣿⣿⣷⡜⠛⢠⡒⣓⡒⠤⡈⢉⣉⡁⢠⢖⡉⢀⡬⢡⠐⣤⣉⢩⡇⡀⡀⣿⣿⣿
⣿⣿⣿⣷⢰⠸⣿⣌⣓⣛⡄⢸⣿⣧⠐⣮⣭⣭⣴⡟⢰⣿⣿⣾⠁⡀⡀⢻⣿⣿
⣿⣿⣿⡇⣼⣦⠙⠿⠿⠛⣡⢻⣿⣿⢧⣈⠛⠛⣉⣴⣿⡿⠁⠳⡀⢰⡀⠘⣿⣿
⣿⣿⣿⢡⣿⠏⠘⣿⣿⣿⣇⠺⣿⡿⢃⣿⣿⣿⣿⣿⣿⡇⡀⡀⡇⢸⡇⢀⣿⣿
⣿⣿⡏⣼⣿⢸⣇⡜⠋⢁⣀⢘⡲⣚⣂⠠⢀⣀⣈⣙⠏⡆⡀⡀⡇⢸⢸⠈⢿⣿
⣿⡿⢱⣿⣿⡘⠏⡲⠉⠻⣿⡝⠿⠿⠟⣡⣾⣿⡿⠁⡀⢳⣀⣰⠁⢸⣦⡇⠈⣿
⣿⢣⣿⣿⣿⣿⢠⡶⡀⡀⠘⡿⠿⠿⠟⠿⣿⠏⢐⡀⡀⡀⢻⡃⡀⣸⣿⣷⡀⢹
⢫⣾⣿⣯⣿⣿⣼⠇⡀⡀⡀⠘⢄⡀⣠⠔⠃⡀⢸⡀⡀⡀⠈⠃⡀⣿⣿⣿⡀⡀
⣿⣿⣿⣿⣿⣿⡿⡀⡀⡀⡀⢀⢆⣭⣝⢆⡀⡀⢸⡀⡀⡀⡀⡀⢀⣿⣿⣿⡄⡀
 */
}
