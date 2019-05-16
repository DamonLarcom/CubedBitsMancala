package mancala.data;

import javax.swing.*;
import java.awt.*;

public class House extends JButton implements Cell {
    private int seeds;

    private Object owner;

    public House(int seeds, Object owner, Color color, Font font) {
        super(String.valueOf(seeds));

        this.seeds = seeds;
        this.owner = owner;

        setFont(font);
        setBorder(Cell.dualBevelBorder);
        setBackground(color);
        setMinimumSize(new Dimension(70, 70));
        setPreferredSize(getMinimumSize());
    }

    public Object getOwner() {
        return owner;
    }

    @Override
    public void addASeed() {
        ++seeds;
        updateSeedDisplay();
    }

    @Override
    public int empty() {
        int seeds = this.seeds;
        this.seeds = 0;

        updateSeedDisplay();

        return seeds;
    }

    @Override
    public int getSeeds() {
        return seeds;
    }

    private void updateSeedDisplay() {
        setText(String.valueOf(seeds));
    }
}
