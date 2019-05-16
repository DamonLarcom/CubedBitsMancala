package mancala.data;

import javax.swing.*;
import java.awt.*;

public class Store extends JLabel implements Cell {
    private int seeds;

    public Store(Font font, Color color) {
        super("0");
        this.seeds = 0;

        setHorizontalAlignment(SwingConstants.CENTER);
        setMinimumSize(new Dimension(70, 175));
        setPreferredSize(getMinimumSize());
        setBorder(Cell.dualBevelBorder);
        setFont(font);
        setBackground(color);
        setOpaque(true);
    }

    @Override
    public void addASeed() {
        ++seeds;
        updateText();
    }

    public int empty() {
        int seeds = this.seeds;
        this.seeds = 0;

        updateText();

        return seeds;
    }

    private void updateText() {
        setText(String.valueOf(seeds));
    }

    @Override
    public int getSeeds() {
        return seeds;
    }
}
