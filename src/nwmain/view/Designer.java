package nwmain.view;

import nwmain.controller.Controller;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;


/**
 * <h3> Designer - nwmain. </h3>
 * Provides JFrame and JWindow initialization methods, background music and SFX playback methods
 * and getters for JComponent data.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   27/07/2022
 */
public class Designer {

    private static JFrame _nwmain;
    private static JWindow _credits;
    private static JWindow _selection;
    private static Clip _backgroundMusic;
    private static Clip _soundEffect;
    private static JComboBox<String> _cmbxClass1 = new JComboBox<>();
    private static JComboBox<String> _cmbxClass2 = new JComboBox<>();
    private static JComboBox<String> _cmbxClass3 = new JComboBox<>();
    private static JComboBox<String> _cmbxClass4 = new JComboBox<>();
    private static JTextField _txfName1;
    private static JTextField _txfName2;
    private static JTextField _txfName3;
    private static JTextField _txfName4;
    private static JSpinner _spnMonsters;

    /**
     * Initialization for JFrame and JWindows, background music and SFX.
     * <p> Start music playback with the {@link #startBackgroundMusic() startBackgroundMusic} method. </p>
     * <p> Play SFX with the {@link #playSoundEffect() playSoundEffect} method. </p>
     */
    public Designer() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // Load music.
        _backgroundMusic = AudioSystem.getClip();
        _backgroundMusic.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/mus/mus_main.wav"))));
        _soundEffect = AudioSystem.getClip();
        _soundEffect.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/mus/mus_click.wav"))));

        initializeMain();
        initializeCredits();
        initializeSelection();
    }

    /** Initialize JFrame and its components. */
    private static void initializeMain() {
        _nwmain = new JFrame("nwmain");
        _nwmain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _nwmain.setSize(800, 600);
        _nwmain.setResizable(false);
        _nwmain.setLocationRelativeTo(null);
        _nwmain.setUndecorated(true);
        _nwmain.setAlwaysOnTop(true);
        _nwmain.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/img_menu.png")))));
        _nwmain.setIconImage(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/icon.png"))).getImage());

        JButton btnNew = new JButton("");
        JButton btnCredits = new JButton("");
        JButton btnExit = new JButton("");

        // Button - New
        btnNew.setBorder(BorderFactory.createEmptyBorder());
        btnNew.setBounds(324, 300, 176, 49);
        btnNew.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_New.png"))));
        // Mouse Hover Event
        btnNew.addMouseListener(new MouseAdapter() {
            // On enter.
            public void mouseEntered(MouseEvent me) {
                btnNew.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HNew.png"))));
            }

            // On exit.
            public void mouseExited(MouseEvent me) {
                btnNew.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_New.png"))));
            }
        });
        // On Click Event.
        btnNew.addActionListener(action -> Controller.newOnClick(btnNew));

        // Button - Credits
        btnCredits.setBorder(BorderFactory.createEmptyBorder());
        btnCredits.setBounds(324, 360, 176, 49);
        btnCredits.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Credits.png"))));
        // Mouse Hover Event
        btnCredits.addMouseListener(new MouseAdapter() {
            // On enter.
            public void mouseEntered(MouseEvent me) {
                btnCredits.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HCredits.png"))));
            }

            // On exit.
            public void mouseExited(MouseEvent me) {
                btnCredits.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_Credits.png"))));
            }
        });
        // On Click Event.
        btnCredits.addActionListener(action -> Controller.creditsOnClick(btnCredits));

        // Button - Exit
        btnExit.setBounds(324, 420, 176, 49);
        btnExit.setBorder(BorderFactory.createEmptyBorder());
        btnExit.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Exit.png"))));
        // Mouse Hover Event
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

        _nwmain.add(btnNew);
        _nwmain.add(btnCredits);
        _nwmain.add(btnExit);
        _nwmain.pack();
    }

    /** Initialize Credits JWindow and its components. */
    private static void initializeCredits() {
        _credits = new JWindow(_nwmain);
        _credits.setSize(429, 446);
        _credits.setLocationRelativeTo(null);
        _credits.setAlwaysOnTop(true);
        _credits.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/img_credits.png")))));
        _credits.setIconImage(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/icon.png"))).getImage());

        // Button - Credits - Close
        JButton btnCreditsClose = new JButton("");
        btnCreditsClose.setBorder(BorderFactory.createEmptyBorder());
        btnCreditsClose.setBounds(125, 390, 176, 49);
        btnCreditsClose.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Close.png"))));
        // Mouse Hover Event.
        btnCreditsClose.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                btnCreditsClose.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HClose.png"))));
            }

            public void mouseExited(MouseEvent me) {
                btnCreditsClose.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_Close.png"))));
            }
        });
        // On Click Event.
        btnCreditsClose.addActionListener(action -> Controller.creditsCloseOnClick(btnCreditsClose));

        _credits.add(btnCreditsClose);
        _credits.pack();
    }

    /** Initialize Selection JWindow and its components. */
    private static void initializeSelection() {
        _selection = new JWindow(_nwmain);
        _selection.setSize(429, 446);
        _selection.setLocationRelativeTo(null);
        _selection.setAlwaysOnTop(true);
        _selection.setFocusableWindowState(true);
        _selection.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/img_newg.png")))));
        _selection.setIconImage(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/icon.png"))).getImage());

        JLabel lblSelectionName1 = new JLabel("Player 1:");
        JLabel lblSelectionName2 = new JLabel("Player 2:");
        JLabel lblSelectionName3 = new JLabel("Player 3:");
        JLabel lblSelectionName4 = new JLabel("Player 4:");
        JLabel lblSelectionMonsters = new JLabel("Random Monsters:");
        JLabel lblSelectionClass1 = new JLabel("Class:");
        JLabel lblSelectionClass2 = new JLabel("Class:");
        JLabel lblSelectionClass3 = new JLabel("Class:");
        JLabel lblSelectionClass4 = new JLabel("Class:");
        _cmbxClass1 = new JComboBox<>();
        _cmbxClass2 = new JComboBox<>();
        _cmbxClass3 = new JComboBox<>();
        _cmbxClass4 = new JComboBox<>();
        _txfName1 = new JTextField("Magnificent Leo", 1);
        _txfName2 = new JTextField("Mr. Bombastic", 1);
        _txfName3 = new JTextField("Garrus Valkyrion", 1);
        _txfName4 = new JTextField("Dominator Gavernicus", 1);
        _spnMonsters = new JSpinner(new SpinnerNumberModel(16, 16, 32, 1));

        // Selection - Labels
        lblSelectionName1.setBounds(15, 60, 50, 24);
        lblSelectionName1.setForeground(Color.lightGray);
        lblSelectionName2.setBounds(15, 90, 50, 24);
        lblSelectionName2.setForeground(Color.lightGray);
        lblSelectionName3.setBounds(15, 120, 50, 24);
        lblSelectionName3.setForeground(Color.lightGray);
        lblSelectionName4.setBounds(15, 150, 50, 24);
        lblSelectionName4.setForeground(Color.lightGray);
        lblSelectionClass1.setBounds(245, 60, 50, 24);
        lblSelectionClass1.setForeground(Color.lightGray);
        lblSelectionClass2.setBounds(245, 90, 50, 24);
        lblSelectionClass2.setForeground(Color.lightGray);
        lblSelectionClass3.setBounds(245, 120, 50, 24);
        lblSelectionClass3.setForeground(Color.lightGray);
        lblSelectionClass4.setBounds(245, 150, 50, 24);
        lblSelectionClass4.setForeground(Color.lightGray);
        lblSelectionMonsters.setBounds(15, 230, 165, 24);
        lblSelectionMonsters.setForeground(Color.lightGray);

        // Selection - Combo Boxes
        _cmbxClass1.setBounds(285, 60, 130, 24);
        _cmbxClass1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _cmbxClass1.setForeground(Color.lightGray);
        _cmbxClass1.setBackground(Color.BLACK);
        _cmbxClass1.addItem("Barbarian");
        _cmbxClass1.addItem("Fighter");
        _cmbxClass1.addItem("Paladin");
        _cmbxClass1.addItem("Palemaster");
        _cmbxClass1.addItem("Sorcerer");
        _cmbxClass2.setBounds(285, 90, 130, 24);
        _cmbxClass2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _cmbxClass2.setForeground(Color.lightGray);
        _cmbxClass2.setBackground(Color.BLACK);
        _cmbxClass2.addItem("Barbarian");
        _cmbxClass2.addItem("Fighter");
        _cmbxClass2.addItem("Paladin");
        _cmbxClass2.addItem("Palemaster");
        _cmbxClass2.addItem("Sorcerer");
        _cmbxClass3.setBounds(285, 120, 130, 24);
        _cmbxClass3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _cmbxClass3.setForeground(Color.lightGray);
        _cmbxClass3.setBackground(Color.BLACK);
        _cmbxClass3.addItem("Barbarian");
        _cmbxClass3.addItem("Fighter");
        _cmbxClass3.addItem("Paladin");
        _cmbxClass3.addItem("Palemaster");
        _cmbxClass3.addItem("Sorcerer");
        _cmbxClass4.setBounds(285, 150, 130, 24);
        _cmbxClass4.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _cmbxClass4.setForeground(Color.lightGray);
        _cmbxClass4.setBackground(Color.BLACK);
        _cmbxClass4.addItem("Barbarian");
        _cmbxClass4.addItem("Fighter");
        _cmbxClass4.addItem("Paladin");
        _cmbxClass4.addItem("Palemaster");
        _cmbxClass4.addItem("Sorcerer");

        // Selection - Text Fields
        _txfName1.setBounds(70, 60, 155, 24);
        _txfName1.setBackground(Color.BLACK);
        _txfName1.setForeground(Color.lightGray);
        _txfName1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _txfName2.setBounds(70, 90, 155, 24);
        _txfName2.setBackground(Color.BLACK);
        _txfName2.setForeground(Color.lightGray);
        _txfName2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _txfName3.setBounds(70, 120, 155, 24);
        _txfName3.setBackground(Color.BLACK);
        _txfName3.setForeground(Color.lightGray);
        _txfName3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        _txfName4.setBounds(70, 150, 155, 24);
        _txfName4.setBackground(Color.BLACK);
        _txfName4.setForeground(Color.lightGray);
        _txfName4.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));

        // Selection - Numeric Boxes
        _spnMonsters.setBounds(132, 230, 35, 24);
        _spnMonsters.setEditor(new JSpinner.DefaultEditor(_spnMonsters));
        _spnMonsters.setBackground(Color.BLACK);
        _spnMonsters.setForeground(Color.lightGray);
        _spnMonsters.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.DARK_GRAY));

        // Selection - OK Button
        JButton btnSelectionOK = new JButton("");
        btnSelectionOK.setBorder(BorderFactory.createEmptyBorder());
        btnSelectionOK.setBounds(9, 390, 176, 49);
        btnSelectionOK.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_OK.png"))));
        // Mouse Hover Event.
        btnSelectionOK.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                btnSelectionOK.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HOK.png"))));
            }

            public void mouseExited(MouseEvent me) {
                btnSelectionOK.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_OK.png"))));
            }
        });
        // On Click Event.
        btnSelectionOK.addActionListener(action -> Controller.selectionOkOnClick(btnSelectionOK));


        // Selection - Close Button
        JButton btnSelectionClose = new JButton("");
        btnSelectionClose.setBorder(BorderFactory.createEmptyBorder());
        btnSelectionClose.setBounds(245, 390, 176, 49);
        btnSelectionClose.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Close.png"))));
        // Mouse Hover Event.
        btnSelectionClose.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                btnSelectionClose.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_HClose.png"))));
            }

            public void mouseExited(MouseEvent me) {
                btnSelectionClose.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/buttons/Button_Close.png"))));
            }
        });
        // On Click Event.
        btnSelectionClose.addActionListener(action -> Controller.selectionCloseOnClick(btnSelectionClose));


        _selection.add(btnSelectionOK);
        _selection.add(btnSelectionClose);
        _selection.add(lblSelectionName1);
        _selection.add(lblSelectionName2);
        _selection.add(lblSelectionName3);
        _selection.add(lblSelectionName4);
        _selection.add(lblSelectionClass1);
        _selection.add(lblSelectionClass2);
        _selection.add(lblSelectionClass3);
        _selection.add(lblSelectionClass4);
        _selection.add(lblSelectionMonsters);
        _selection.add(_cmbxClass1);
        _selection.add(_cmbxClass2);
        _selection.add(_cmbxClass3);
        _selection.add(_cmbxClass4);
        _selection.add(_txfName1);
        _selection.add(_txfName2);
        _selection.add(_txfName3);
        _selection.add(_txfName4);
        _selection.add(_spnMonsters);
        _selection.pack();
    }

    /** Hide then dispose JFrame. */
    public static void dispose(WindowType type) {
        switch (type) {
            case MAIN -> {
                _nwmain.setVisible(false);
                _nwmain.dispose();
            }
            case CREDITS -> {
                _credits.setVisible(false);
                _credits.dispose();
            }
            case SELECTION -> {
                _selection.setVisible(false);
                _selection.dispose();
            }
            default -> {
                _nwmain.setVisible(false);
                _selection.dispose();
                _credits.dispose();
                _nwmain.dispose();
            }
        }
    }

    /**
     * Get the JComboBox value, defined by {@code number}.
     *
     * @param number  Defines which value to return.
     *
     * @return Selected JComboBox index. Each index corresponds to a {@link nwarena.model.toon.ClassType ClassType}.
     */
    public static int getComboBoxValue(int number) {
        switch (number) {
            case 0 -> {
                return _cmbxClass1.getSelectedIndex();
            }
            case 1 -> {
                return _cmbxClass2.getSelectedIndex();
            }
            case 2 -> {
                return _cmbxClass3.getSelectedIndex();
            }
            case 3 -> {
                return _cmbxClass4.getSelectedIndex();
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Get the JSpinner value.
     *
     * @return JSpinner value which determinates the amount of {@link nwarena.model.toon.Monster Monsters} in a
     * {@link nwarena.model.Game Game}.
     */
    public static int getMonsterCount() {
        try {
            _spnMonsters.commitEdit();
        } catch (java.text.ParseException exc) {
            Controller.exit(401);
        }
        return (Integer) _spnMonsters.getValue();
    }

    /**
     * Get the JTextField value, defined by {@code number}.
     *
     * @param number  Defines which value to return.
     *
     * @return Selected JTextField value. Each value will be used as a {@link nwarena.model.toon.Player Player's} name.
     */
    public static String getTextFieldValue(int number) {
        switch (number) {
            case 0 -> {
                return _txfName1.getText();
            }
            case 1 -> {
                return _txfName2.getText();
            }
            case 2 -> {
                return _txfName3.getText();
            }
            case 3 -> {
                return _txfName4.getText();
            }
            default -> {
                return "";
            }
        }
    }

    /**
     * Set visible property to {@code visible}.
     *
     * @param type  Defines which component to alter.
     * @param visible  Sets whether component will be visible or not.
     */
    public static void setVisible(WindowType type, boolean visible) {
        switch (type) {
            case MAIN -> _nwmain.setVisible(visible);
            case CREDITS -> _credits.setVisible(visible);
            case SELECTION -> _selection.setVisible(visible);
            default -> {
                _nwmain.setVisible(visible);
                _selection.setVisible(visible);
                _credits.setVisible(visible);
                _nwmain.setVisible(visible);
            }
        }
    }

    /**
     * Show message on a {@link JOptionPane}.
     *
     * @param title  Defines the title of the message.
     * @param text  Defines the body of the message.
     */
    public static void showMessage(String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }

    /** Play Sound Effect. */
    public static void playSoundEffect() {
        // Don't forget to reset position.
        _soundEffect.setMicrosecondPosition(0);
        _soundEffect.start();
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