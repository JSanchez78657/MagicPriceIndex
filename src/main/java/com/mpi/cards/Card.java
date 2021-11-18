package com.mpi.cards;

import java.util.ArrayList;

public class Card {
    private String name;
    private ArrayList<Printing> printings = new ArrayList<>();

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", printings=" + printings +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Printing> getPrintings() {
        return printings;
    }

    public void setPrintings(ArrayList<Printing> printings) {
        this.printings = printings;
    }
}
