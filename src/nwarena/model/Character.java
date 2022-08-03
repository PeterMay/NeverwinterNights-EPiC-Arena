package nwarena.model;

import nwarena.model.toon.ClassType;
import nwarena.model.toon.Monster;
import nwarena.model.toon.Player;

import java.awt.*;

/** Character is an abstract, which {@link nwarena.model.toon.Monster Monster} and
 * {@link nwarena.model.toon.Player Player} are base on. It provides getters and setters
 * for all properties that are used on {@link Game}.
 * */
public abstract class Character {

    private boolean _isDead = false;
    private int _health, _index;
    private final int _abRounds, _restAmount, _ac, _ab;
    private final int _healthLimit;
    private Point _coordinates;
    private final ClassType[] _prefClass = new ClassType[2];
    protected String name;
    public final ClassType Class;

    /**
     * Initializes Character.
     * @param type  {@link ClassType} of Character.
     * @param index  Unique index of character. ({@link nwarena.model.toon.Monster Monsters} always have index = -1)
     */
    public Character(ClassType type, int index) {
        Class = type;
        _index = index;
        switch (type) {
            case BARBARIAN -> {
                _healthLimit = 300;
                _health = 300;
                _ac = 21;
                _ab = 22;
                _abRounds = 2;
                _restAmount = 50;
                _prefClass[0] = ClassType.FIGHTER;
                _prefClass[1] = ClassType.PALEMASTER;
            }
            case FIGHTER -> {
                _healthLimit = 260;
                _health = 260;
                _ac = 24;
                _ab = 20;
                _abRounds = 2;
                _restAmount = 20;
                _prefClass[0] = ClassType.PALADIN;
                _prefClass[1] = ClassType.SORCERER;
            }
            case PALADIN -> {
                _healthLimit = 220;
                _health = 220;
                _ac = 25;
                _ab = 18;
                _abRounds = 3;
                _restAmount = 35;
                _prefClass[0] = ClassType.BARBARIAN;
                _prefClass[1] = ClassType.PALEMASTER;
            }
            case PALEMASTER -> {
                _healthLimit = 200;
                _health = 200;
                _ac = 35;
                _ab = 17;
                _abRounds = 1;
                _restAmount = 40;
                _prefClass[0] = ClassType.PALADIN;
                _prefClass[1] = ClassType.SORCERER;
            }
            case SORCERER -> {
                _healthLimit = 175;
                _health = 175;
                _ac = 30;
                _ab = 25;
                _abRounds = 1;
                _restAmount = 60;
                _prefClass[0] = ClassType.FIGHTER;
                _prefClass[1] = ClassType.BARBARIAN;
            }
            case MONSTER -> {
                _index = -1;
                _healthLimit = 100;
                _health = 100;
                _ac = 25;
                _ab = 20;
                _abRounds = 1;
                _restAmount = 10;
                _prefClass[0] = ClassType.MONSTER;
                _prefClass[1] = ClassType.MONSTER;
            }
            default -> {
                _healthLimit = 0;
                _health = 0;
                _ac = 0;
                _ab = 0;
                _abRounds = 0;
                _restAmount = 0;
                _prefClass[0] = ClassType.BARBARIAN;
                _prefClass[1] = ClassType.BARBARIAN;
            }
        }
    }

    /** Get the Health value bound.
     *
     * @return The Health Limit of the {@link Character}
     * */
    protected int getHealthLimit() {
        return _healthLimit;
    }

    /** Get the Health replenishing amount during a rest.
     *
     * @return The rest amount of the {@link Character}
     * */
    protected int getRestAmount() {
        return _restAmount;
    }

    /** Set the coordinates, defined by {@code point}.
     *
     * @param point  {@link Point} acting as coordinates.
     *
     * */
    protected void setCoordinates(Point point) {
        _coordinates = point;
    }

    /** Modify Health by {@code amount}.
     *
     * @param amount  The amount of health to alter.
     * */
    protected void modifyHealth(int amount) {
        if (_health + amount > _healthLimit) _health = _healthLimit;
        else if (_health + amount <= 0) {
            _health = 0;
            _isDead = true;
        } else _health += amount;
    }

    /**
     * Check if {@link Character Character's} Health amount, is bigger than zero.
     * @return True, if Health value is positive.
     */
    public boolean getIsDead() {
        return _isDead;
    }

    /**
     * Get the Attack Bonus value.
     * @return The Attack Bonus.
     */
    public int getAB() {
        return _ab;
    }

    /**
     * Get the amount of Attack Rounds.
     * @return The Attack Rounds.
     */
    public int getAttackRounds() {
        return _abRounds;
    }

    /**
     * Get the AC value.
     * @return The AC.
     */
    public int getAC() {
        return _ac;
    }

    /**
     * Get the Health value.
     * @return The AC.
     */
    public int getHealth() {
        return _health;
    }

    /**
     * Get the Unique Index value.
     * @return The Unique Index.
     */
    public int getIndex() {
        return _index;
    }

    /**
     * Get the Name of {@link Character}.
     * @return The name of {@link Character}.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current Coordinates.
     * @return The coordinates of {@link Character}.
     */
    public Point getCoordinates() {
        return _coordinates;
    }

    /**
     * Get the Class of {@link Character}.
     * @return The {@link ClassType} of {@link Character}.
     */
    public ClassType[] getPreferredClass() {
        return _prefClass;
    }

    /**
     * Get the Attack Roll of {@link Character}. To be used during an Attack Round.
     *
     * @implNote A Class, which extends {@link Character}, should implement its own {@code getAttackRoll()} method in
     * a unique way.
     * <p>
     *     For example: {@link Monster}{@code .getAttackRoll()}, will always return a number between 5 and 20 plus AB.
     *     On the other hand, {@link Player}{@code .getAttackRoll()}, can produce critical hits.
     * </p>
     *
     * @return The amount of damage the {@link Character} is producing during an Attack Round.
     */
    public abstract int getAttackRoll();

}