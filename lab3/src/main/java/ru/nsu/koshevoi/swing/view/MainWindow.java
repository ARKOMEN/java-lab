package ru.nsu.koshevoi.swing.view;

import ru.nsu.koshevoi.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ModelListener {

    private final PacManModel model;
    private final static int SIZE = 20;
    private final BoardPanel boardPanel;

    public MainWindow(PacManModel model) {
        this.model = model;
        this.boardPanel = new BoardPanel(model.getBoard());

        initComponents();
        model.setListener(this);
        onModelChanged();
    }

    private void initComponents() {
        setTitle("Pac-Man Game");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(boardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void onModelChanged() {
        SwingUtilities.invokeLater(boardPanel::repaint);
    }

    private class BoardPanel extends JPanel {

        private final Board board;

        public BoardPanel(Board board) {
            this.board = board;
            setPreferredSize(new Dimension(board.getWidth() * SIZE, board.getHeight() * SIZE));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ArrayList<Wall> walls = (ArrayList<Wall>) board.getWalls();
            for (Wall wall : walls) {
                g.setColor(Color.BLACK);
                g.fillRect(wall.getX() * SIZE, wall.getY() * SIZE, SIZE, SIZE);
            }

            ArrayList<PowerPellet> powerPellets = (ArrayList<PowerPellet>) board.getPowerPellets();
            for (PowerPellet pellet : powerPellets) {
                g.setColor(Color.YELLOW);
                g.fillOval(pellet.getX() * SIZE, pellet.getY() * SIZE, SIZE, SIZE);
            }

            PacMan pacMan = model.getPacMan();
            g.setColor(Color.BLUE);
            g.fillOval(pacMan.getX(), pacMan.getY(), SIZE, SIZE);

            ArrayList<Ghost> ghosts = (ArrayList<Ghost>) model.getGhosts();
            for (Ghost ghost : ghosts) {
                g.setColor(Color.RED);
                g.fillOval(ghost.getX() * SIZE, ghost.getY() * SIZE, SIZE, SIZE);
            }

        }
    }
}