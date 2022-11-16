package fr.joupi.im.utils.gui;

import fr.joupi.im.utils.item.ItemBuilder;
import fr.joupi.im.utils.item.XMaterial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ValidateGui<P extends JavaPlugin> extends Gui<P> {

    private final ItemStack borderItem, informationItem;
    private final Gui<P> previousGui;
    @Setter private ValidateAction validateAction;

    public ValidateGui(P plugin, String inventoryName, ItemStack borderItem, ItemStack informationItem, Gui<P> previousGui, ValidateAction validateAction) {
        super(plugin, inventoryName, 3);
        this.borderItem = borderItem;
        this.informationItem = informationItem;
        this.previousGui = previousGui;
        this.validateAction = validateAction;
    }

    @Override
    public void setup() {
        setItems(getBorders(), getBorderItem());

        setItem(11, confirmButton());

        setItem(13, new GuiButton(getInformationItem()));

        setItem(15, denyButton());
    }


    public GuiButton confirmButton() {
        return new GuiButton(new ItemBuilder(XMaterial.GREEN_STAINED_GLASS_PANE.parseItem()).setName("&7» &aConfirmer").build(), event -> getValidateAction().validateAction());
    }

    public GuiButton denyButton() {
        return new GuiButton(new ItemBuilder(XMaterial.RED_STAINED_GLASS_PANE.parseItem()).setName("&7» &cAnnuler").build(),
                event -> getValidateAction().denyAction(getPreviousGui(), (Player) event.getWhoClicked()));
    }
}
