package nwarena.view.gamegridbutton;

import nwarena.view.Designer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Used in {@link nwarena.view.Designer nwarena View}, GameGridButton extends the {@link JButton} class.
 * <p>
 *     A GameGridButton is used to mark a space on the Grid, regardless if it's occupied by a
 *     {@link nwarena.model.toon.Player Player}, or a {@link nwarena.model.toon.Monster Monster}.
 *     It also provides functions that can change the {@link State} of the
 *     surrounding GameGridButtons.
 * </p>
 *
 * @author  Peter Mavrofrydis
 * @version 1.1
 * @since   01/08/2022
 * @see JButton
 */
public class GameGridButton extends JButton {

    private final int _x, _y;
    private State _state;
    public boolean HasMonster = false;
    public boolean HasPlayer = false;

    /**
     * Initializes coordinates, state and visual style.
     *
     * @param x  The x coordinate.
     * @param y  The y coordinate.
     */
    public GameGridButton(int x, int y) {
        // Store coordinates.
        _x = x / 40;
        _y = y / 40;
        this.setBounds(x, y, 40, 40);
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#7d4005")));
        // Set State to NEUTRAL.
        setState(State.NEUTRALBLOCK);
    }

    /**
     * Set state of GameGridButton.
     *
     * @param state  New state.
     */
    public void setState(State state) {
        switch (state) {
            case FREEBLOCK -> {
                this.setForeground(Color.decode("#01bf00"));
                this.setBackground(Color.decode("#01bf00"));
                _state = State.FREEBLOCK;
            }
            case ATTACKBLOCK -> {
                this.setForeground(Color.decode("#c80a0a"));
                this.setBackground(Color.decode("#c80a0a"));
                _state = State.ATTACKBLOCK;
            }
            case NEUTRALBLOCK -> {
                this.setForeground(Color.decode("#383838"));
                this.setBackground(Color.decode("#383838"));
                _state = State.NEUTRALBLOCK;
            }
        }
    }

    /**
     * Set state of surrounding GameGridButtons.
     *
     * @param clear  State will be set to NEUTRAL if true.
     */
    public void setSurroundState(boolean clear) {
        int x_min = _x - 1, x_max = _x + 1, y_min = _y - 1, y_max = _y + 1;

        // Ensure that x,y point to correct GameGridButtons.
        if (x_min < 1) x_min = 1;
        if (x_max > 14) x_max = 14;
        if (y_min < 1) y_min = 1;
        if (y_max > 11) y_max = 11;

        // Get surrounding GameGridButtons.
        for (int x_cnt = x_min; x_cnt <= x_max; x_cnt++) {
            for (int y_cnt = y_min; y_cnt <= y_max; y_cnt++) {
                // Set state of GameGridButtons to NEUTRAL if clear is true.
                if (clear) Designer.getGridButton(x_cnt, y_cnt).setState(State.NEUTRALBLOCK);
                else {
                    // Check if GameGridButton has a player or a Monster on it.
                    if (Designer.getGridButton(x_cnt, y_cnt).HasPlayer)
                        Designer.getGridButton(x_cnt, y_cnt).setState(State.ATTACKBLOCK);
                    else
                        Designer.getGridButton(x_cnt, y_cnt).setState(State.FREEBLOCK);
                }
            }
        }
        // Current block is always state NEUTRAL.
        this.setState(State.NEUTRALBLOCK);
    }

    /**
     * Set icon of GameGridButton.
     *
     * @param type  New icon type.
     */
    public void setIcon(IconType type) {
        switch (type) {
            case PLAYER1 ->
                    this.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/img_p1.png"))));
            case PLAYER2 ->
                    this.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/img_p2.png"))));
            case PLAYER3 ->
                    this.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/img_p3.png"))));
            case PLAYER4 ->
                    this.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/img_p4.png"))));
            case MONSTER ->
                    this.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/img_m.png"))));
            case EMPTY -> super.setIcon(null);
        }
    }

    /**
     * Get the coordinates of GameGridButton.
     *
     * @return Coordinates as {@link Point}.
     */
    public Point getCoordinates() {
        return new Point(_x, _y);
    }

    /**
     * Get the state of GameGridButton.
     *
     * @return Current state.
     */
    public State getState() {
        return _state;
    }

}