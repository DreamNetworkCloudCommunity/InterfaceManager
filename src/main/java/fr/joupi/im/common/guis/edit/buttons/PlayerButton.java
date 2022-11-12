package fr.joupi.im.common.guis.edit.buttons;

import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import fr.joupi.im.utils.gui.GuiButton;
import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.SkullBuilder;

public class PlayerButton extends GuiButton {

    public PlayerButton(DNPlayer player) {
        super(new ItemBuilder(SkullBuilder.itemFromName(player.getName())).setName("&b" + player.getName()).build(),
                event -> event.getWhoClicked().sendMessage("open player edit menu"));
    }

}
