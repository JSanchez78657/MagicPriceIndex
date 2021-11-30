package com.mpi.cards;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Card {
    private String name;
    private ArrayList<Printing> printings;

    public Card(JSONObject json) {
        this.name = extractName(json);
        this.printings = new ArrayList<>(Printing.extractPrintings(json));
    }

    public Card(String name, Printing printing) {
        this.name = name;
        this.printings = new ArrayList<>();
        this.printings.add(printing);
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

    public String csvString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Printing> iterator = printings.iterator();
        Printing hold;
        double minPrice = Double.MAX_VALUE, maxPrice = 0;
        double curMin, curMax;
        builder.append(name).append(',');
        while(iterator.hasNext()) {
            hold = iterator.next();
            curMin = Double.min(hold.getPrice(), hold.getPriceFoil());
            curMax = Double.max(hold.getPrice(), hold.getPriceFoil());
            //Cards with no listed price default to 0.
            if(curMin > 0) minPrice = Double.min(minPrice, curMin);
            maxPrice = Double.max(maxPrice, curMax);
            builder.append(hold.csvString());
            if(iterator.hasNext())
                builder.append("\n,,");
        }
        //If there are no price listings, this makes sure the price isn't DoubleMax.
        if(minPrice == Double.MAX_VALUE) minPrice = 0;
        builder.append(",").append(minPrice).append(",").append(maxPrice);
        return builder.toString();
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
