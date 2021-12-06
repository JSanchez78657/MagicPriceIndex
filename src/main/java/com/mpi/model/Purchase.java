package com.mpi.model;

import com.mpi.cards.Card;

import java.text.NumberFormat;

public class Purchase {

    private final int quantity;
    private final Card card;

    public Purchase(int quantity, Card card) {
        this.quantity = quantity;
        this.card = card;
    }

    @Override
    public String toString() {
        return quantity + " " + card.getName();
    }

    public String csvString(NumberFormat formatter) {
        return quantity + "," + card.csvString(formatter);
    }
}
