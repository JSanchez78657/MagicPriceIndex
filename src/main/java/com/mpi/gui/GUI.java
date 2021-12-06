package com.mpi.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class GUI {

    private final JFrame frame;
    private final JTextArea textArea;
    private final JButton searchButton;
    private final ButtonGroup currencyGroup;

    public GUI() {
        this.frame = new JFrame("MagicPriceIndex");
        JPanel mainPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel radioButtonPanel = new JPanel();
        this.textArea  = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.searchButton = new JButton("Search");
        JRadioButton usdRadioButton = new JRadioButton("USD");
        JRadioButton euroRadioButton = new JRadioButton("Euro");
        this.currencyGroup = new ButtonGroup();

        textArea.setRows(20);
        textArea.setColumns(40);

        usdRadioButton.setSelected(true);

        radioButtonPanel.setLayout(new FlowLayout());
        radioButtonPanel.setBorder(new TitledBorder("Currency"));
        radioButtonPanel.add(usdRadioButton);
        radioButtonPanel.add(euroRadioButton);
        currencyGroup.add(usdRadioButton);
        currencyGroup.add(euroRadioButton);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(radioButtonPanel);
        buttonPanel.add(searchButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display() {
        frame.setVisible(true);
    }

    public String getCurrency() {
        Iterator<AbstractButton> i = currencyGroup.getElements().asIterator();
        AbstractButton hold;
        while(i.hasNext()) {
            hold = i.next();
            if (hold.isSelected())
                return hold.getText().toLowerCase();
        }
        return "err";
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getText() {
        return textArea.getText();
    }
}
