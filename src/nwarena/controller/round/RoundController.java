package nwarena.controller.round;

import nwarena.controller.ArenaController;
import nwarena.model.Character;
import nwarena.model.Game;

/**
 * <h3> Thread - nwarena - Round. </h3>
 * Runs a game round on a different thread.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   29/07/2022
 */
public class RoundController extends Thread {

    private static RoundControllerListener _listener;
    private final Character _attacker, _defender;
    private final Game _game;

    /**
     * Initializes a new Round.
     *
     * @param game  Current {@link nwarena.model.Game Game}
     * @param attacker  The {@link nwarena.model.Character Character} who initiated the round.
     * @param defender  The {@link nwarena.model.Character Character} who gets attacked.
     * @param listener  The {@link RoundControllerListener event listener} for this thread.
     */
    public RoundController(Game game, Character attacker, Character defender, RoundControllerListener listener) {
        _game = game;
        _attacker = attacker;
        _defender = defender;
        _listener = listener;
    }

    /** Starts thread.<p>Run game round.</p> */
    public void run() {
        // Will be swapping between attacker and defender.
        boolean swap = false;
        Character[] characters = new Character[2];
        // Inform of potential attack bonuses.
        if (_attacker.getPreferredClass()[0] == _defender.Class || _attacker.getPreferredClass()[1] == _defender.Class)
            nwarena.view.Designer.appendLogText(_attacker.getName() + ", holds a Class advantage against " + _defender.getName());
        else if (_defender.getPreferredClass()[0] == _attacker.Class || _defender.getPreferredClass()[1] == _attacker.Class)
            nwarena.view.Designer.appendLogText(_defender.getName() + ", holds a Class advantage against " + _attacker.getName());
        // Repeat till ATTACKER or DEFENDER is dead.
        // During round: ATTACKER is always [0], DEFENDER is always [1].
        do {
            if (swap) {
                characters[0] = _defender;
                characters[1] = _attacker;
            } else {
                characters[0] = _attacker;
                characters[1] = _defender;
            }
            // Set attacking player stats on Designer.
            ArenaController.setPlayerStats(characters[0].getIndex());
            // AB Rounds dedicate how many attacks will take place.
            for (int attackCnt = 0; attackCnt < characters[0].getAttackRounds(); attackCnt++) {
                // Attack round, between ATTACKER and DEFENDER.
                int damageDealt = _game.attackRound(characters[0], characters[1]);
                // Append text accordingly.
                if (damageDealt <= 0)
                    nwarena.view.Designer.appendLogText(characters[0].getName() + " tried to hit " + characters[1].getName());
                else
                    nwarena.view.Designer.appendLogText(characters[0].getName() + " attacked " + characters[1].getName() +
                            " - Damage dealt: " + damageDealt);
                // Wait 1 second.
                try {
                    sleep(1000);
                } catch (InterruptedException exc) {
                    // Signal thread error.
                    _listener.threadException();
                    nwarena.view.Designer.showMessage("RoundController", exc.getMessage());
                }
            }
            // Check if DEFENDER is dead.
            // If not, swap ATTACKER and DEFENDER.
            if (characters[1].getIsDead()) break;
            else swap = !swap;
        } while (!characters[1].getIsDead());
        // Append text accordingly.
        nwarena.view.Designer.appendLogText(characters[0].getName() + " killed " + characters[1].getName() + "!");
        // Signal thread complete.
        _listener.threadComplete();
    }

}

