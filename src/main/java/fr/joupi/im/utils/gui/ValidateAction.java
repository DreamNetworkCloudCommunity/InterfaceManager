package fr.joupi.im.utils.gui;

import org.bukkit.entity.Player;

public interface ValidateAction {

    void validateAction();

    default void denyAction(Gui<?> previousGui, Player player) {
        previousGui.onOpen(player);
    }

}