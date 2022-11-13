package fr.joupi.im.common.guis.edit;

import be.alexandre01.dnplugin.api.objects.server.DNServer;
import fr.joupi.im.InterfaceManager;
import fr.joupi.im.utils.gui.Gui;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;

public class ServerWhitelistGui extends Gui<InterfaceManager> {

    @Getter
    private final DNServer server;

    public ServerWhitelistGui(InterfaceManager plugin, DNServer server) {
        super(plugin, "&7» &eMaintenance", 6);
        this.server = server;
    }

    @Override
    public void setup() {
        setItems(getBorders(), XMaterial.CYAN_STAINED_GLASS_PANE.parseItem());
    }

}
