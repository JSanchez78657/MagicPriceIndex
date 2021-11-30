package com.mpi.cards;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.*;

public class Printing {
    private String setName;
    private String setSymbol;
    private ArrayList<String> notes;
    private double price;
    private double priceFoil;

    private static final NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public Printing(String setName, String setSymbol) {
        this.setName = setName;
        this.setSymbol = setSymbol;
    }

    public Printing(JSONObject json) {
        JSONObject prices = (JSONObject) json.get("prices");
        this.setName = (String) json.get("set_name");
        this.setSymbol = ((String) json.get("set")).toUpperCase();
        this.notes = extractNotes(json);
        if(!prices.isNull("usd"))
            this.price = Double.parseDouble((String) prices.get("usd"));
        if(!prices.isNull("usd_foil"))
            this.priceFoil = Double.parseDouble((String) prices.get("usd_foil"));
    }



    @Override
    public String toString() {
        return  "\n\t\tPrinting {\n" +
                "\t\t\tsetName='" + setName + "',\n" +
                "\t\t\tsetSymbol='" + setSymbol + "',\n" +
                "\t\t\tnotes=" + notes + ",\n" +
                "\t\t\tprice=" + formatter.format(price) + ",\n" +
                "\t\t\tpriceFoil=" + formatter.format(priceFoil) + "\n" +
                "\t\t}";
    }

    public String csvString() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return String.format("%s,%s,%s,%s,%s", setSymbol,setName,f.format(price),f.format(priceFoil),notes.toString().replace(',','/'));
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceFoil() {
        return priceFoil;
    }

    public void setPriceFoil(double priceFoil) {
        this.priceFoil = priceFoil;
    }
}
