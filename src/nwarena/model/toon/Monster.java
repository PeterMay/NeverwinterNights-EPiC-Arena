package nwarena.model.toon;

import nwarena.model.Character;

/**
 * <p>Provides an initialization method and an Attack Roll generator.</p>
 */
public class Monster extends Character {

    /** Initialize Monster.*/
    public Monster() {
        super(ClassType.MONSTER, -1);
        super.name = "Monster";
    }

    /**
     * Get the Attack Roll of {@link Player}. To be used during an Attack Round.
     *
     * @implNote A {@link java.util.Random Random} is used to emulate a die roll. {@link Monster Monsters} can only
     * roll between numbers 5 and 20.
     *
     * @return The amount of damage the {@link Player} is producing during an Attack Round.
     */
    public int getAttackRoll() {
        java.util.Random rand = new java.util.Random();
        return (getAB() + rand.nextInt(16) + 5);
    }

}