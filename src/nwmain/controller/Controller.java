package nwmain.controller;

import nwmain.view.*;

import javax.swing.*;
import java.util.Objects;

/**
 * <h3> Controller - nwmain. </h3>
 * Provides Event Handlers for {@link Designer}.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   27/07/2022
 */
public class Controller {

    /** Initialize {@link Designer Designer} and start background music. */
    public Controller() {
        // New Designer.
        try {
            new Designer();
        } catch (Exception exc) {
            Designer.showMessage("NWMain - Error.", "Could not load Designer.\nThe application will now terminate.\n" + exc);
            exit(25);
        }

        // Show Designer and play background music.
        Designer.setVisible(WindowType.MAIN, true);
        Designer.startBackgroundMusic();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Show "New Game" window. </p>
     * <p> Alter JButton's image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void newOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_PNew.png"))));
        // Stop background music and play click SFX.
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(150, actionTimer -> {
            sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_New.png"))));
            Designer.setVisible(WindowType.SELECTION, true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Show "Credits" window. </p>
     * <p> Alter JButton's image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void creditsOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_PCredits.png"))));
        // Stop background music and play click SFX.
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(150, actionTimer -> {
            sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_Credits.png"))));
            Designer.setVisible(WindowType.CREDITS, true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Exit application. </p>
     * <p> Alter JButton's image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void exitOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_PExit.png"))));
        // Stop background music and play click SFX.
        Designer.stopBackgroundMusic();
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(150, actionTimer -> {
            sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_Exit.png"))));
            exit();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Close "Credits" window. </p>
     * <p> Alter JButton's image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void creditsCloseOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_PClose.png"))));
        // Stop background music and play click SFX.
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(150, actionTimer -> {
            sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_Close.png"))));
            Designer.setVisible(WindowType.CREDITS, false);
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Start new game. </p>
     * <p> Alter JButton's image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void selectionOkOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_POK.png"))));
        // Stop background music and play click SFX.
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(150, actionTimer -> {
            sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_OK.png"))));
            Designer.stopBackgroundMusic();
            // Dispose then garbage collector.
            Designer.dispose(WindowType.ALL);
            System.gc();
            // Pass player and game values to Arena.
            new nwarena.controller.ArenaController(Designer.getMonsterCount(), Designer.getTextFieldValue(0), Designer.getComboBoxValue(0),
                    Designer.getTextFieldValue(1), Designer.getComboBoxValue(1),
                    Designer.getTextFieldValue(2), Designer.getComboBoxValue(2),
                    Designer.getTextFieldValue(3), Designer.getComboBoxValue(3));
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * <u>Button</u> Event Listener:
     * <p> Close "New Game" window. </p>
     * <p> Alter JButton's image. </p>
     *
     * @param sender  JButton caller.
     **/
    public static void selectionCloseOnClick(JButton sender) {
        // Update JButton image.
        sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_PClose.png"))));
        // Stop background music and play click SFX.
        Designer.playSoundEffect();
        // Timer for effect.
        Timer timer = new Timer(150, actionTimer -> {
            sender.setIcon(new ImageIcon(Objects.requireNonNull(autorun.controller.Controller.class.getResource("/img/buttons/Button_Close.png"))));
            Designer.setVisible(WindowType.SELECTION, false);
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