package nwarena.model;

import nwarena.model.toon.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3> Model - nwarena. </h3>
 * Provides methods which change {@link Character} properties, as well as getters for various Game elements.
 * <p>{@code Game} also contains a {@link GameMap}, which tracks the location of {@link Character Characters}.</p>
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   30/07/2022
 */
public class Game {

    private Player _curPlayer;
    private final ArrayList<Player> _players;
    private final ArrayList<Monster> _monsters;
    private GameMap _map;

    /** Initialize Game. */
    public Game() {
        _players = new ArrayList<>();
        _monsters = new ArrayList<>();
        _map = new GameMap();
        _curPlayer = null;
    }

    /** Initialize position of all {@link Character Characters} on {@link GameMap}. */
    public void initializeCharactersPosition() {
        _map.setPlayerOnMap(_players.get(0), new Point(1, 1));
        _map.setPlayerOnMap(_players.get(1), new Point(14, 1));
        _map.setPlayerOnMap(_players.get(2), new Point(1, 11));
        _map.setPlayerOnMap(_players.get(3), new Point(14, 11));
        _map.setMonstersOnMap(_monsters);
    }

    /**
     * Add a new {@link Monster}.
     *
     * @implNote {@code addMonster()} won't set {@link Monster} on {@link GameMap}. Monsters should
     * be added <u>before</u> {@link #initializeCharactersPosition()} is called.
     */
    public void addMonster() {
        _monsters.add(new Monster());
    }

    /**
     * Add a new {@link Player}.
     *
     * @implNote {@code addPlayer(String, ClassType)} won't set {@link Player} on {@link GameMap}. Players should
     * be added <u>before</u> {@link #initializeCharactersPosition()} is called.
     *
     * @param name  Name of {@link Player} to add.
     * @param classType  {@link ClassType} of {@link Player} to add.
     */
    public void addPlayer(String name, ClassType classType) {
        _players.add(new Player(classType, name, _players.size()));
    }

    /**
     * Moves current active {@link Player} to specific coordinates, dictated by {@code point}.
     *
     * @param point  Coordinates to move player.
     */
    public void moveCurrentPlayer(Point point) {
        _map.setPlayerOnMap(_curPlayer, point);
    }

    /**
     * Moves {@link Player} defined by {@code playerIndex}, to specific coordinates dictated by {@code point}.
     *
     * @param playerIndex  Unique index of {@link Player}.
     * @param point  Coordinates to move player.
     */
    public void movePlayer(int playerIndex, Point point) {
        _map.setPlayerOnMap(_players.get(playerIndex), point);
    }

    /** Sets next {@link Player} as the current one (if alive). */
    public void nextPlayer() {
        // Check if this is the start of the game.
        if (_curPlayer == null) {
            _curPlayer = _players.get(0);
            return;
        }
        // Set CUR_PLAYER to next available player.
        do {
            if (_curPlayer.getIndex() == 3) _curPlayer = _players.get(0);
            else _curPlayer = _players.get(_curPlayer.getIndex() + 1);
        } while (_curPlayer.getIsDead());
    }

    /** Rests the current {@link Player} by replenishing health. */
    public boolean restCurrentPlayer() {
        if (_curPlayer.getHealth() == _curPlayer.getHealthLimit())
            return false;
        _curPlayer.modifyHealth(_curPlayer.getRestAmount());
        return true;
    }

    /**
     * Executes an attack round, by calculating the amount of damage the {@code attacker}
     * inflicts on the {@code defender}.
     *
     * @param offender  Attacker {@link Character}.
     * @param defender  Defender {@link Character}.
     *
     * @return The amount of damage inflicted on {@code defender}.
     */
    public int attackRound(Character offender, Character defender) {
        int attackAmount = offender.getAttackRoll();
        // Versus a preferred class grants +5 damage.
        if (offender.getPreferredClass()[0] == defender.Class || offender.getPreferredClass()[1] == defender.Class)
            attackAmount += 5;
        if (attackAmount > defender.getAC()) defender.modifyHealth(-attackAmount + defender.getAC());
        return attackAmount - defender.getAC();
    }

    /**
     * Checks the amount of {@link Player Players} that have a positive amount of HP.
     *
     * @return The amount of players currently alive.
     */
    public int getAlivePlayers() {
        int cnt = 0;
        for (Player player : _players) {
            if (!player.getIsDead()) cnt++;
        }
        return cnt;
    }

    /**
     * Get the {@link Monster} on specific coordinates, defined by {@code point}.
     *
     * @param point  Specific {@link Point} to check.
     *
     * @return The {@link Monster} on coordinates.
     */
    public Monster getMonsterOnCoordinates(Point point) {
        return _map.getMonsterOnMap(point);
    }

    /**
     * Get the current {@link Player} whose turn is still to pass.
     *
     * @return The active {@link Player}.
     */
    public Player getCurrentPlayer() {
        return _curPlayer;
    }

    /**
     * Get the current {@link Player}, define by {@code playerIndex}.
     *
     * @param playerIndex  Unique index of {@link Player}.
     *
     * @return {@link Player} with a matching index.
     */
    public Player getPlayer(int playerIndex) {
        return _players.get(playerIndex);
    }

    /**
     * Get the {@link Player} on specific coordinates, defined by {@code point}.
     *
     * @param point  Specific {@link Point} to check.
     *
     * @return The {@link Player} on coordinates.
     */
    public Player getPlayerOnCoordinates(Point point) {
        return _map.getPlayerOnMap(point);
    }

    /**
     * Get a {@link List} with all the {@link Player Players} in the Game.
     *
     * @return {@link List} with four {@link Player Players}.
     */
    public List<Player> getPlayerList() {
        return _players;
    }

    /**
     * Get a {@link List} with all the {@link Monster Monster} in the Game.
     *
     * @return {@link List} with all {@link Monster Monsters} currently in the Game.
     */
    public List<Monster> getMonsterList() {
        return _monsters;
    }

}