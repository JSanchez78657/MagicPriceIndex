package com.mpi.controller;

import com.mpi.cards.Card;
import com.mpi.cards.CardService;
import com.mpi.cards.Printing;
import com.mpi.model.BuyList;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestController {

    CardService service;

    public RequestController() {
        this.service = new CardService(new RestTemplate());
    }


    public BuyList getBuyList(String list) {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        BuyList buyList = new BuyList();
        Arrays.stream(list.split("\n")).forEach(line -> {
            line = line.trim();
            //Makes sure that blank lines and commented lines aren't read.
            if(!(line.equals("") || line.startsWith("/"))) {
                int quantity = 1;
                String cardName = line;
                //Check if the line begins with a quantity. If it doesn't, quantity is defaulted to 1.
                if(line.split(" ")[0].matches("[0-9]*")) {
                    //Deckstats.net uses this notation to designate your commander.
                    //It would mess up the request, so I remove it.
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
            Card card;
            //Scryfall requests a 75-100 ms delay between requests, I'm just playing it safe at 100.
            while(elapsedTime < 100) {
                elapsedTime = System.currentTimeMillis() - timestamp;
            }
            //Resolve 404s
            try {
                card = service.getCard(name);
                cards.add(card);
            } catch(HttpClientErrorException e) {
                System.out.println("Error, card not found.");
                cards.add(new Card("CardNotFound", new Printing("ContactCreator", "CNF")));
            }
        });
        for(int i = 0; i < cards.size(); ++i)
            buyList.addPurchase(quantities.get(i), cards.get(i));
        return buyList;
    }
}
