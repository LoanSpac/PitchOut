package fr.loanspac.pitchout.managers;

import fr.loanspac.pitchout.PitchOut;
import fr.loanspac.pitchout.game.Game;
import fr.loanspac.pitchout.listeners.GlobalListener;
import fr.loanspac.pitchout.listeners.PitchOutListener;
import fr.loanspac.pitchout.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager {
    PitchOut main = PitchOut.instance();

    private List<Listener> listeners = new ArrayList<>();

    public void registerEvents(){
        this.listeners.add(new PlayerListener());
        this.listeners.add(new GlobalListener());
        this.listeners.add(new PitchOutListener());
        this.listeners.add(new Game());
        this.listeners.forEach((listener -> {
            Bukkit.getPluginManager().registerEvents(listener, main);
        }));
    }
}
