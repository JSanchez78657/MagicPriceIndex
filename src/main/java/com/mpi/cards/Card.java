package com.mpi.cards;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    private String name;
    private ArrayList<Printing> printings;

    public Card(JSONObject json) {
        this.name = extractName(json);
        this.printings = new ArrayList<>(Printing.extractPrintings(json));
    }

    private static String extractName(JSONObject json) {
        return (String) ((JSONObject) (json.getJSONArray("data")).get(0)).get("name");
    }

    @Override
    public String toString() {
        return  "Card {" + "\n" +
                "\tname='" + name + "'\n" +
                "\tprintings=" + printings +
                "\n}";
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
