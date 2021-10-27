package view;

import controller.Game;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

public class GameView extends JFrame {

    JFrame thisWindow;

    JPanel spacer, sidebar, buttonHolder, callSelectionHolder, cardsHolder, newCardHolder, newCardPositioner, centralArea;
    JButton completeTurn, getCard;
    JRadioButton tschau, sepp, noCall;
    ButtonGroup callOutSelectors;
    JList<Player> players;
    JList<Card> cards;
    JScrollPane playerScrollView, cardScrollView;
    Color bgColour, sideBarColour;

    JLabel centralCard, newCard;

    Game game;

    public GameView(Game game) {

        super("Tschau Sepp");
        setResizable(true);
        this.getContentPane().setLayout(new BorderLayout());

        this.thisWindow = this;
        this.game = game;
        this.bgColour = new Color(32, 115,36);
        this.sideBarColour = new Color(213, 226, 213);

        this.setUpJLists();
        this.setUpRadioButtons();
        this.setUpMisc();
        this.setUpScrollView();
        this.setUpPanels();


        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpPanels() {
        callSelectionHolder = new JPanel(new GridLayout(3, 0));
        callSelectionHolder.add(noCall);
        callSelectionHolder.add(tschau);
        callSelectionHolder.add(sepp);
        callSelectionHolder.setBackground(sideBarColour);
        callSelectionHolder.setBorder(new EmptyBorder(10,0,10, 0));

        buttonHolder = new JPanel(new BorderLayout());
        buttonHolder.add(callSelectionHolder, BorderLayout.CENTER);
        buttonHolder.add(completeTurn, BorderLayout.SOUTH);
        buttonHolder.setBackground(sideBarColour);
        buttonHolder.setBorder(new EmptyBorder(10, 10, 10, 10));

        sidebar = new JPanel(new BorderLayout());
        sidebar.add(buttonHolder, BorderLayout.SOUTH);
        sidebar.add(playerScrollView);
        sidebar.setBackground(sideBarColour);
        sidebar.setPreferredSize(new Dimension(175, 0));

        cardsHolder = new JPanel(new BorderLayout());
        cardsHolder.setBackground(bgColour);
        cardsHolder.add(cardScrollView, BorderLayout.CENTER);
        cardsHolder.setPreferredSize(new Dimension(0, 275));

        newCardHolder = new JPanel(new BorderLayout());
        newCardHolder.setBorder(new EmptyBorder(10, 10, 5, 10));
        newCardHolder.setBackground(bgColour);
        newCardHolder.add(newCard, BorderLayout. CENTER);
        newCardHolder.add(getCard, BorderLayout.SOUTH);

        newCardPositioner = new JPanel(new BorderLayout());
        newCardPositioner.setBackground(bgColour);
        newCardPositioner.add(newCardHolder, BorderLayout.NORTH);

        centralArea = new JPanel(new BorderLayout());
        centralArea.setBackground(bgColour);
        centralArea.add(centralCard, BorderLayout.CENTER);
        centralArea.add(newCardPositioner, BorderLayout.WEST);

        spacer = new JPanel(new BorderLayout());
        spacer.add(cardsHolder, BorderLayout.SOUTH);
        spacer.add(centralArea, BorderLayout.CENTER);
        spacer.setBackground(bgColour);

        this.getContentPane().add(sidebar, BorderLayout.WEST);
        this.getContentPane().add(spacer, BorderLayout.CENTER);
    }

    private void setUpRadioButtons() {
        tschau = new JRadioButton("Tschau");
        sepp = new JRadioButton("Sepp");
        noCall = new JRadioButton("Kein Ausruf");

        callOutSelectors = new ButtonGroup();
        callOutSelectors.add(tschau);
        callOutSelectors.add(sepp);
        callOutSelectors.add(noCall);
    }

    private void setUpJLists() {
        players = new JList<>(game);
        players.setBorder(new EmptyBorder(0,0,0,0));
        players.setCellRenderer(new PlayerRenderer());
        players.setBackground(sideBarColour);
        players.setSelectedIndex(game.getCurrentPlayer());

        cards = new JList<>(this.game.getPlayers().get(this.game.getCurrentPlayer()));
        cards.setBackground(bgColour);
        cards.setBorder(new EmptyBorder(0,0,0,0));
        cards.setCellRenderer(new CardRenderer());
        cards.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        cards.setVisibleRowCount(1);
    }

    private void setUpScrollView() {
        playerScrollView = new JScrollPane(players);
        playerScrollView.setBorder(new EmptyBorder(0,0,0,0));

        cardScrollView = new JScrollPane(cards);
        cardScrollView.setBorder(new EmptyBorder(0,0,0,0));
    }

    private void setUpMisc() {
        completeTurn = new JButton("Zug Ausführen");
        completeTurn.addActionListener(new SubmitButtonListener());
        getCard = new JButton("Karte Aufnehmen");
        getCard.addActionListener(new NewCardButtonListener());
        centralCard = new JLabel(ImageHandler.loadImage(game.getCentralCardStack().getTopCard().getFilename()));
        newCard = new JLabel(ImageHandler.loadImage("/img/cardback.png"));
    }

    public void showWinDialogue() {
        JOptionPane.showMessageDialog(null, game.getPlayers().get(game.getCurrentPlayer()).getName() + " hat gewonnen. Bravo!"
                , "Gewinner steht fest", JOptionPane.WARNING_MESSAGE);
        this.dispose();
        new StartupView();
    }

    class SubmitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Call call;

            if (tschau.isSelected()) {
                call = Call.TSCHAU;
            } else if (sepp.isSelected()) {
                call = Call.SEPP;
            } else {
                call = Call.NONE;
            }

            game.getPlayers().get(game.getCurrentPlayer()).setCall(call);

            int[] selectedCards = cards.getSelectedIndices();
            Vector<Integer> selCardsVec = new Vector<>();
            for (int num : selectedCards) {
                selCardsVec.add(num);
            }

            if (game.getSettings().isPutTwoIdenticalCardsIsPossible()) {
                if (selectedCards[0] == selectedCards[1] && selectedCards.length < 3) {
                    if (game.turnIsValid(cards.getSelectedValue())) {

                        game.putCardsToCentralStack(selCardsVec);
                    } else {
                        JOptionPane.showMessageDialog(null, "Diese Karte können Sie nicht legen. Wählen Sie eine \n" +
                                "andere aus oder ziehen Sie eine neue.", "Zug ist ungültig", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sie können nicht zwei verschiedene Karten legen.\n" +
                                    "Unter Umständen haben Sie auch mehr als zwei angewählt.\nDies ist nicht zulässig.",
                            "Zug ist ungültig", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                if (selectedCards.length > 1) {
                    JOptionPane.showMessageDialog(null, "Sie dürfen nur eine Karte anwählen", "Zu viele Karten angewählt", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (game.turnIsValid(cards.getSelectedValue())) {
                        game.putCardsToCentralStack(selCardsVec);
                    } else {
                        JOptionPane.showMessageDialog(null, "Diese Karte können Sie nicht legen. Wählen Sie eine \n" +
                                "andere aus oder ziehen Sie eine neue.", "Zug ist ungültig", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            cards.setModel(game.getPlayers().get(game.getCurrentPlayer()));
            centralCard.setIcon(ImageHandler.loadImage(game.getCentralCardStack().getTopCard().getFilename()));
            players.setSelectedIndex(game.getCurrentPlayer());
            setTitle("Tschau Sepp - " + game.getPlayers().get(game.getCurrentPlayer()).getName() + " ist am Zug");
        }
    }

    class NewCardButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            game.getCardsFromNewStack(1);
        }
    }
}
