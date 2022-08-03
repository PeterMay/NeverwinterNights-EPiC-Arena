package nwarena.view;

import nwarena.controller.ArenaController;
import nwarena.view.gamegridbutton.GameGridButton;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * <h3> Designer - nwarena. </h3>
 * Provides JFrame initialization methods, background music and SFX playback methods
 * and getters for JComponent data and GameGridButtons.
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   30/07/2022
 */
public class Designer {

    private static int _backgroundMusicIndex = 0, _backgroundBattleMusicIndex = 0;
    private static Clip _backgroundMusic;
    private static Clip _soundEffect;
    private static JFrame _nwarena;
    private static JLabel _lblPlayerName;
    private static JLabel _lblPlayerClass;
    private static JLabel _lblPlayerHP;
    private static JLabel _lblPlayerAC;
    private static JLabel _lblPlayerAB;
    private static JTextArea _txtLog;
    private static GameGridButton[][] _gameGridButtons;

    /**
     * Initialization for JFrame, background music and SFX.
     * <p> Start music playback with the {@link #startBackgroundMusic(boolean) startBackgroundMusic} method. </p>
     * <p> Play SFX with the {@link #playSoundEffect() playSoundEffect} method. </p>
     */
    public Designer() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // Clips and files.
        _soundEffect = AudioSystem.getClip();
        _backgroundMusic = AudioSystem.getClip();
        _soundEffect.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/mus/mus_click.wav"))));
        // Initialize Arena JFrame.
        initializeArena();
    }

    /** Initialize JFrame and its components. */
    private static void initializeArena() {
        // JFrame visual properties.
        _nwarena = new JFrame("nwarena");
        _nwarena.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _nwarena.setSize(800, 600);
        _nwarena.setResizable(false);
        _nwarena.setLocationRelativeTo(null);
        _nwarena.setUndecorated(true);
        _nwarena.setAlwaysOnTop(true);
        _nwarena.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/img_gbackground.png")))));
        _nwarena.setIconImage(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/icon.png"))).getImage());

        // nwarena - Labels
        JLabel lblName = new JLabel("Player Name:");
        JLabel lblClass = new JLabel("Class:");
        JLabel lblHP = new JLabel("HP:");
        JLabel lblAC = new JLabel("AC:");
        JLabel lblAttackBonus = new JLabel("Attack Bonus:");
        _lblPlayerName = new JLabel();
        _lblPlayerClass = new JLabel();
        _lblPlayerHP = new JLabel();
        _lblPlayerAC = new JLabel();
        _lblPlayerAB = new JLabel();

        lblName.setBounds(620, 40, 100, 24);
        lblName.setForeground(Color.lightGray);
        lblClass.setBounds(620, 85, 200, 24);
        lblClass.setForeground(Color.lightGray);
        lblHP.setBounds(620, 130, 200, 24);
        lblHP.setForeground(Color.lightGray);
        lblAC.setBounds(620, 175, 200, 24);
        lblAC.setForeground(Color.lightGray);
        lblAttackBonus.setBounds(620, 220, 200, 24);
        lblAttackBonus.setForeground(Color.lightGray);
        _lblPlayerName.setBounds(630, 55, 200, 24);
        _lblPlayerName.setForeground(Color.lightGray);
        _lblPlayerClass.setBounds(630, 100, 200, 24);
        _lblPlayerClass.setForeground(Color.lightGray);
        _lblPlayerHP.setBounds(630, 145, 200, 24);
        _lblPlayerHP.setForeground(Color.lightGray);
        _lblPlayerAC.setBounds(630, 190, 200, 24);
        _lblPlayerAC.setForeground(Color.lightGray);
        _lblPlayerAB.setBounds(630, 235, 200, 24);
        _lblPlayerAB.setForeground(Color.lightGray);

        // nwarena - Grid Buttons
        _gameGridButtons = new GameGridButton[14][11];
        for (int cnt1 = 40; cnt1 <= 560; cnt1 += 40) {
            for (int cnt2 = 40; cnt2 <= 440; cnt2 += 40) {
                // By 40: makes placement of GameGridButton easier on JFrame.
                GameGridButton grdButton = new GameGridButton(cnt1, cnt2);
                grdButton.addActionListener(action -> ArenaController.gridButtonOnClick(grdButton));
                _gameGridButtons[(cnt1 / 40) - 1][(cnt2 / 40) - 1] = grdButton;
                _nwarena.add(grdButton);
            }
        }

        // nwarena - Terminate Button
        JButton btnExit = new JButton("");
        btnExit.setBorder(BorderFactory.createEmptyBorder());
        btnExit.setBounds(775, 13, 25, 25);
        btnExit.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Terminate.png"))));
        // On Click Event.
        btnExit.addActionListener(action -> ArenaController.exitOnClick());

        // nwarena - Rest Button
        JButton btnRest = new JButton("");
        btnRest.setBorder(BorderFactory.createEmptyBorder());
        btnRest.setBounds(620, 420, 58, 29);
        btnRest.setIcon(new ImageIcon(Objects.requireNonNull(Designer.class.getResource("/img/buttons/Button_Rest.png"))));
        // On Click Event.
        btnRest.addActionListener(action -> ArenaController.restOnClick());

        // nwarena - TextArea
        _txtLog = new JTextArea();
        _txtLog.setLineWrap(true);
        _txtLog.setWrapStyleWord(true);
        _txtLog.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
        _txtLog.setBackground(Color.BLACK);
        _txtLog.setForeground(Color.lightGray);
        _txtLog.setEditable(false);
        _txtLog.setAutoscrolls(true);
        JScrollPane scrollTextArea = new JScrollPane(_txtLog);
        scrollTextArea.setBounds(45, 493, 553, 105);

        _nwarena.add(lblName);
        _nwarena.add(lblClass);
        _nwarena.add(lblHP);
        _nwarena.add(lblAC);
        _nwarena.add(lblAttackBonus);
        _nwarena.add(_lblPlayerName);
        _nwarena.add(_lblPlayerClass);
        _nwarena.add(_lblPlayerHP);
        _nwarena.add(_lblPlayerAC);
        _nwarena.add(_lblPlayerAB);
        _nwarena.add(btnExit);
        _nwarena.add(btnRest);
        _nwarena.add(scrollTextArea);
        _nwarena.pack();
    }

    /**
     * Append JTextArea text.
     *
     * @param text  Text to append.
     */
    public static void appendLogText(String text) {
        _txtLog.append(text + "\n");
        _txtLog.setCaretPosition(_txtLog.getDocument().getLength());
    }

    /** Hide then dispose JFrame. */
    public static void dispose(){
        _nwarena.dispose();
        _backgroundMusic.stop();
        _backgroundMusic.close();
        _soundEffect.stop();
        _soundEffect.close();
    }

    /** Play Sound Effect. */
    public static void playSoundEffect() {
        // Don't forget to reset position.
        _soundEffect.setMicrosecondPosition(0);
        _soundEffect.start();
    }

    /**
     * Set a Player's stat on View.
     *
     * @param stat  {@link nwarena.view.PlayerStat PlayerStat} to set.
     * @param value  Value of stat.
     */
    public static void setPlayerStat(PlayerStat stat, String value) {
        switch (stat) {
            case NAME -> _lblPlayerName.setText(value);
            case CLASS -> _lblPlayerClass.setText(value);
            case AC -> _lblPlayerAC.setText(value);
            case HP -> _lblPlayerHP.setText(value);
            case ATTACK -> _lblPlayerAB.setText(value);
        }
    }


    /**
     * Set visible property to {@code visible}.
     *
     * @param visible  Sets whether component will be visible or not.
     */
    public static void setVisible(boolean visible) {
        _nwarena.setVisible(visible);
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

    /** Stop Background Music. */
    public static void stopBackgroundMusic() {
        _backgroundMusic.stop();
    }

    /** Start Background Music. */
    public static void startBackgroundMusic(boolean finish) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String musicFile = "";
        // Stop current background music and close file.
        _backgroundMusic.stop();
        _backgroundMusic.close();
        // Get random music file - mus_end if this is the end of the game.
        if (finish) musicFile = "mus_end.wav";
        else {
            int randomTrack;
            // Repeat till background music is different from last played background music.
            do {
                Random rand = new Random();
                randomTrack = (byte) (rand.nextInt(5) + 1);
                switch (randomTrack) {
                    case 1 -> musicFile = "mus_aribeth.wav";
                    case 2 -> musicFile = "mus_beggarsnest.wav";
                    case 3 -> musicFile = "mus_charwood.wav";
                    case 4 -> musicFile = "mus_neverwinterwood.wav";
                    case 5 -> musicFile = "mus_undermountain.wav";
                    default -> System.out.println(randomTrack);
                }
            } while (randomTrack == _backgroundMusicIndex);
            _backgroundMusicIndex = randomTrack;
        }
        // Open and don't forget to reset position.
        _backgroundMusic.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Designer.class.getResource("/mus/" + musicFile))));
        _backgroundMusic.setMicrosecondPosition(0);
        _backgroundMusic.start();
    }

    /** Start Battle Music. */
    public static void startBattleMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String musicFile = "";
        // Stop current background music and close file.
        _backgroundMusic.stop();
        _backgroundMusic.close();
        int randomTrack;
        // Repeat till battle music is different from last played battle music.
        do {
            Random rand = new Random();
            randomTrack = (byte) (rand.nextInt(3) + 1);
            switch (randomTrack) {
                case 1 -> musicFile = "mus_battle1.wav";
                case 2 -> musicFile = "mus_battle2.wav";
                case 3 -> musicFile = "mus_battle3.wav";
                default -> ArenaController.exit();
            }
        } while (randomTrack == _backgroundBattleMusicIndex);
        _backgroundBattleMusicIndex = randomTrack;
        // Open and don't forget to reset position.
        _backgroundMusic.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Designer.class.getResource("/mus/" + musicFile))));
        _backgroundMusic.setMicrosecondPosition(0);
        _backgroundMusic.start();
    }

    /**
     * Get the {@link GameGridButton} on coordinates defined
     * by {@code x} and {@code y}.
     *
     * @param x  The x coordinate.
     * @param y  The y coordinate.
     * @return The appropriate {@link GameGridButton}
     * on ({@code x}, {@code y}).
     */
    public static GameGridButton getGridButton(int x, int y) {
        if (x > 14 || x < 1) x = 1;
        if (y > 11 || y < 1) y = 1;
        return _gameGridButtons[x - 1][y - 1];
    }

}