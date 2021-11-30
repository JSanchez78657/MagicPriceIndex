package com.mpi.model;

import com.mpi.cards.Card;

import java.util.ArrayList;

public class BuyList {

    ArrayList<Purchase> list = new ArrayList<Purchase>();

    public void addPurchase(int quantity, Card card) {
        list.add(new Purchase(quantity, card));
    }

    public void addPurchase(Purchase purchase) {
        list.add(purchase);
    }
}
