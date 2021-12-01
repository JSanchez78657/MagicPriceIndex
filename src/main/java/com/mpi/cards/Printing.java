package com.mpi.cards;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.*;

public class Printing {
    private String setName;
    private String setSymbol;
    private ArrayList<String> notes;
    private HashMap<String, Double> prices;

    private static final NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public Printing(String setName, String setSymbol) {
        this.setName = setName;
        this.setSymbol = setSymbol;
    }

    public Printing(JSONObject json) {
        this.setName = (String) json.get("set_name");
        this.setSymbol = ((String) json.get("set")).toUpperCase();
        this.notes = extractNotes(json);
        this.prices = extractPrices((JSONObject) json.get("prices"));
    }

    @Override
    public String toString() {
        return  "\n\t\tPrinting {\n" +
                "\t\t\tsetName='" + setName + "',\n" +
                "\t\t\tsetSymbol='" + setSymbol + "',\n" +
                "\t\t\tnotes=" + notes + ",\n" +
                "\t\t\tprice=" + prices + ",\n" +
                "\t\t}";
    }

    public String csvString(NumberFormat formatter) {
        return String.format("%s,%s,%s,%s,%s,%s", setSymbol,setName.replace(",", "\",\""),getPrice(formatter, ""), getPrice(formatter, "foil"), getPrice(formatter, "etched"),notes.toString().replace(',','/'));
    }

    public String getPrice(NumberFormat formatter, String type) {
        String currency = formatter.getCurrency().toString().toLowerCase();
        Double hold;
        switch(type) {
            case("foil") -> hold = prices.get(currency + "_foil");
            case("etched") -> hold = prices.get(currency + "_etched");
            default -> hold = prices.get(currency);
        }
        if(hold == null) hold = 0.0;
        return formatter.format(hold.doubleValue()).replace(",",".");
    }

    public double getDoublePrice(NumberFormat formatter, String type) {
        String currency = formatter.getCurrency().toString().toLowerCase();
        Double hold;
        switch(type) {
            case("foil") -> hold = prices.get(currency + "_foil");
            case("etched") -> hold = prices.get(currency + "_etched");
            default -> hold = prices.get(currency);
        }
        if(hold == null) hold = 0.0;
        return hold;
    }

    private static ArrayList<String> extractNotes(JSONObject json) {
        ArrayList<String> hold = new ArrayList<>();
        String setType = (String) json.get("set_type");
        if(!setType.equals("expansion"))
            hold.add(setType);
        if(json.has("frame_effects")) {
            json.getJSONArray("frame_effects").forEach(effect -> {
                if(!effect.equals("legendary"))
                    hold.add((String) effect);
            });
        }
        if((boolean) json.get("full_art"))
            hold.add("fullart");
        return hold;
    }

    public static ArrayList<Printing> extractPrintings(JSONObject json) {
        ArrayList<Printing> printings = new ArrayList<>();
        JSONArray data = json.getJSONArray("data");
        data.forEach(p -> printings.add(new Printing((JSONObject) p)));
        return printings;
    }

    private static HashMap<String, Double> extractPrices(JSONObject prices) {
        HashMap<String, Double> ret = new HashMap<>();
        Map<String, Object> hold = prices.toMap();
        hold.forEach((K,V) -> {
            if(V != null)
                ret.put(K,Double.parseDouble((String) V));
        });
        return ret;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetSymbol() {
        return setSymbol;
    }

    public void setSetSymbol(String setSymbol) {
        this.setSymbol = setSymbol;
    }
}
