package nwarena.controller.round;

import nwarena.controller.ArenaController;
/**
 * <h3> Thread Listener - nwarena - Round. </h3>
 * Provides Event Handlers for {@link RoundController}.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   29/07/2022
 */
public class RoundControllerListener {

    /**
     * <u>Thread</u> Event Listener:
     * <p> Change background music. </p>
     * <p> Signal the end of Round on {@link nwarena.controller.ArenaController Controller}. </p>
     **/
    protected void threadComplete() {
        // Change music.
        try {
            nwarena.view.Designer.startBackgroundMusic(false);
        } catch (Exception exc) {
            nwarena.view.Designer.showMessage("Could not load music file.\nThe application will now terminate.\n" + exc, "NWMain - Error.");
            ArenaController.exit(25);
        }
        // Round complete.
        ArenaController.onRoundComplete();
    }

    /**
     * <u>Thread</u> Event Listener:
     * <p> Exit application, with code. </p>
     **/
    protected void threadException() {
        ArenaController.exit(36);
    }

}
