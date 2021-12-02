package com.mpi.gui;

import com.mpi.model.BuyList;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

public class GUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel radioButtonPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton searchButton;
    private ButtonGroup currencyGroup;
    private JRadioButton usdRadioButton;
    private JRadioButton euroRadioButton;

    public GUI() {
        this.frame = new JFrame("MagicPriceIndex");
        this.mainPanel = new JPanel();
        this.buttonPanel = new JPanel();
        this.radioButtonPanel = new JPanel();
        this.textArea  = new JTextArea();
        this.scrollPane = new JScrollPane(textArea);
        this.searchButton = new JButton("Search");
        this.usdRadioButton = new JRadioButton("USD");
        this.euroRadioButton = new JRadioButton("Euro");
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
