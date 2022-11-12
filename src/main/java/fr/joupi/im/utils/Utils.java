package fr.joupi.im.utils;

import be.alexandre01.dnplugin.api.objects.RemoteService;
import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.plugins.spigot.DNSpigot;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import fr.joupi.im.utils.threading.MultiThreading;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@UtilityClass
public class Utils {

    public String getPrefix() {
        return coloredText("&6&lIM &7» ");
    }

    public DNPlayer findPlayer(Player player) {
        return DNSpigotAPI.getInstance().getDnPlayerManager().getDnPlayers().values().stream().filter(dnPlayer -> player.getName().equals(dnPlayer.getName())).findFirst().orElse(null);
    }

    public int getOnlinePlayerCount(RemoteService category) {
        AtomicInteger finalCount = new AtomicInteger(0);
        DNSpigotAPI.getInstance().getServices().get(category.getName()).getServers().forEach((integer, dnServer) -> finalCount.addAndGet(dnServer.getPlayers().size()));
        return finalCount.get();
    }

    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);

        connection.sendPacket(packetPlayOutTimes);

        if (subTitle != null) {
            IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + coloredText(subTitle) + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatBaseComponent);

            connection.sendPacket(packetPlayOutSubTitle);
        }

        if (title != null) {
            IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + coloredText(title) + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatBaseComponent);

            connection.sendPacket(packetPlayOutSubTitle);
        }
    }

    public void sendActionbar(Player player, String message) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + coloredText(message) + "\"}");
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(chatBaseComponent, (byte) 2);

        craftPlayer.getHandle().playerConnection.sendPacket(packetPlayOutChat);
    }

    public void sendFirework(Player player, int power, FireworkEffect fireworkEffect) {
        Firework firework = (Firework) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        fireworkMeta.addEffect(fireworkEffect);
        fireworkMeta.setPower(power);
        firework.setFireworkMeta(fireworkMeta);

        MultiThreading.runAsync(firework::detonate);
    }

    public void sendMessages(CommandSender commandSender, String... messages) {
        Arrays.asList(messages)
                .forEach(message -> commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', getPrefix() +  message)));
    }

    public void sendMessages(Player player, List<String> messages) {
        messages.forEach(message -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', getPrefix() + message)));
    }

    public void sendMessages(Player player, String... messages) {
        Arrays.asList(messages)
                .forEach(message -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', getPrefix() + message)));
    }

    public String coloredText(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public DecimalFormat decimalFormat() {
        return new DecimalFormat("#.##");
    }

}
