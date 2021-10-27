package view;

import model.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlayerRenderer implements ListCellRenderer<Player> {

    public PlayerRenderer() {

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index, boolean isSelected, boolean cellHasFocus) {

        JPanel listElement, textContainer;
        JLabel playerName, playerInfo, image;
        Color selectionbgColour;

        listElement = new JPanel(new BorderLayout());
        textContainer = new JPanel(new BorderLayout());
        selectionbgColour = new Color(19, 120, 216);
        image = new JLabel(ImageHandler.loadImage("/img/" + value.getIconFileName()));

        playerName = new JLabel(value.getName());
        playerName.setFont(playerName.getFont().deriveFont(Font.BOLD, 13.0f));

        playerInfo = new JLabel(value.getPoints() + " Pkt. | " + value.getAmountOfCards() + " Karten");
        playerInfo.setFont(playerInfo.getFont().deriveFont(12.0f));

        if (isSelected) {
            listElement.setBackground(selectionbgColour);
            listElement.setForeground(list.getSelectionForeground());
            textContainer.setBackground(selectionbgColour);
            textContainer.setForeground(list.getSelectionForeground());
            playerInfo.setForeground(Color.WHITE);
            playerName.setForeground(Color.WHITE);
        } else {
            listElement.setBackground(list.getBackground());
            listElement.setForeground(list.getForeground());
            textContainer.setBackground(list.getBackground());
            textContainer.setForeground(list.getForeground());
            playerInfo.setForeground(Color.BLACK);
            playerName.setForeground(Color.BLACK);
        }

        textContainer.add(playerName, BorderLayout.NORTH);
        textContainer.add(playerInfo, BorderLayout.SOUTH);
        textContainer.setBorder(new EmptyBorder(0, 7, 0, 0));

        listElement.add(image, BorderLayout.WEST);
        listElement.add(textContainer, BorderLayout.CENTER);
        listElement.setBorder(new EmptyBorder(4, 4, 5, 2));

        return listElement;
    }
}
