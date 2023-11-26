package fr.loanspac.pitchout.game;

import fr.loanspac.pitchout.managers.ScoreboardManager;
import fr.loanspac.pitchout.utils.ItemManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Game extends BukkitRunnable implements Listener {
    private final ScoreboardManager scoreboard = new ScoreboardManager();
    public static int time = -20;
    private static final List<Player> inPVP = new ArrayList<>();
    public static List<Player> alives = new ArrayList<>();

    public static List<Player> getInPVP() {
        return inPVP;
    }

    private static void teleportPlayer(Player player) {
        player.teleport(GameSettings.pitchout);
    }

    public static void equipPlayer(Player player) {
        player.getInventory().clear();
        ItemStack pelle = ItemManager.getItem(Material.DIAMOND_SPADE, "§cPelle Poussoire", true, true);
        ItemStack arc = ItemManager.getItem(Material.BOW, "§cArc Poussoire", Enchantment.ARROW_INFINITE, 1, true);
        ItemStack fleche = new ItemStack(Material.ARROW);

        player.getInventory().setItem(0, pelle);
        player.getInventory().setItem(1, arc);
        player.getInventory().setItem(9, fleche);

        player.updateInventory();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if(GameType.getCurrentState().equals(GameType.PITCHOUT)){
            alives.remove(event.getPlayer());
            getInPVP().remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event){
        if(GameType.getCurrentState().equals(GameType.PITCHOUT)) {
            Player player = event.getPlayer();
            alives.remove(player);

            getInPVP().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            player.sendMessage("§cLa partie est déjà en cours !");
        } else if(GameType.getCurrentState().equals(GameType.END)) {
            Player player = event.getPlayer();
            alives.remove(player);

            getInPVP().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
            player.sendMessage("§cLa partie est déjà terminée !");
        }
    }

    @Override
    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            scoreboard.updateScoreboard(players, 0);
            switch(time) {
                case -10:
                    players.sendTitle("§cAttention", "§6La partie commence dans 10s..", 10, 20, 10);
                    break;
                case -5:
                    players.sendTitle("§95", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.E));
                    break;
                case -4:
                    players.sendTitle("§24", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.D));
                    break;
                case -3:
                    players.sendTitle("§e3", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.C));
                    break;
                case -2:
                    players.sendTitle("§62", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.B));
                    break;
                case -1:
                    players.sendTitle("§c1", "", 10, 20, 10);
                    players.playNote(players.getLocation(), Instrument.BELL, Note.natural(1, Note.Tone.A));
                    break;
            }
        }

        if(time == 0) {
            GameType.setState(GameType.PITCHOUT);
            Bukkit.getWorld("world").setPVP(true);
            for(Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                alives.add(players);
                getInPVP().add(players);
                teleportPlayer(players);
                equipPlayer(players);
            }
        }

        if(time > 0) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                scoreboard.updateScoreboard(players, time);
                if(players.getHealth() <= 0) {
                    players.setHealth(20);
                    players.setFoodLevel(20);
                    players.setGameMode(GameMode.SPECTATOR);
                    alives.remove(players);
                }
            }
            if(alives.size() == 99) { // Modifier le nombre -> 1
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendTitle("§cFINISH !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                getInPVP().remove(alives.get(0));
                GameType.setState(GameType.END);
                cancel();
            }
        }

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            time++;
        } else if (time != -20) {
            time = -20;
            if (Bukkit.getWorld("world").getPVP()) {
                Bukkit.getWorld("world").setPVP(false);
            }
            Bukkit.broadcastMessage("En attente de joueur..");
        }
    }
}
