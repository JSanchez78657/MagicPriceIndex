package com.mpi.cards;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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

    public String csvString(NumberFormat formatter) {
        StringBuilder builder = new StringBuilder();
        Iterator<Printing> iterator = printings.iterator();
        Printing hold;
        builder.append("\"").append(name).append("\"").append(',');
        while(iterator.hasNext()) {
            hold = iterator.next();
            builder.append(hold.csvString(formatter));
            if(iterator.hasNext())
                builder.append("\n,,");
        }
        builder.append(",").append(getMinMaxPrice(formatter));
        return builder.toString();
    }

    private String getMinMaxPrice(NumberFormat formatter) {
        double normal, foil, etched, max = 0.0, min = Double.MAX_VALUE, curMin, curMax;
        List<Double> prices;
        for(Printing p : printings) {
            normal = p.getDoublePrice(formatter, "");
            foil = p.getDoublePrice( formatter, "foil");
            etched = p.getDoublePrice(formatter, "etched");

            prices = Arrays.asList(normal, foil, etched);

            curMin = prices.stream().min((o1, o2) -> {
                if(o1 == 0.0) return o2.intValue();
                if(o2 == 0.0) return o1.intValue();
                return o1.compareTo(o2);
            }).get();
            curMax = prices.stream().max(Double::compareTo).get();
            if(curMin > 0.0 && curMin < min) min = curMin;
            if(curMax > max) max = curMax;
        }
        if(min == Double.MAX_VALUE) min = 0.0;
        return formatter.format(min) + ',' + formatter.format(max);
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
