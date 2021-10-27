package view;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CardRenderer implements ListCellRenderer<Card> {

    public CardRenderer() {

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Card> list, Card value, int index, boolean isSelected, boolean cellHasFocus) {


        JPanel listElement;
        JLabel image;
        Color selectionbgColour;

        listElement = new JPanel(new BorderLayout());
        selectionbgColour = new Color(19, 120, 216);
        image = new JLabel(ImageHandler.loadImage(value.getFilename()));


        if (isSelected) {
            listElement.setBorder(new LineBorder(selectionbgColour, 5));
            listElement.setBackground(selectionbgColour);
            listElement.setForeground(list.getForeground());
        } else {
            listElement.setBorder(new EmptyBorder(0,0,0,0));
            listElement.setBackground(list.getBackground());
            listElement.setForeground(list.getForeground());
        }

        listElement.add(image, BorderLayout.CENTER);
        listElement.setBorder(new EmptyBorder(0, 5, 10, 5));

        return listElement;
    }
}
