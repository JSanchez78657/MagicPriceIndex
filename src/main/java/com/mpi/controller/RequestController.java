package com.mpi.controller;

import com.mpi.cards.Card;
import com.mpi.cards.CardService;
import com.mpi.model.BuyList;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestController {

    CardService service;

    public RequestController() {
        this.service = new CardService(new RestTemplate());
    }


    public ArrayList<Card> getCards(String list) {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        BuyList buyList = new BuyList();
        Arrays.stream(list.split("\n")).forEach(line -> {
            line = line.trim();
            if(!(line.equals("") || line.startsWith("/"))) {
                int quantity = 1;
                String cardName = line;
                //Check if the line begins with a quantity
                if(line.split(" ")[0].matches("[0-9]*")) {
                    if(line.contains("#!Commander"))
                        line = line.substring(0,line.indexOf("#!Commander") - 1);
                    quantity = Integer.parseInt(line.split(" ")[0]);
                    if (!(line.length() <= line.indexOf(' ') + 1))
                        cardName = line.substring(line.indexOf(' ') + 1);
                }
                names.add(cardName);
                quantities.add(quantity);
            }
        });
        names.forEach(name -> {
            long timestamp = System.currentTimeMillis();
            long elapsedTime = 0L;
            while(elapsedTime < 100) {
                elapsedTime = System.currentTimeMillis() - timestamp;
            }
            cards.add(service.getCard(name));
        });
        System.out.println(cards);
        return cards;
    }
}
