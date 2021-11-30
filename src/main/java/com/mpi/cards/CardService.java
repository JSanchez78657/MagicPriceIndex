package com.mpi.cards;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class CardService {

    private final RestTemplate template;

    public CardService(RestTemplate template) {
        this.template = template;
    }

    public Card getCard(String name) {
        JSONObject json = new JSONObject(
                template.getForObject(constructQuery(name),String.class)
        );
        return new Card(json);
    }

    private static String constructQuery(String name) {
        return "https://api.scryfall.com/cards/search?q=!\"" + name + "\" unique:prints not:digital";
    }
}
