package view;

import controller.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartupView extends JFrame {

    JButton newGame, quit;
    JPanel buttonHolder, buttonPlacer, spacer;
    Color bgColour;
    ImageIcon tsImage;
    JLabel tsImageHolder;

    JFrame thisWindow;

    public StartupView() {

        super("Startbildschirm - Tschau Sepp");

        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(500, 200);
        this.setLocationRelativeTo(null);
        this.thisWindow = this;

        bgColour = new Color(32, 115,36);

        tsImage = ImageHandler.loadImage("/img/title.png");
        tsImageHolder = new JLabel(tsImage);

        buttonHolder = new JPanel(new BorderLayout());
        buttonPlacer = new JPanel(new BorderLayout());
        spacer = new JPanel(new BorderLayout());
        spacer.setBorder(new EmptyBorder(10, 10, 10, 10));

        newGame = new JButton("Neues Spiel");
        newGame.addActionListener(new NewGameListener());

        quit = new JButton("Beenden");
        quit.addActionListener(new QuitListener());

        buttonHolder.add(newGame, BorderLayout.SOUTH);
        buttonHolder.add(quit, BorderLayout.NORTH);
        buttonHolder.setBackground(bgColour);

        buttonPlacer.add(buttonHolder, BorderLayout.SOUTH);
        buttonPlacer.setBackground(bgColour);

        spacer.add(buttonPlacer, BorderLayout.EAST);
        spacer.add(tsImageHolder, BorderLayout.CENTER);
        spacer.setBackground(bgColour);

        this.getContentPane().add(spacer, BorderLayout.CENTER);

        this.getRootPane().setDefaultButton(newGame);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setVisible(true);


    }


    class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Game game = new Game();
            thisWindow.dispose();
        }
    }
}
