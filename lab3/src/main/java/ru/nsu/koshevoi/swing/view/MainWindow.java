package ru.nsu.koshevoi.swing.view;

import ru.nsu.koshevoi.model.*;
import ru.nsu.koshevoi.swing.controller.SwingController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
            case WIN -> {model.setTimeout(5000);displayWin();}
            case TABLE -> displayTable();
        }
    }

    private void displayTable(){
        Map<String, List<String>> map = model.parser();
        Object[][] data = new Object[map.size()][5];
        for(int i = 0; i < map.size(); i++){
            for(int j = 0; j < 5; j++){
            }
        }
        int i = 0;
        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            data[i] = new Object[]{entry.getKey(), entry.getValue().get(0), entry.getValue().get(1), entry.getValue().get(2), entry.getValue().get(3)};
            i++;
        }
        String[] header = {"level number", "first","second","third","fourth"};
        JTable table = new JTable(data, header);
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.cyan);
        table.setAutoCreateRowSorter(true);
        this.getContentPane().removeAll();
        this.getContentPane().add(table);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
    private void displayDidNotEnterRecords() {
        JLabel label = new JLabel("GAME OVER");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 18));
        label.setOpaque(true);
        label.setBackground(Color.RED);
        label.setPreferredSize(new Dimension(400, 100));
        this.getContentPane().removeAll();
        this.getContentPane().add(label, BorderLayout.CENTER);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void displayWin() {
        JPanel panel = new JPanel();
        JLabel label1 = new JLabel("WIN!");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("Serif", Font.BOLD, 18));
        label1.setOpaque(true);
        label1.setPreferredSize(new Dimension(400, 100));
        JLabel label2 = new JLabel("Enter your name");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setFont(new Font("Serif", Font.BOLD, 18));
        label2.setOpaque(true);
        label2.setPreferredSize(new Dimension(400, 100));
        JTextField answer = new JTextField(controller.getInputModel(), "", 6);
        answer.setMinimumSize(answer.getPreferredSize());
        answer.setMaximumSize(answer.getPreferredSize());
        JButton button = new JButton("Enter");
        button.setActionCommand(SwingController.SUBMIT_ANSWER);
        button.addActionListener(controller);
        this.getContentPane().removeAll();
        panel.add(label1, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(label2, BorderLayout.CENTER);
        panel.add(answer);
        panel.add(button);
        this.getContentPane().add(panel);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }


    private class BoardPanel extends JPanel {

        private Board board;

        public BoardPanel(Board board) {
            this.board = board;
            setPreferredSize(new Dimension(board.getWidth() * SIZE, (board.getHeight() + 1)* SIZE));
        }
        @Override
        protected void paintComponent(Graphics g) {
            setBackground(Color.WHITE);
            super.paintComponent(g);
            ArrayList<Wall> walls = (ArrayList<Wall>) board.getWalls();
            for (Wall wall : walls) {
                g.setColor(Color.BLACK);
                g.fillRect(wall.getX() * SIZE, wall.getY() * SIZE, SIZE, SIZE);
            }
            g.fillRect(0,HEIGHT*SIZE+360,SIZE*SIZE+180, SIZE);

            ArrayList<PowerPellets> powerPellets = (ArrayList<PowerPellets>) model.getPowerPellets();
            for (PowerPellets pellets : powerPellets) {
                g.setColor(Color.BLACK);
                g.fillOval(pellets.getX() * SIZE + 7, pellets.getY() * SIZE + 6, 5, 5);
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
            String string = "Power pellets: " + model.getPacMan().getScore();
            g.drawString(string, 20, SIZE*SIZE + 15);
        }
    }
}