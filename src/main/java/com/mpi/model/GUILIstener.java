package com.mpi.model;

import com.mpi.cards.Card;
import com.mpi.controller.RequestController;
import com.mpi.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Locale;

public class GUILIstener implements ActionListener {

    GUI gui;
    RequestController controller;

    public GUILIstener(GUI gui) {
        this.gui = gui;
        this.controller = new RequestController();
        gui.addSearchButtonListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String list = gui.getText();
        BuyList buyList = controller.getBuyList(list);
        gui.showSheet(buyList);
    }
}
