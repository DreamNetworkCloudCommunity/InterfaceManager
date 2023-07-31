package fr.joupi.im;

import be.alexandre01.dreamnetwork.api.addons.Addon;
import be.alexandre01.dreamnetwork.api.addons.DreamExtension;
import be.alexandre01.dreamnetwork.core.console.Console;

public class InterfaceManagerExtension extends DreamExtension {

    public InterfaceManagerExtension(Addon addon) {
        super(addon);
    }

    @Override
    public void onLoad() {
        registerPluginToServers(this);
    }

    @Override
    public void start() {
        Console.debugPrint("⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄\n" +
                "⠄⠄⠄⠄⠄⠄⠄⠄⣠⣤⣤⣤⣤⣤⣄⡀⢀⣀⣤⣤⣤⣄⠄⠄⠄⠄⠄⠄⠄⠄\n" +
                "⠄⠄⠄⠄⠄⠄⢀⣾⣿⠛⠉⠙⠻⣿⣿⣿⣿⡿⠟⠛⠻⣿⣷⣄⠄⠄⠄⠄⠄⠄\n" +
                "⠄⠄⠄⠄⠄⣠⣾⣿⠃⢰⣿⣆⠄⠈⢿⣿⠏⠄⢀⣾⡇⠈⣿⣿⣧⡀⠄⠄⠄⠄\n" +
                "⠄⠄⠄⢀⣼⣿⣿⣿⡄⠸⣯⣼⠄⠄⢸⣿⠄⠄⢸⣹⡇⠄⣿⣿⣿⣿⣦⣄⠄⠄\n" +
                "⠄⢀⣴⣿⣿⣿⣿⣿⣷⣄⠛⠁⠄⢀⣼⣿⡄⠄⠈⠛⠃⣰⣿⣿⣿⣿⣿⣿⣷⣄\n" +
                "⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣾⣿⣿⣿⣿⣶⣦⣴⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                "⣿⣿⠋⢉⣉⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⣿⣿⣿\n" +
                "⣿⡇⣠⠛⢿⣧⡀⠛⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⢉⣤⡴⠆⠸⣿⣿\n" +
                "⣿⣇⠘⣦⣀⠙⠿⢶⣤⣄⣈⣉⣉⠉⠛⠛⠛⢉⣉⣀⣤⣴⣾⡿⠛⠁⣸⠄⣿⣿\n" +
                "⠿⣿⣆⠈⠻⣷⣦⣤⣈⣉⡙⠛⠛⠻⠿⠟⠛⠛⠛⢛⣛⢉⣡⣤⣶⠿⠃⢠⣿⣿\n" +
                "⠄⠈⠙⢷⣦⣄⣈⠉⠛⠛⠿⠿⠷⣶⣶⣶⣿⣿⣿⣿⣿⠟⠛⢉⣀⣤⣶⡿⠋⠁\n" +
                "⣠⣴⣶⣾⣿⣿⣿⣿⣿⣶⣶⣶⣶⣶⣶⣤⣤⣤⣤⣤⣶⣶⣾⣿⣿⣿⣿⣶⣤⣄\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
    }

}
