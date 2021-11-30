package com.mpi.model;

import com.mpi.cards.Card;

public class Purchase {

    private int quantity;
    private Card card;

    public Purchase(int quantity, Card card) {
        this.quantity = quantity;
        this.card = card;
    }
}
