package nwarena.model;

import nwarena.model.toon.Monster;
import nwarena.model.toon.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Provides setters and getters for the position of each {@link Player Player} and {@link Monster Monster} in the game.
 *
 * @implNote {@code GameMap} is a package-private class, and should only be used in {@link Game}.
 */
class GameMap {

    private HashMap<Character, Point> _charactersOnMap;
    private HashMap<Monster, Point> _monstersOnMap;
    private HashMap<Player, Point> _playersOnMap;

    /** Initialize GameMap. */
    protected GameMap() {
        _charactersOnMap = new HashMap<>();
        _monstersOnMap = new HashMap<>();
        _playersOnMap = new HashMap<>();
    }

    /**
     * Set a {@link Player} on the map.
     *
     * @param player  {@link Player} to set on map.
     * @param point  {@link Point} to use as coordinates.
     */
    protected void setPlayerOnMap(Player player, Point point) {
        if (point.x > 14 || point.y > 11 || point.x < 1 || point.y < 1) point = new Point(-1, -1);
        _charactersOnMap.remove(player);
        _charactersOnMap.put(player, point);
        _playersOnMap.remove(player);
        _playersOnMap.put(player, point);
        player.setCoordinates(point);
    }

    /**
     * Set {@link Monster Monsters} on the map.
     *
     * @implNote A {@link Monster Monster's} position on the map is permanent. Therefore,
     * {@code setMonstersOnMap} should only be called once.
     *
     * @param monsterList  {@link List} of {@link Monster Monsters} to set on map.
     */
    protected void setMonstersOnMap(ArrayList<Monster> monsterList) {
        Random rand = new Random();
        for (Monster monster : monsterList) {
            Point point;
            do {
                int x, y;
                do {
                    x = rand.nextInt(13) + 1;
                    y = rand.nextInt(10) + 1;
                } while ((x <= 3 || x >= 11) && (y <= 3 || y >= 9));
                point = new Point(x, y);
            } while (getCharacterOnMap(point) != null);
            _charactersOnMap.put(monster, point);
            _monstersOnMap.put(monster, point);
            monster.setCoordinates(point);
        }
    }

    /**
     * Get the {@link Character} on specific coordinates, defined by {@code point}.
     *
     * @param point  {@link Point} to use as coordinates.
     *
     * @return  {@link Character} on coordinates.
     */
    protected Character getCharacterOnMap(Point point) {
        if (!_charactersOnMap.containsValue(point)) return null;
        for (Map.Entry<Character, Point> entry : _charactersOnMap.entrySet()) {
            if (entry.getValue().equals(point)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Get the {@link Monster} on specific coordinates, defined by {@code point}.
     *
     * @param point  {@link Point} to use as coordinates.
     *
     * @return  {@link Monster} on coordinates.
     */
    protected Monster getMonsterOnMap(Point point) {
        if (!_monstersOnMap.containsValue(point)) return null;
        for (Map.Entry<Monster, Point> entry : _monstersOnMap.entrySet()) {
            if (entry.getValue().equals(point)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Get the {@link Player} on specific coordinates, defined by {@code point}.
     *
     * @param point  {@link Point} to use as coordinates.
     *
     * @return  {@link Player} on coordinates.
     */
    protected Player getPlayerOnMap(Point point) {
        if (!_playersOnMap.containsValue(point)) return null;
        for (Map.Entry<Player, Point> entry : _playersOnMap.entrySet()) {
            if (entry.getValue().equals(point)) {
                return entry.getKey();
            }
        }
        return null;
    }

}