package autorun.view;

import autorun.controller.Controller;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * <h3> Designer - autorun. </h3>
 * Provides JFrame initialization, background music and SFX playback functions.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   25/07/2022
 */
public class Designer {

    private static JFrame _autorun;
    private static Clip _backgroundMusic;
    private static Clip _soundEffect;

    /**
     * Initialization for JFrame, background music and SFX.
     * <p> Start music playback with the {@link #startBackgroundMusic() startBackgroundMusic} method. </p>
     * <p> Play SFX with the {@link #playSoundEffect() playSoundEffect} method. </p>
     */
    public Designer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // New clips and open appropriate music file.
        _soundEffect = AudioSystem.getClip();
        _backgroundMusic = AudioSystem.getClip();
        _soundEffect.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/mus/mus_click.wav"))));
        _backgroundMusic.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/mus/mus_autorun.wav"))));
        // Initialize JFrame.
        initializeFrame();
    }

    /** Initialize JFrame and its components. */
    private static void initializeFrame() {
        // JFrame visual properties.
        _autorun = new JFrame("Neverwinter Nights - EPiC Arena");
        _autorun.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _autorun.setSize(394, 259);
        _autorun.setResizable(false);
        _autorun.setLocationRelativeTo(null);
        _autorun.setUndecorated(true);
        _autorun.setAlwaysOnTop(true);

        // Set JFrame icon and Background image.
        try {
            _autorun.setIconImage(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/icon.png"))).getImage());
            _autorun.setContentPane(new JLabel(new ImageIcon(ImageIO.read(Objects.requireNonNull(Designer.class.getResource("/img/img_autorun.png"))))));
        } catch (IOException exc) {
            showMessage("Autorun - Error", "Could not load image files.\nThe application will terminate.\n" + exc);
            Controller.exit();
        }

        JButton btnPlay = new JButton("");
        JButton btnExit = new JButton("");

        // Button - Play
        btnPlay.setName("btnPlay");
        btnPlay.setBorder(BorderFactory.createEmptyBorder());
        btnPlay.setBounds(30, 140, 178, 48);
        btnPlay.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Play.png"))));
        // Mouse Hover Event
        btnPlay.addMouseListener(new MouseAdapter() {
            // On enter.
            public void mouseEntered(MouseEvent me) {
                btnPlay.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HPlay.png"))));
            }

            // On exit.
            public void mouseExited(MouseEvent me) {
                btnPlay.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_Play.png"))));
            }
        });
        // On Click Event.
        btnPlay.addActionListener(action -> Controller.playOnClick(btnPlay));

        // Button - Exit
        btnPlay.setName("btnExit");
        btnExit.setBounds(30, 200, 178, 48);
        btnExit.setBorder(BorderFactory.createEmptyBorder());
        btnExit.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Exit.png"))));
        // Mouse Hover Event.
        btnExit.addMouseListener(new MouseAdapter() {
            // On enter.
            public void mouseEntered(MouseEvent me) {
                btnExit.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HExit.png"))));
            }

            // On exit.
            public void mouseExited(MouseEvent me) {
                btnExit.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_Exit.png"))));
            }
        });
        // On Click Event.
        btnExit.addActionListener(action -> Controller.exitOnClick(btnExit));

        _autorun.add(btnPlay);
        _autorun.add(btnExit);
    }

    /** Hide then dispose JFrame. */
    public static void dispose() {
        _autorun.setVisible(false);
        _autorun.dispose();
    }

    /** Play SFX. */
    public static void playSoundEffect() {
        // Don't forget to reset position.
        _soundEffect.setMicrosecondPosition(0);
        _soundEffect.start();
    }

    /** Show JFrame. */
    public static void show() {
        startBackgroundMusic();
        // JFrame pack and set Visible.
        _autorun.pack();
        _autorun.setVisible(true);
    }

    /**
     * Show message on a {@link JOptionPane JOptionPane}.
     *
     * @param title  Title String.
     * @param text  Message body String.
     *
     * @see JOptionPane#showMessageDialog(Component, Object, String, int) JOptionPane.showMessageDialog()
     */
    public static void showMessage(String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }

    /** Start Background Music. */
    public static void startBackgroundMusic() {
        // Don't forget to reset position.
        _backgroundMusic.setMicrosecondPosition(0);
        _backgroundMusic.start();
    }

    /** Stop Background Music. */
    public static void stopBackgroundMusic() {
        _backgroundMusic.stop();
    }

}
