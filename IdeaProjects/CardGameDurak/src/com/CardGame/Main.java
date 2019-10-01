package com.CardGame;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to my performance of famous card game Durak.\n" +
                "There is light version of it, you can play with the computer AKA Opponent.\n" +
                "All your and its actions output in the console and viewed as a string with the ranks and suits of playing cards.\n" +
                "Players can beat cards, take cards to them hands and toss cards.\n" +
                "This is my first project in programming, I tried to do this well, so the code could be poor subject to complicated features.\n" +
                "I hope you enjoy this code and interface while I develop my own skills!\n");
        System.out.println("Are you ready? Let's the game begin!\n");
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");

        Deck deck = new Deck();
        deck.initialize();
        deck.shuffle();

        deck.spreadToMe();
        deck.spreadToOpponent();

        deck.chooseTrumps();
        deck.moveOrder();

        while (deck.getGame()) {
            while (deck.getMarker() == 1) {
                while (deck.getMyMove()) {
                    deck.myMove();
                    deck.beatCardsArray();
                }
                deck.opponentResponse();
                while (deck.getToss()) {
                    deck.tossCardsArray();
                    deck.tossingCards();
                }
            }
            while (deck.getMarker() == 0) {
                while (deck.getOpponentMove()) {
                    deck.opponentMove();
                }
                deck.myResponse();
                while (deck.getToss()) {
                    deck.tossOpponentCardsArray();
                    deck.tossingOpponentCards();
                }
            }
        }
    } // main
} // Main
