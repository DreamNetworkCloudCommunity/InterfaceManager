package fr.joupi.im.utils;

import be.alexandre01.dnplugin.api.objects.RemoteService;
import be.alexandre01.dnplugin.api.objects.player.DNPlayer;
import be.alexandre01.dnplugin.api.objects.server.DNServer;
import be.alexandre01.dnplugin.plugins.spigot.api.DNSpigotAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public int getOnlinePlayerCount() {
        AtomicInteger finalCount = new AtomicInteger(0);
        DNSpigotAPI.getInstance().getServices().forEach((s, remoteService) -> remoteService.getServers().values().forEach(server -> finalCount.addAndGet(server.getPlayers().size())));
        return finalCount.get();
    }

    public int getOnlineServerCount() {
        AtomicInteger finalCount = new AtomicInteger(0);
        DNSpigotAPI.getInstance().getServices().forEach((s, remoteService) -> finalCount.addAndGet(remoteService.getServers().size()));
        return finalCount.get();
    }

    public String getServerVersion(DNServer dnServer) {
        return dnServer.getNetworkBaseAPI().getInfo().replaceAll("SPIGOT", "Spigot").replaceAll("PAPER", "Paper").replaceAll("_", ".");
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
