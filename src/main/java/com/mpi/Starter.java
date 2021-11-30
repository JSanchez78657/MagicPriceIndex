package com.mpi;

import com.mpi.gui.GUI;
import com.mpi.model.GUILIstener;

public class Starter {
    public static void main(String[] args) {
        GUI gui = new GUI();
        GUILIstener search = new GUILIstener(gui);
        gui.display();
    }
}
