package mancala;

import javax.swing.*;
import java.awt.*;

public class Mancala
{
    public static final Font GAME_FONT = new Font("Magneto", Font.PLAIN, 40);

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            MancalaGame mancala = new MancalaGame();
            mancala.displayGame();
        });
    }
}
