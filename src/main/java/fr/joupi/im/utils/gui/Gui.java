package fr.joupi.im.utils.gui;

import com.google.common.collect.Maps;
import fr.joupi.im.utils.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

@Getter
public abstract class Gui<P extends JavaPlugin> {

    private final P plugin;

    private final Inventory inventory;
    private final String inventoryName;
    private final int rows;

    private final ConcurrentMap<Integer, GuiButton> buttons;
    private transient GuiButtonListener oldListener;

    protected Gui(P plugin, String inventoryName, int rows) {
        this.plugin = plugin;
        this.inventoryName = inventoryName;
        this.rows = rows;
        this.buttons = Maps.newConcurrentMap();
        this.inventory = Bukkit.createInventory(null, rows * 9, Utils.coloredText(inventoryName));
        defaultLoad();
    }

    public abstract void setup();

    public void onOpen(Player player) {
        setup();
        open(player);
    }

    private void open(Player player) {
        player.openInventory(getInventory());
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public void update(Player player) {
        close(player);
        open(player);
    }

    public void setItem(int slot, GuiButton button) {
        getButtons().put(slot, button);
        update();
    }

    public void setItems(int[] slots, GuiButton button) {
        for (int slot : slots)
            setItem(slot, button);
    }

    public void setItems(int[] slots, ItemStack itemStack) {
        for (int slot : slots)
            setItem(slot, new GuiButton(itemStack));
    }

    public void setHorizontalLine(int from, int to, GuiButton button) {
        IntStream.range(from, to)
                .forEach(slot -> setItem(slot, button));
    }

    public void setHorizontalLine(int from, int to, ItemStack item) {
        IntStream.range(from, to)
                .forEach(slot -> setItem(slot, new GuiButton(item)));
    }

    public void setVerticalLine(int from, int to, ItemStack item) {
        for (int slot = from; slot <= to; slot += 9)
            setItem(slot, new GuiButton(item));
    }

    public void setVerticalLine(int from, int to, GuiButton button) {
        for (int slot = from; slot <= to; slot += 9)
            setItem(slot, button);
    }

    public void addItem(GuiButton item) {
        setItem(getInventory().firstEmpty(), item);
        update();
    }

    public void fillAllInventory(GuiButton button) {
        IntStream.range(0, getSize())
                .filter(i -> getItem(i) != null)
                .forEach(i -> setItem(i, button));
    }

    public int[] getBorders() {
        return IntStream.range(0, getSize())
                .filter(i -> getSize() < 27 || i < 9 || i % 9 == 0 || (i - 8) % 9 == 0 || i > getSize() - 9)
                .parallel()
                .toArray();
    }

    public void removeItem(int slot) {
        getButtons().remove(slot);
        getInventory().remove(getInventory().getItem(slot));
    }

    public void clear() {
        getButtons().keySet().forEach(this::removeItem);
        update();
    }

    private void defaultLoad() {
        update();
    }

    private void update() {
        registerItems();
        registerEvents();
    }

    public void refresh() {
        clear();
        setup();
    }

    private void registerItems() {
        getButtons().forEach((slot, item) -> getInventory().setItem(slot, item.getItemStack()));
    }

    private void registerEvents() {
        if (oldListener != null)
            InventoryClickEvent.getHandlerList().unregister(oldListener);

        GuiButtonListener event = new GuiButtonListener();
        getPlugin().getServer().getPluginManager().registerEvents(event, plugin);
        oldListener = event;
    }

    public GuiButton getItem(int slot) {
        return getButtons().get(slot);
    }

    public int getSize() {
        return rows * 9;
    }

    public class GuiButtonListener implements Listener {

        @EventHandler
        public void onClick(InventoryClickEvent event) {
            Inventory inventory = getInventory();
            ItemStack itemStack = event.getCurrentItem();

            if (itemStack != null)
                if (event.getInventory().equals(inventory)) {
                    if (event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.LEFT)
                        getButtons()
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().getItemStack().equals(itemStack))
                            .findFirst()
                            .ifPresent(entry -> entry.getValue().getClickEvent().accept(event));

                    event.setCancelled(true);
                }
        }
    }

}
