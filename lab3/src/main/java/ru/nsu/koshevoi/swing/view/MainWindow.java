package ru.nsu.koshevoi.swing.view;

import ru.nsu.koshevoi.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ModelListener {

    private final PacManModel model;
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
            setPreferredSize(new Dimension(board.getWidth() * 20, board.getHeight() * 20));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ArrayList<Wall> walls = (ArrayList<Wall>) board.getWalls();
            for (Wall wall : walls) {
                g.setColor(Color.BLACK);
                g.fillRect(wall.getX() * 20, wall.getY() * 20, 20, 20);
            }

            ArrayList<PowerPellet> powerPellets = (ArrayList<PowerPellet>) board.getPowerPellets();
            for (PowerPellet pellet : powerPellets) {
                g.setColor(Color.YELLOW);
                g.fillOval(pellet.getX() * 20, pellet.getY() * 20, 20, 20);
            }

            PacMan pacMan = model.getPacMan();
            g.setColor(Color.BLUE);
            g.fillOval(pacMan.getX() * 20, pacMan.getY() * 20, 20, 20);

            ArrayList<Ghost> ghosts = (ArrayList<Ghost>) model.getGhosts();
            for (Ghost ghost : ghosts) {
                g.setColor(Color.RED);
                g.fillOval(ghost.getX() * 20, ghost.getY() * 20, 20, 20);
            }
        }
    }
}