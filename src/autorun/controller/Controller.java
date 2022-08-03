package autorun.controller;

import autorun.view.Designer;

import javax.swing.*;
import java.util.Objects;

/**
 * <h3> Controller - autorun. </h3>
 * Provides Event Handlers for {@link Designer} and a {@link #main(String[]) main} method.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   25/07/2022
 */
public class Controller {

    /**
     * Start Neverwinter Nights - Epic Arena.
     *
     * @param args  The source file and target file path
     **/
    public static void main(String[] args) {
        // New Designer.
        try {
            new Designer();
        } catch (Exception exc) {
            Designer.showMessage( "Autorun - Error.", "Could not load Model.\nThe application will now terminate.\n" + exc);
            Controller.exit(29);
        }
        Designer.show();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Exit autorun. </p>
     * <p> Update caller image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void exitOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(Controller.class.getResource("/img/buttons/Button_PExit.png"))));
        // Stop background music and play click SFX.
        Designer.stopBackgroundMusic();
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(100, actionTimer -> Controller.exit());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Play click SFX. </p>
     * <p> Update caller image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void playOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(Controller.class.getResource("/img/buttons/Button_PPlay.png"))));
        // Stop background music and play click SFX.
        Designer.stopBackgroundMusic();
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(100, actionTimer -> {
            Designer.dispose();
            System.gc();
            new nwmain.controller.Controller();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /** Terminate application. */
    public static void exit() {
        System.exit(0);
    }

    /**
     * Terminate application with exit code.
     *
     * @param exitCode  Predefined code, showed on exit.
     **/
    public static void exit(int exitCode) {
        System.exit(exitCode);
    }

}
