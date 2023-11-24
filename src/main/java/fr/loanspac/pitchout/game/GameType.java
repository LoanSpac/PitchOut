package fr.loanspac.pitchout.game;

public enum GameType {
    WAITING("En attente"),
    PITCHOUT("En jeu"),
    BUILD("Build"),
    END("Fin");

    private final String name;
    private static GameType currentState;

    GameType(String name) {
        this.name = name;
    }

    public static GameType getCurrentState() {
        return currentState;
    }

    public static void setState(GameType currentState) {
        GameType.currentState = currentState;
    }

    public String getName() {
        return name;
    }
}
