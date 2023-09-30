package fr.joupi.im.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

    NEW_SERVER ("newServer"),

    UPDATE_MAINTENANCE_DATA ("updateMaintenance"),

    UPDATE_MAINTENANCE_STATUS ("changeMaintenanceStatus"),

    UPDATE_MAINTENANCE_LIST_ADD ("addPlayerInMaintenanceList"),
    UPDATE_MAINTENANCE_LIST_REMOVE ("removePlayerInMaintenanceList"),

    KICK_ALL ("kickAll"),
    KICK_PLAYER ("kickPlayer"),

    TELEPORT_PLAYER ("teleportPlayer");

    private final String name;

/*
⣿⣿⣿⣿⣿⡿⢛⣭⣭⡝⠿⠫⣭⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⢱⡿⣫⣷⣿⣿⣿⣷⣾⣻⣷⣾⣯⣟⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⢟⣿⢧⣿⣿⣿⣿⣿⣿⠟⠋⠊⣿⣿⣿⠓⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⡟⣵⣿⣿⡞⣿⣿⣿⣿⣿⣿⠄⠄⠄⣶⣯⣥⡀⠰⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⡿⣹⣿⣿⣿⣿⣶⣭⣟⣻⣿⣟⣣⣴⠾⢿⣿⣿⡿⢿⣶⣮⣻⣿⣿⣿⣿⣿⣿
⣿⢣⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⣫⣭⣻⣷⣾⣿⣷⣬⢻⣿⣿⣿⣿
⢟⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡸⡟⠉⠉⠙⠿⣿⣿⣿⣷⡜⣿⣿⣿
⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣶⣶⣶⣤⣀⣉⣭⣍⢺⣿⣿⣿
⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⣿⣿⣿
⣿⣿⣿⣿⣿⣿⡿⠛⠛⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠏⠁⣾⣿⣿
⡼⣿⣿⣿⣿⣿⣧⣀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠁⠄⠄⠄⠄⠄⠄⠄⢸⣿⣿⣿
⣿⣮⡻⣿⣿⣿⣿⣿⣿⣦⣤⣀⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⣀⢠⣾⣿⣿⣿
⢿⣿⣿⣶⣽⣻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠸⣿⣿⣿⣿
⠄⠙⠻⠿⣿⣿⣿⣶⣭⣛⣻⡿⠿⠿⠿⣿⣿⢿⣿⣿⣿⣿⣿⣿⡎⠄⠄⠻⣿⣿
⠄⠄⠄⠄⠄⠉⠛⠿⣿⣿⠋⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠉⡇⠄⠄⠄⠈⠻

 */
}
