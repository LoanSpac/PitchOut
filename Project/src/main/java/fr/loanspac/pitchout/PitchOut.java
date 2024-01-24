package fr.loanspac.pitchout;

import fr.loanspac.pitchout.game.Game;
import fr.loanspac.pitchout.game.GameType;
import fr.loanspac.pitchout.managers.CommandManager;
import fr.loanspac.pitchout.managers.ListenerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PitchOut extends JavaPlugin {
    private static PitchOut INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Enable PitchOut 1.0");
        Bukkit.getLogger().info("===========================");

        GameType.setState(GameType.WAITING);

        //Bukkit.getWorld("world").setGameRuleValue("RF", "true");

        ListenerManager listeners = new ListenerManager();
        listeners.registerEvents();

        CommandManager commands = new CommandManager();
        commands.setCommand();

        Game game = new Game();
        game.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Disable PitchOut 1.0");
        Bukkit.getLogger().info("===========================");
    }

    public static PitchOut instance() {
        return INSTANCE;
    }

}
