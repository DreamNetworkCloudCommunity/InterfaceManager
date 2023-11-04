package fr.joupi.im.common.maintenance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceServer {

    private String serverName;
    private List<String> whitelists;
    private boolean whitelisted;

}

/*
⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⡶⠂⣀⣤⣤⣶⣶⣶⣶⣶⣤⣄⡒⢶⣶⣶⣶⣶⣶
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠋⣀⣶⣿⠋⠉⠉⠈⠙⢿⣿⣿⣿⣿⣦⠙⠋⠙⣿⣿
⣿⡿⠋⠁⣀⣀⠈⠻⣿⠃⠄⠘⠟⠏⠄⠄⠄⠄⠄⠄⠻⣿⣿⣿⣿⠄⠄⢸⣿⣿
⠛⢡⣾⣿⡿⠛⢻⣶⡌⠄⠄⠄⠄⢰⣿⣿⣿⣿⣷⠆⠄⠘⠟⠛⠋⡀⣤⢸⣿⣿
⠄⠸⣿⣿⡇⠄⠄⣿⠇⠄⣀⣠⣀⠨⠻⠿⣿⣿⣿⠦⢆⣤⣴⣷⣧⣷⣦⣼⣿⣿
⡀⠄⠻⣿⣧⣀⣰⠏⢀⣼⣿⣿⣿⣷⣶⣶⡤⢀⣴⣾⣿⣿⣿⣿⣿⣿⣿⣧⡙⢿
⠿⠶⠄⠈⠙⢿⡏⠄⣾⣿⣿⣿⣿⣿⣿⡿⠄⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡈
⣤⣄⡀⠄⠄⠄⠄⢼⣿⣿⣿⣿⣿⠟⠁⠄⠄⠉⠙⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⡿⠏⠄⠄⠄⠄⠈⣿⣿⣿⡟⠁⠄⢠⣶⢠⠄⠄⠄⠄⠙⠙⣿⣿⣿⣿⣿⣿⣿
⠋⠄⠄⠄⠄⠄⠄⠄⠘⣿⣿⠄⠄⢀⣾⡇⣼⢇⠄⠄⠄⠄⠄⠄⠈⠛⠿⠿⠿⠿
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠹⣿⠄⠄⣾⣿⠃⣿⢸⠄⠄⢠⣾⠛⠒⢦⣤⣤⢀⣀⣤
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢠⣿⣿⣠⡏⢸⢸⡇⣿⣷⣶⣶⣶⣿⣿⠸⣿⣿
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣿⣿⣿⣧⣾⢸⠃⣿⣿⣿⣿⣿⡿⠋⠄⢹⣿
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⣿⣿⡄⠈⠉⠉⠁⠄⠄⠄⠄⠄⢻
⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⣿⣿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄
 */
