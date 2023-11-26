package fr.loanspac.pitchout.managers;

import fr.loanspac.pitchout.PitchOut;
import fr.loanspac.pitchout.commands.player.Spawn;
import fr.loanspac.pitchout.commands.staff.Build;

import java.util.Objects;

public class CommandManager {
    PitchOut main = PitchOut.instance();

    public void setCommand(){
        Objects.requireNonNull(main.getCommand("spawn")).setExecutor(new Spawn());
        Objects.requireNonNull(main.getCommand("build")).setExecutor(new Build());
    }
}
