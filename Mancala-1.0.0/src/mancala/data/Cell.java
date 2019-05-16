package mancala.data;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public interface Cell {
    Border dualBevelBorder = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
                                                                BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    void addASeed();

    int empty();

    int getSeeds();
}
