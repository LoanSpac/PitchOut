package fr.loanspac.pitchout.listeners;

import fr.loanspac.pitchout.PitchOut;
import fr.loanspac.pitchout.game.GameSettings;
import fr.loanspac.pitchout.game.GameType;
import fr.loanspac.pitchout.managers.ScoreboardManager;
import fr.loanspac.pitchout.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final ScoreboardManager scoreboard = new ScoreboardManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        Bukkit.broadcastMessage("§7[§2+§7] §b" + player.getName());

        scoreboard.createScoreboard(player);
        player.sendTitle("§c§lPitchOut", "§fBienvenue " + player.getName());

        if(GameType.getCurrentState().equals(GameType.WAITING)) {
            player.setMaxHealth(20);
            player.setHealth(20);
            player.showPlayer(PitchOut.instance(), player);
            player.setGlowing(false);
            player.getInventory().clear();
            player.setExp(0);
            player.setLevel(64);
            player.sendMessage("§8§m----------------------------------------");
            player.sendMessage("§7Bienvenue sur §c§lPitchOut");
            player.sendMessage("");
            player.sendMessage("§fClick sur le panneau pour jouer au jeu.");
            player.sendMessage("§7Tu peux aussi utiliser §6§l/§espawn §7!)");
            player.sendMessage("§8§m----------------------------------------");
            if(!(player.getGameMode().equals(GameMode.ADVENTURE))){
                player.setGameMode(GameMode.ADVENTURE);
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1, 1);
            player.teleport(GameSettings.spawn);
            player.getInventory().setItem(0, ItemManager.getItem(Material.NETHER_STAR, "§6Main", false, false));
            player.getInventory().setItem(4, ItemManager.getItem(Material.WOOL, "§6Team", false, false));
            player.getInventory().setItem(8, ItemManager.getItem(Material.DARK_OAK_DOOR_ITEM, "§4Retour Lobby", false, false));

            player.updateInventory();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.broadcastMessage("§7[§4-§7] §b" + player.getName());
        scoreboard.deleteScoreboard(player);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getCustomName() + " §8§l» §7" + event.getMessage());
    }
}
