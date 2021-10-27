package view;

import model.Settings;
import controller.Game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupView extends JFrame {

    JTextField playerAmount, ptsNeededToWin, cardAmount;
    JLabel playerAmountLabel, ptsNeededLabel, cardAmountLabel, specialCardsLabel, cardStyleLabel;
    JButton standardSettings, cancel, startGame;
    JCheckBox specialCards;
    JRadioButton french, german;
    ButtonGroup cardStyleSelectors;
    JPanel buttonPlacer, rightButtonPlacer, spacer;
    JPanel playerAmountPanel, winAmountPanel, cardAmountPanel, specialCardsPanel, specialCardsLabelAligner, cardStylePanel, panelAligner;
    JPanel frenchAligner, germanAligner;

    Settings settings;
    Game game;
    JFrame thisWindow;

    public SetupView(Game game) {

        super("Spieleinstellungen");
        setResizable(false);
        this.getContentPane().setLayout(new BorderLayout());

        this.game = game;
        this.settings = game.getSettings();
        this.thisWindow = this;

        this.setUpLabels();
        this.setUpButtons();
        this.setUpTextfields();
        this.setUpMisc();
        this.setUpPanels();

        this.getRootPane().setDefaultButton(startGame);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(450, 270);
        this.setLocationRelativeTo(null);
        setVisible(true);


    }

    private void setUpButtons() {
        standardSettings = new JButton("Standardeinstellungen");
        standardSettings.addActionListener(new StandardListener());

        cancel = new JButton("Abbrechen");
        cancel.addActionListener(new CancelListener());

        startGame = new JButton("Spiel Starten");
        startGame.addActionListener(new StartListener());
    }

    private void setUpLabels() {
        playerAmountLabel = new JLabel("Anzahl Spieler:");
        ptsNeededLabel = new JLabel("Zum Gewinnen benötigte Punktzahl:");
        cardAmountLabel = new JLabel("Kartenmenge zu Beginn");
        specialCardsLabel = new JLabel("Zwei Karten setzen");
        cardStyleLabel = new JLabel("Kartenstil:");
    }

    private void setUpPanels() {
        spacer = new JPanel(new BorderLayout());
        buttonPlacer = new JPanel(new BorderLayout());
        rightButtonPlacer = new JPanel(new BorderLayout());
        playerAmountPanel = new JPanel(new BorderLayout());
        winAmountPanel = new JPanel(new BorderLayout());
        cardAmountPanel = new JPanel(new BorderLayout());
        specialCardsPanel = new JPanel(new BorderLayout());
        specialCardsLabelAligner = new JPanel(new BorderLayout());
        cardStylePanel = new JPanel(new BorderLayout());
        panelAligner = new JPanel(new GridLayout(5, 1));
        frenchAligner = new JPanel(new BorderLayout());
        germanAligner = new JPanel(new BorderLayout());
        Insets borderInsets = new Insets(5, 30, 5, 0);

        // setup of buttonPlacer

        rightButtonPlacer.add(startGame, BorderLayout.EAST);
        rightButtonPlacer.add(cancel, BorderLayout.WEST);

        buttonPlacer.add(rightButtonPlacer, BorderLayout.EAST);
        buttonPlacer.add(standardSettings, BorderLayout.WEST);

        // setup of playerAmountPanel

        playerAmountPanel.add(playerAmountLabel, BorderLayout.WEST);
        playerAmountPanel.add(playerAmount, BorderLayout.EAST);
        playerAmountPanel.setBorder(new EmptyBorder(borderInsets));

        // setup of winAmountPanel

        winAmountPanel.add(ptsNeededLabel, BorderLayout.WEST);
        winAmountPanel.add(ptsNeededToWin, BorderLayout.EAST);
        winAmountPanel.setBorder(new EmptyBorder(borderInsets));

        // setup of cardAmountPanel

        cardAmountPanel.add(cardAmountLabel, BorderLayout.WEST);
        cardAmountPanel.add(cardAmount, BorderLayout.EAST);
        cardAmountPanel.setBorder(new EmptyBorder(borderInsets));

        // setup of specialCardsPanel

        specialCardsPanel.add(specialCards, BorderLayout.WEST);

        // Start setup of cardStylePanel

        frenchAligner.add(french, BorderLayout.WEST);
        frenchAligner.setBorder(new EmptyBorder(0, 50, 0, 0));

        germanAligner.add(german, BorderLayout.WEST);
        germanAligner.setBorder(new EmptyBorder(0, 0, 0, 20));

        cardStylePanel.add(cardStyleLabel, BorderLayout.WEST);
        cardStylePanel.add(frenchAligner, BorderLayout.CENTER);
        cardStylePanel.add(germanAligner, BorderLayout.EAST);
        cardStylePanel.setBorder(new EmptyBorder(borderInsets));

        // setup of panelAligner

        panelAligner.add(playerAmountPanel);
        panelAligner.add(winAmountPanel);
        panelAligner.add(cardAmountPanel);
        panelAligner.add(specialCardsPanel);
        panelAligner.add(cardStylePanel);
        panelAligner.setBorder(new EmptyBorder(0,10,30,20));

        // setup of spacer

        spacer.add(panelAligner, BorderLayout.CENTER);
        spacer.add(buttonPlacer, BorderLayout.SOUTH);
        spacer.setBorder(new EmptyBorder(10, 10, 10, 10));

        // put spacer into JFrame

        this.getContentPane().add(spacer);
    }

    private void setUpTextfields() {
        Dimension textFieldDimenstion = new Dimension(50, 10);

        playerAmount = new JTextField();
        playerAmount.setPreferredSize(textFieldDimenstion);

        ptsNeededToWin = new JTextField();
        ptsNeededToWin.setPreferredSize(textFieldDimenstion);

        cardAmount = new JTextField();
        cardAmount.setPreferredSize(textFieldDimenstion);
    }

    private void setUpMisc() {
        specialCards = new JCheckBox("Mit Sonderkarten");

        french = new JRadioButton("Französisch");
        german = new JRadioButton("Deutsch");

        french.setSelected(true);

        cardStyleSelectors = new ButtonGroup();

        cardStyleSelectors.add(french);
        cardStyleSelectors.add(german);
    }

    class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int playerAmountValue, ptsNeededValue, cardAmountValue;

            try {
                playerAmountValue = Integer.parseInt(playerAmount.getText());
                ptsNeededValue = Integer.parseInt(ptsNeededToWin.getText());
                cardAmountValue = Integer.parseInt(cardAmount.getText());

                if (playerAmountValue > 8 || playerAmountValue < 2) {
                    throw new InvalidAmountException(InvalidAmountType.PLAYERAMOUNT);
                }

                if (ptsNeededValue < 1) {
                    throw new InvalidAmountException(InvalidAmountType.PTSNEEDED);
                }

                if (cardAmountValue < 2 || cardAmountValue > 25) {
                    throw new InvalidAmountException(InvalidAmountType.CARDAMOUNT);
                }

                if (german.isSelected()) {
                    throw new Exception();
                }


                settings.setPlayerCount(playerAmountValue);
                settings.setInitialCardCount(cardAmountValue);
                settings.setPointsNeededToWin(ptsNeededValue);
                settings.setPutTwoIdenticalCardsIsPossible(specialCards.isSelected());

                game.start();

                thisWindow.dispose();


            } catch (NumberFormatException nfe) {
                System.out.println("A textfield contained something that is not a number. (NumberFormatException)");
                JOptionPane.showMessageDialog(null, "Ein Textfeld beinhaltet etwas, was keine Zahl ist.\n" +
                        "Bitte geben Sie einen gültigen Wert ein.", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
            } catch (InvalidAmountException iae) {
                System.out.println("A value was too high or too low when setting up game settings.");
                switch (iae.getType()) {
                    case PLAYERAMOUNT:
                        JOptionPane.showMessageDialog(null, "An einem Spiel dürfen minimal zwei und maximal acht Personen teilnehmen.\n" +
                                "Bitte passen Sie die Spieleranzahl an", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                        break;
                    case CARDAMOUNT:
                        JOptionPane.showMessageDialog(null, "Es müssen mindestens zwei oder maximal 25 Karten verteilt werden.\n" +
                                "Bitte passen Sie die Kartenmenge an.", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                        break;
                    case PTSNEEDED:
                        JOptionPane.showMessageDialog(null, "Die Mindestanzahl Punkte, welche für einen Sieg benötigt wird,\n" +
                                "beträgt 1. Bitte passen Sie den Wert entsprechend an.", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
                        break;
                    default:
                        System.out.println("Error: falsely thrown InvalidAmountException in Startlistener.");
                        break;

                }
            } catch (Exception ex) {
                System.out.println("An unknown error occured.");
                ex.printStackTrace();

                JOptionPane.showMessageDialog(null, "Deutsche Karten wurden in dieser Version noch nicht eingebaut, bitte\n" +
                        "die französischen auswählen.", "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    class StandardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            playerAmount.setText("4");
            ptsNeededToWin.setText("200");
            cardAmount.setText("7");
            german.setSelected(false);
            french.setSelected(true);
            specialCards.setSelected(false);
        }
    }

    class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            settings = null;
            game = null;
            thisWindow.dispose();
            new StartupView();
        }
    }

}
