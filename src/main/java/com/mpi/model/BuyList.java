package com.mpi.model;

import com.mpi.cards.Card;

import java.text.NumberFormat;
import java.util.ArrayList;

public class BuyList {

    ArrayList<Purchase> purchaseList = new ArrayList<>();

    public void addPurchase(int quantity, Card card) {
        purchaseList.add(new Purchase(quantity, card));
    }

    public void addPurchase(Purchase purchase) {
        purchaseList.add(purchase);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        purchaseList.forEach(purchase -> builder.append(purchase).append("\n"));
        return builder.toString();
    }

    public String csvString(NumberFormat formatter) {
        StringBuilder builder = new StringBuilder();
        builder.append("#,Name,Set Symbol,Set Name,Non-Foil,Foil,Etched,Notes,Min,Max");
        purchaseList.forEach(purchase -> builder.append("\n").append(purchase.csvString(formatter)));
        return builder.toString();
    }
}
