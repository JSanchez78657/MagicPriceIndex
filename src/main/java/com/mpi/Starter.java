package com.mpi;

import com.mpi.gui.GUI;
import com.mpi.model.GUIListener;

public class Starter {
    public static void main(String[] args) {
        GUI gui = new GUI();
        GUIListener search = new GUIListener(gui);
        gui.display();
    }
}
