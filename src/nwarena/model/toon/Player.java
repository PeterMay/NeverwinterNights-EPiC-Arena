package nwarena.model.toon;

import nwarena.model.Character;

/**
 * <p>Provides an initialization method and an Attack Roll generator.</p>
 */
public class Player extends Character {

    /**
     * Initialize Player.
     *
     * @param type  The {@link ClassType Class Type}, which will define the player's stats.
     * @param name  Name of new Player.
     * @param index  Unique index of Player.
     */
    public Player(ClassType type, String name, int index) {
        super(type, index);
        super.name = name;
    }

    /**
     * Get the Attack Roll of {@link Player}. To be used during an Attack Round.
     *
     * @implNote A {@link java.util.Random Random} is used to emulate a die roll. If the generated int is 20,
     * the overall damage is doubled.
     *
     * @return The amount of damage the {@link Player} is producing during an Attack Round.
     */
    public int getAttackRoll() {
        java.util.Random rand = new java.util.Random();
        int roll = rand.nextInt(20) + 1;
        if (roll == 20) return (getAB() + roll) * 2;
        return (getAB() + rand.nextInt(19) + 1);
    }

}