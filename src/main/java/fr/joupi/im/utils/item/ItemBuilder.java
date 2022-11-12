package fr.joupi.im.utils.item;

import fr.joupi.im.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public static ItemBuilder from(ItemStack item) {
        return new ItemBuilder(item);
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setData(MaterialData data) {
        item.setData(data);
        return this;
    }

    public ItemBuilder setType(Material type) {
        item.setType(type);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.coloredText(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public boolean removeLoreLine(int line) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());

        if (line < 0 || line > lore.size())
            return false;
        else {
            lore.remove(line);
            meta.setLore(lore);
            item.setItemMeta(meta);
            return true;
        }
    }

    public boolean removeLoreLine(String line) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());

        if (!lore.contains(line))
            return true;
        else {
            lore.remove(line);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return true;
    }

    public ItemBuilder addLore(String line) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (meta.hasLore())
            lore = new ArrayList<>(meta.getLore());

        lore.add(line);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (meta.hasLore())
            lore = new ArrayList<>(meta.getLore());

        for (String line : lines)
            lore.add(ChatColor.translateAlternateColorCodes('&', line));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(List<String> lines) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (meta.hasLore())
            lore = new ArrayList<>(meta.getLore());

        for (String line : lines)
            lore.add(ChatColor.translateAlternateColorCodes('&', line));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public boolean addLore(String line, int index) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());

        if (index < 0 || index > lore.size())
            return false;
        else {
            lore.set(index, line);
            meta.setLore(lore);
            item.setItemMeta(meta);
            return true;
        }
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setEnchant(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        ItemMeta meta = item.getItemMeta();

        if (glowing) {
            meta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else
            meta.removeEnchant(Enchantment.KNOCKBACK);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
            item.setItemMeta(meta);
        } catch (ClassCastException exception) {
            throw new ClassCastException("Non leather armor can't have a color");
        }
        return this;
    }

    public ItemBuilder setPotionEffect(PotionEffectType effectType, int duration, int amplifier) {
        if (item.getType() != Material.POTION)
            throw new IllegalStateException("Non potion items can't have a effect");

        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(effectType, duration, amplifier), true);
        item.setItemMeta(potionMeta);
        return this;
    }

    public ItemBuilder setDyeColor(DyeColor color) {
        item.setDurability(color.getData());
        return this;
    }

    public ItemStack build() {
        return item;
    }

}
