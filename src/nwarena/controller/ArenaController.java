package nwarena.controller;

import nwarena.controller.round.*;
import nwarena.model.toon.ClassType;
import nwarena.model.Game;
import nwarena.view.*;
import nwarena.view.gamegridbutton.*;
import nwmain.controller.Controller;

import java.awt.*;

/**
 * <h3> Controller - nwarena. </h3>
 * Provides Event Handlers for {@link nwarena.view.Designer View}.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   30/07/2022
 */
public class ArenaController {

    private static boolean _roundActive = false;
    private static Game _game;
    private static GameGridButton _clickedGridButton;

    /**
     * Initialize {@link nwarena.view.Designer View}, {@link nwarena.model.Game Model}  and start background music.
     *
     * @param monsterCount  Amount of monster on the {@link nwarena.model.Game}.
     * @param playerName1  Name of the first {@link nwarena.model.toon.Player Player}.
     * @param playerClass1  {@link nwarena.model.toon.ClassType Class} of the first {@link nwarena.model.toon.Player Player}.
     * @param playerName2  Name of the second {@link nwarena.model.toon.Player Player}.
     * @param playerClass2  {@link nwarena.model.toon.ClassType Class} of the second {@link nwarena.model.toon.Player Player}.
     * @param playerName3  Name of the third {@link nwarena.model.toon.Player Player}.
     * @param playerClass3 {@link nwarena.model.toon.ClassType Class} of the third {@link nwarena.model.toon.Player Player}.
     * @param playerName4  Name of the fourth {@link nwarena.model.toon.Player Player}.
     * @param playerClass4  {@link nwarena.model.toon.ClassType Class} of the fourth {@link nwarena.model.toon.Player Player}.
     */
    public ArenaController(int monsterCount, String playerName1, int playerClass1,
                           String playerName2, int playerClass2,
                           String playerName3, int playerClass3,
                           String playerName4, int playerClass4) {
        // New game. Add players and monsters.
        _game = new Game();
        _game.addPlayer(playerName1, ClassType.values()[playerClass1]);
        _game.addPlayer(playerName2, ClassType.values()[playerClass2]);
        _game.addPlayer(playerName3, ClassType.values()[playerClass3]);
        _game.addPlayer(playerName4, ClassType.values()[playerClass4]);
        for (int cnt = 0; cnt < monsterCount; cnt++) _game.addMonster();
        // Initialize characters.
        _game.initializeCharactersPosition();

        // New view.
        try {
            new Designer();
        } catch (Exception exc) {
            nwarena.view.Designer.showMessage("Could not load music file.\nThe application will now terminate.\n" + exc, "NWMain - Error.");
            exit(58);
        }
        // Set icons.
        Designer.getGridButton(1, 1).setIcon(IconType.PLAYER1);
        Designer.getGridButton(14, 1).setIcon(IconType.PLAYER2);
        Designer.getGridButton(1, 11).setIcon(IconType.PLAYER3);
        Designer.getGridButton(14, 11).setIcon(IconType.PLAYER4);

        // Append Log and update GameGridButton (Players).
        for (int cnt = 0; cnt < 4; cnt++) {
            Designer.getGridButton(_game.getPlayerList().get(cnt).getCoordinates().x,
                    _game.getPlayerList().get(cnt).getCoordinates().y).HasPlayer = true;
        }
        // Update GameGridButton (Monsters).
        for (int cnt = 0; cnt < monsterCount; cnt++) {
            Designer.getGridButton(_game.getMonsterList().get(cnt).getCoordinates().x,
                    _game.getMonsterList().get(cnt).getCoordinates().y).HasMonster = true;
        }
        Designer.appendLogText("[Game] You have entered a PvP area!");

        // Show Designer and play background music.
        Designer.setVisible(true);
        try {
            Designer.startBackgroundMusic(false);
        } catch (Exception exc) {
            Designer.showMessage("Could not load music file.\nThe application will now terminate.\n" + exc, "NWMain - Error.");
            exit(84);
        }
        // Get first player.
        newRound();
    }

    /** Start a new round. */
    private static void newRound() {
        // Get next available, alive player.
        _game.nextPlayer();
        // Check if there is one remaining player.
        if (_game.getAlivePlayers() == 1) {
            // Set player status on VIEW.
            setPlayerStats(_game.getCurrentPlayer().getIndex());
            Designer.appendLogText("\n[Game] " + _game.getCurrentPlayer().getName() + " won the Epic Arena!");
            // Set appropriate background music.
            Designer.setVisible(true);
            try {
                Designer.startBackgroundMusic(true);
            } catch (Exception exc) {
                Designer.showMessage("NWArena - Error.", "Could not load music file.\nThe application will now terminate.\n" + exc);
                exit(105);
            }
            return;
        }
        // The player is currently sitting on a GameGridButton.
        // Set state on the surrounding GameGridButtons (states).
        Designer.getGridButton(_game.getCurrentPlayer().getCoordinates().x, _game.getCurrentPlayer().getCoordinates().y).
                setSurroundState(false);
        // Set player status on VIEW.
        setPlayerStats(_game.getCurrentPlayer().getIndex());
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Exit application. </p>
     */
    public static void exitOnClick() {
        // Stop background music and play click SFX.
        Designer.stopBackgroundMusic();
        Designer.playSoundEffect();
        // Timer for effect.
        javax.swing.Timer timer = new javax.swing.Timer(150, actionTimer -> {
            Designer.dispose();
            System.gc();
            new Controller();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>{@link GameGridButton}</u> Event Listener:
     * <p>
     *     Check clicked {@link State}, then does one of the following:
     *     <ul>
     *         <li>Returns if a round is already active.</li>
     *         <li>Moves {@link nwarena.model.toon.Player Player}.</li>
     *         <li>Initiates new round between a {@link nwarena.model.toon.Player Player} and a {@link nwarena.model.toon.Monster Monster}.</li>
     *         <li>Initiates new round between two {@link nwarena.model.toon.Player Players}.</li>
     *     </ul>
     * </p>
     *
     * @param grdButton Clicked {@link GameGridButton GameGridButton}.
     **/
    public static void gridButtonOnClick(GameGridButton grdButton) {
        if (_roundActive) return;
        _clickedGridButton = grdButton;
        // Current Player clicked on a free block.
        if (grdButton.getState() == State.FREEBLOCK && !grdButton.HasMonster) {
            // Round is active.
            _roundActive = true;
            onRoundComplete();
        }
        // Player clicked on an occupied block.
        else {
            // Try statements and Listener on both conditions as a workaround for no OnClick event validation.
            // Player clicked on a monster occupied GameGridButton.
            if (grdButton.getState() == State.FREEBLOCK && grdButton.HasMonster) {
                // Round is active.
                _roundActive = true;
                // New round controller listener.
                RoundControllerListener threadListener = new RoundControllerListener();
                // Change music to battle.
                try {
                    Designer.startBattleMusic();
                } catch (Exception exc) {
                    Designer.showMessage("Could not load battle music file.\nThe application will now terminate.\n" + exc, "NWMain - Error.");
                    exit(172);
                }
                // New round - monster as attacker, current player as defender.
                new RoundController(_game,
                        _game.getMonsterOnCoordinates(grdButton.getCoordinates()),
                        _game.getCurrentPlayer(),
                        threadListener).start();
            }
            // Player clicked on a Player occupied GameGridButton.
            else if (grdButton.getState() == State.ATTACKBLOCK && grdButton.HasPlayer) {
                // Round is active.
                _roundActive = true;
                // New round controller listener.
                RoundControllerListener threadListener = new RoundControllerListener();
                // Change music to battle.
                try {
                    Designer.startBattleMusic();
                } catch (Exception exc) {
                    Designer.showMessage("Could not load battle music file.\nThe application will now terminate.\n" + exc, "NWMain - Error.");
                    exit(191);
                }
                // New round - current player as attacker, player as defender.
                new RoundController(_game,
                        _game.getCurrentPlayer(),
                        _game.getPlayerOnCoordinates(grdButton.getCoordinates()),
                        threadListener).start();
            }
        }
    }

    /** Handles the end of a round.
     * <p>
     *      Checks which {@link nwarena.model.Character Character} is dead and alters the appropriate
     *      {@link GameGridButton}.
     * </p>
     */
    public static void onRoundComplete() {
        // Current Player is sitting in a GameGridButton.
        // Reset surrounding GameGridButtons.
        Designer.getGridButton(_game.getCurrentPlayer().getCoordinates().x, _game.getCurrentPlayer().getCoordinates().y).
                setSurroundState(true);
        // If round is active.
        if (_roundActive) {
            // On Current Player's GameGridButton.
            // Clear image.
            Designer.getGridButton(_game.getCurrentPlayer().getCoordinates().x, _game.getCurrentPlayer().getCoordinates().y).
                    setIcon(IconType.EMPTY);
            // Set HasPlayer property to false.
            Designer.getGridButton(_game.getCurrentPlayer().getCoordinates().x, _game.getCurrentPlayer().getCoordinates().y).
                    HasPlayer = false;
            // If current player is still alive.
            if (_game.getCurrentPlayer().getHealth() > 0) {
                // On Current Player's clicked GameGridButton.
                // Move dead character outside the map (only if character is player).
                if (_clickedGridButton.HasPlayer)
                    _game.movePlayer(_game.getPlayerOnCoordinates(_clickedGridButton.getCoordinates()).getIndex(), new Point(-1, -1));
                // Set Image, HasMonster and HasPlayer properties.
                _clickedGridButton.setIcon(IconType.values()[_game.getCurrentPlayer().getIndex()]);
                _clickedGridButton.HasMonster = false;
                _clickedGridButton.HasPlayer = true;
                // Move current player to new GameGridButton.
                _game.moveCurrentPlayer(_clickedGridButton.getCoordinates());
            } else {
                // Current player is dead. Move current player outside the map.
                _game.moveCurrentPlayer(new Point(-1, -1));
            }
            // Round is finished.
            _roundActive = false;
        }
        newRound();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Rest current {@link nwarena.model.toon.Player Player} and end turn. </p>
     */
    public static void restOnClick() {
        // Do not allow rest while round is active.
        if (_roundActive) return;
        if (_game.restCurrentPlayer())
            onRoundComplete();
        else
            Designer.appendLogText("You don't need to rest right now.");
    }

    /**
     * Set the stat of {@link nwarena.model.toon.Player Player}, defined by {@code playerIndex}.
     *
     * @param playerIndex  The index of the {@link nwarena.model.toon.Player Player}.
     */
    public static void setPlayerStats(int playerIndex) {
        // PlayerIndex can't be negative.
        if (playerIndex < 0) return;
        // Set player status on VIEW.
        Designer.setPlayerStat(PlayerStat.NAME, _game.getPlayer(playerIndex).getName());
        Designer.setPlayerStat(PlayerStat.AC, String.valueOf(_game.getPlayer(playerIndex).getAC()));
        Designer.setPlayerStat(PlayerStat.ATTACK, String.valueOf(_game.getPlayer(playerIndex).getAB()));
        Designer.setPlayerStat(PlayerStat.CLASS, _game.getCurrentPlayer().Class.name());
        Designer.setPlayerStat(PlayerStat.HP, String.valueOf(_game.getPlayer(playerIndex).getHealth()));
    }

    /** Terminate application. */
    public static void exit() {
        System.gc();
        System.exit(0);
    }

    /**
     * Terminate application with exit code.
     *
     * @param exitCode  Predefined code, showed on exit.
     **/
    public static void exit(int exitCode) {
        System.gc();
        System.exit(exitCode);
    }

}