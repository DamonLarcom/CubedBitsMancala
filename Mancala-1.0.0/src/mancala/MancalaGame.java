package mancala;

import mancala.data.Cell;
import mancala.data.House;
import mancala.data.Loop;
import mancala.data.Store;

import javax.swing.*;
import java.awt.*;

public class MancalaGame {
    private static final Color player1Color = new Color(0x87cefa);

    private static final Color player2Color = new Color(0xffd700);

    private Object ownerOfTurn;

    private Object player1;

    private Object player2;

    private Store player1Store;

    private Store player2Store;

    private JLabel announce;

    private Loop<Cell> cells;

    private JFrame gameWindow;

    public MancalaGame() {
        cells = new Loop<>();
        gameWindow = new JFrame("Mancala");
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLayout(new GridBagLayout());

        player1 = new Object();
        player2 = new Object();
        ownerOfTurn = player1;

        //Used to say the turn and victory
        announce = new JLabel("Player 1");
        announce.setFont(Mancala.GAME_FONT);

        GridBagConstraints announceConstraints = new GridBagConstraints();
        announceConstraints.gridx = 0;
        announceConstraints.gridy = 0;
        announceConstraints.gridwidth = 8;
        gameWindow.add(announce, announceConstraints);

        GridBagConstraints storeConstraints = new GridBagConstraints();
        storeConstraints.gridx = 0;
        storeConstraints.gridy = 1;
        storeConstraints.gridheight = 5;

        //Note: The order in which Houses and Stores are added is specific and critical.

        //Player 1 Store
        player1Store = new Store(Mancala.GAME_FONT, player1Color);
        addCell(player1Store, storeConstraints);

        //Player 2 house creation
        GridBagConstraints houseConstraints = new GridBagConstraints();
        houseConstraints.gridx = 1;
        houseConstraints.gridy = 4;
        houseConstraints.gridheight = 2;
        make6HouseRow(houseConstraints, 1, player2, player2Color);

        //Player 2 Store
        storeConstraints.gridx = 7;
        player2Store = new Store(Mancala.GAME_FONT, player2Color);
        addCell(player2Store, storeConstraints);

        //Player 1 House creation
        houseConstraints.gridy = 1;
        houseConstraints.gridx = 6;
        make6HouseRow(houseConstraints, -1, player1, player1Color);
    }

    public void displayGame() {
        gameWindow.pack();
        gameWindow.setVisible(true);
        new JFrameRepainter(gameWindow, 30).start();
    }

    private void handleHouseClick(House house) {
        if(house.getOwner() == ownerOfTurn) {
            if(house.getSeeds() > 0) {
                clickHelper(house);
            }

            SwingUtilities.invokeLater(this::updateGameState);
        }
    }

    private void clickHelper(House house) {
        //Puts the loop to the house that was clicked
        cells.advanceTo(house);

        //Take the seeds from the current house and add one to every house in a loop until out
        for(int seeds = house.empty(); seeds > 0; --seeds) {
            cells.advance();
            cells.get().addASeed();
        }

        //If the very last house a seed was added to has more than one seed then click it.
        if(!(cells.get() instanceof Store) && cells.get().getSeeds() > 1) {
            clickHelper((House) cells.get());
        }
    }

    private void updateGameState() {
        int houseSeeds1 = 0;
        int houseSeeds2 = 0;

        for(Cell c : cells) {
            if(c instanceof House) {
                if(((House) c).getOwner() == ownerOfTurn)
                    houseSeeds1 += c.getSeeds();
                else
                    houseSeeds2 += c.getSeeds();
            }
        }

        if(houseSeeds1 == 0 && houseSeeds2 == 0) {
            ownerOfTurn = null;
            int seeds1 = player1Store.getSeeds();
            int seeds2 = player2Store.getSeeds();

            if(seeds1 > seeds2)
                announce.setText("Victory: Player 1");
            else if(seeds2 > seeds1)
                announce.setText("Victory: Player 2");
            else
                announce.setText("Tie");
        }
        else if(houseSeeds2 != 0) {
            if(ownerOfTurn == player1) {
                ownerOfTurn = player2;
                announce.setText("Player 2");
            }
            else {
                ownerOfTurn = player1;
                announce.setText("Player 1");
            }
        }
    }

    private void make6HouseRow(GridBagConstraints constraints, int skip, Object player, Color color) {
        for(int i = 0; i < 6; ++i) {
            House newHouse = new House(3, player, color, Mancala.GAME_FONT);
            newHouse.addActionListener(e -> handleHouseClick((House) e.getSource()));
            addCell(newHouse, constraints);
            constraints.gridx += skip;
        }
    }

    private <T extends JComponent & Cell> void addCell(T cell, GridBagConstraints constraints) {
        cells.add(cell);
        gameWindow.add(cell, constraints);
    }
}