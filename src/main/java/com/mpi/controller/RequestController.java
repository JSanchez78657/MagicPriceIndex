package com.mpi.controller;

import com.mpi.cards.Card;
import com.mpi.cards.CardService;
import com.mpi.cards.Printing;
import com.mpi.model.BuyList;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.*;

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

        Iterator<String> nameIterator;
        Iterator<Exclusions> exclusionsIterator;
        boolean exclude = false;
        long timestamp;
        long elapsedTime;
        Card card;
        String name;

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
        nameIterator = names.iterator();
        while(nameIterator.hasNext()) {
            timestamp = System.currentTimeMillis();
            elapsedTime = 0L;
            name = nameIterator.next();
            //Certain cards have A LOT of printings and are in A LOT of decks, so they are excluded.
            exclusionsIterator = Arrays.stream(Exclusions.values()).iterator();
            while(exclusionsIterator.hasNext() && !exclude)
                exclude = exclusionsIterator.next().name().equals(name.toUpperCase());
            if(exclude) {
                exclude = false;
                continue;
            }
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
        }
        for(int i = 0; i < cards.size(); ++i)
            buyList.addPurchase(quantities.get(i), cards.get(i));
        return buyList;
    }

    public void saveFile(BuyList buyList, String currency) {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose file location");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                Path filePath = (fileToSave.getPath().endsWith(".csv")) ? fileToSave.toPath() : Paths.get(fileToSave.getPath() + ".csv");
                BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                writer.write(buyList.csvString(getFormat(currency)));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Failed to save");
            }
        }
    }

    public NumberFormat getFormat(String currency) {
        switch(currency) {
            case("usd") -> { return NumberFormat.getCurrencyInstance(Locale.US); }
            case ("euro") -> { return NumberFormat.getCurrencyInstance(Locale.GERMANY); }
        }
        return NumberFormat.getCurrencyInstance(Locale.US);
    }
}
