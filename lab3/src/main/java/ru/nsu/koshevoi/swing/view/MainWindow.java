package ru.nsu.koshevoi.swing.view;

import ru.nsu.koshevoi.model.*;
import ru.nsu.koshevoi.swing.controller.SwingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class MainWindow extends JFrame implements ModelListener {

    private final PacManModel model;
    private final static int SIZE = 20;
    private final BoardPanel boardPanel;
    private SwingController controller;

    public MainWindow(PacManModel model, SwingController controller) {
        this.model = model;
        this.boardPanel = new BoardPanel(model.getBoard());
        this.controller = controller;
        initComponents();
        model.setListener(this);
        onModelChanged();
    }

    private void initComponents() {
        setTitle("Pac-Man Game");
        setResizable(false);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    model.close();
                    System.exit(0);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(boardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void onModelChanged() {
         switch (model.getState()){
             case ALIVE -> SwingUtilities.invokeLater(boardPanel::repaint);
             case DEAD -> displayDidNotEnterRecords();
             case WIN -> displayWin();
             case WAIT -> System.out.println("WAIT");
         }
    }

    private void displayDidNotEnterRecords() {
        JLabel label = new JLabel("Did not enter records");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setOpaque(true);
        label.setBackground(Color.RED);
        label.setPreferredSize(new Dimension(400, 100));
        JButton button = new JButton("restart");
        button.addActionListener(controller);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        this.getContentPane().removeAll();
        this.getContentPane().add(button, BorderLayout.CENTER);
        this.getContentPane().add(label, BorderLayout.CENTER);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void displayWin() {
        JLabel label = new JLabel("WIN! Your time: " + model.getTime()/60);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setOpaque(true);
        label.setBackground(Color.GREEN);
        label.setPreferredSize(new Dimension(400, 100));
        this.getContentPane().removeAll();
        this.getContentPane().add(label, BorderLayout.CENTER);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private class BoardPanel extends JPanel {

        private final Board board;

        public BoardPanel(Board board) {
            this.board = board;
            setPreferredSize(new Dimension(board.getWidth() * SIZE, (board.getHeight() + 1)* SIZE));
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
            g.fillRect(0,HEIGHT*SIZE+360,SIZE*SIZE+180, SIZE);

            Map<Integer, Map<Integer, Boolean>> powerPellets = board.getPowerPellets();
            for (Map.Entry<Integer, Map<Integer, Boolean>> pellet : powerPellets.entrySet()) {
                for(Map.Entry<Integer, Boolean> pel : pellet.getValue().entrySet()){
                    g.setColor(Color.BLACK);
                    g.fillOval(pel.getKey() * SIZE + 7, pellet.getKey() * SIZE + 6, 5, 5);
                }
            }

            PacMan pacMan = model.getPacMan();
            g.setColor(Color.BLUE);
            g.fillOval(pacMan.getX()*SIZE, pacMan.getY()*SIZE, SIZE, SIZE);

            ArrayList<Ghost> ghosts = (ArrayList<Ghost>) model.getGhosts();
            for (Ghost ghost : ghosts) {
                g.setColor(Color.RED);
                g.fillOval(ghost.getX() * SIZE, ghost.getY() * SIZE, SIZE, SIZE);
            }
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            String string = "Power pellets: " + model.getScore();
            g.drawString(string, 20, SIZE*SIZE + 15);

        }
    }
}