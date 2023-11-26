package fr.loanspac.pitchout.managers;

import fr.loanspac.pitchout.game.GameType;
import fr.loanspac.pitchout.utils.FastBoard;
import fr.loanspac.pitchout.utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager { // supprimer les static
    private static Map<Player, FastBoard> boards = new HashMap<>();

    public void createScoreboard(Player player){
        boards.put(player, new FastBoard(player));
    }

    public void deleteScoreboard(Player player){
        boards.remove(player);
    }

    public void updateScoreboard(Player player, int time) {

        if (GameType.getCurrentState().equals(GameType.WAITING)) {
            boards.get(player).updateTitle("§c§nPitchOut");
            boards.get(player).updateLines(
                    "§7§m------------------",
                    "§8■ §fStatus §7➢ §cEn attente...",
                    "",
                    "§8■ §fJoueurs §7➢ §e" + Bukkit.getOnlinePlayers().size(),
                    "§7§m------------------",
                    "§6localhost"
            );
        }
        if (GameType.getCurrentState().equals(GameType.PITCHOUT)) {
            boards.get(player).updateTitle("§c§nPitchOut");
            boards.get(player).updateLines(
                    "§7§m------------------",
                    "§8■ §fStatus §7➢ §cEn Jeu",
                    "",
                    "§8■ §fTemps §7➢ §3" + Time.convert(time),
                    "",
                    "§8■ §fJoueurs §7➢ §e" + Bukkit.getOnlinePlayers().size(),
                    "§7§m------------------",
                    "§6localhost"
            );
        }
    }
}
