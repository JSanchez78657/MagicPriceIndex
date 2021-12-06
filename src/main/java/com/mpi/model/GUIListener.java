package com.mpi.model;

import com.mpi.controller.RequestController;
import com.mpi.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIListener implements ActionListener {

    GUI gui;
    RequestController controller;

    public GUIListener(GUI gui) {
        this.gui = gui;
        this.controller = new RequestController();
        gui.addSearchButtonListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String list = gui.getText();
        BuyList buyList = controller.getBuyList(list);
        controller.saveFileDialogue(buyList, gui.getCurrency());
    }
}
