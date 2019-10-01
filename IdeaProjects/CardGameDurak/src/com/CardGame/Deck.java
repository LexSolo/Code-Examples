package com.CardGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Deck {

    /**
     * Enum with the card ranks and some of actions/conctructors/methods.
     */

    private enum Rank {

        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(11),
        Queen(12),
        King(13),
        Ace(14);

        private int rankIndex;

        Rank(int rankIndex) {
            this.rankIndex = rankIndex;
        }

        public int getRankIndex() {
            return rankIndex;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Enum with the card suits.
     */

    private enum Suit {

        Hearts,
        Spades,
        Diamonds,
        Clubs
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Class for description of card and other actions with it.
     */

    private class Card {

        Rank rank;
        Suit suit;

        Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }

        public String getCard() {
            return rank + " of " + suit;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * All variables and arrays for further game declares here. Also some methods.
     */

    private final int cardInHand = 6; // value of cards through the move
    private int marker = 0; // flag of pointing my or opponent's move
    private int opponentSuit = 1; // flag of confirming trump's suit of opponent's card

    public int getMarker() {
        return marker;
    }

    private boolean game = true; // game is going on flag
    private boolean flag = true; // intermediate flag for checking different conditionals
    private boolean anotherFlag = true; // intermediate flag for checking different conditionals
    private boolean toss = true; // flag for tossing cards
    private boolean myMove = true; // flag for pointing move of mine
    private boolean opponentMove = true; // flag for pointing opponent's move

    public boolean getGame() {
        return game;
    }

    public boolean getToss() {
        return toss;
    }

    public boolean getMyMove() {
        return myMove;
    }

    public boolean getOpponentMove() {
        return opponentMove;
    }

    private Scanner scanner = new Scanner(System.in);

    private Suit trumps; // suit for trumps in game
    private Card card; // intermediate field for writing my card
    private Card opponentCard; // intermediate field for writing opponent's card
    private Card minOpponentTrumpCard; // minimal trump card in opponent's hand for beating my card presently
    private Card minBeatCard; // minimal card in opponent's hand for beating my card presently
    private Card minBeatTrumpCard; // minimal trump card in opponent's hand for moving of beating my card presently

    private ArrayList<Card> deck = new ArrayList<>(); // deck of cards in right order
    private ArrayList<Card> shuffledDeck = new ArrayList<>(); // shuffled deck of cards
    private ArrayList<Card> hand = new ArrayList<>(); // card in my hand presently
    private ArrayList<Card> opponentHand = new ArrayList<>(); // card in opponent hand presently

    private ArrayList<Card> checkingTrumps = new ArrayList<>(); // array for checking the values of trumps in my hand
    private ArrayList<Card> checkingOpponentTrumps = new ArrayList<>(); // array for checking the values of trumps in opponent hand
    private ArrayList<Card> beatCards = new ArrayList<>(); // array for beating my cards
    private ArrayList<Card> beatTrumpCards = new ArrayList<>(); // array for beating my trumps
    private ArrayList<Integer> trumpsIndexes = new ArrayList<>(); // array for comparing indexes of trumps with the ranks
    private ArrayList<Integer> trumpsOpponentIndexes = new ArrayList<>(); // array for comparing indexes of trumps with the ranks

    private ArrayList<Card> opponentTrumps = new ArrayList<>(); // array for description opponent's trump cards presently
    private ArrayList<Card> opponentCards = new ArrayList<>(); // array for description opponent's cards presently

    private ArrayList<Card> beaten = new ArrayList<>(); // array for intermediate beaten cards in the process of move
    private ArrayList<Card> finalBeaten = new ArrayList<>(); // array for beaten cards
    private ArrayList<Card> tossCards = new ArrayList<>(); // array for tossing cards to opponent
    private ArrayList<Card> tossOpponentCards = new ArrayList<>(); // array for tossing cards to me

    public String[] getTossCards() {
        String[] tossCardsToString = new String[tossCards.size()];
        for (int i = 0; i < tossCards.size(); i++) {
            tossCardsToString[i] = tossCards.get(i).getCard();
        }
        return tossCardsToString;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method creates deck of cards in correct order.
     */

    public void initialize() {

        int cardCounter = 1;

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(rank, suit);
                deck.add(card);
                if (cardCounter == 52) {
                    deck.add(card);
//                    System.out.println(card.getCard() + ".");
                    break;
                }
//                System.out.print(card.getCard() + ", ");
                cardCounter++;
            }
        }
    } // initialize

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method shuffles previous created deck of cards in random order.
     */

    public void shuffle() {

        while (deck.size() > 0) {
            int index = (int) (Math.random() * deck.size());
            shuffledDeck.add(deck.remove(index));
        }
    } // shuffle

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method spread cards to user's hand at start of the game and filling user's hand with the missing cards through the game then.
     */

    public void spreadToMe() {

        int cardCounter = 0;

        for (int i = hand.size(); i < cardInHand; i++) {
            if (i == cardInHand - 1) {
                hand.add(shuffledDeck.remove(cardCounter));
                break;
            }
            hand.add(shuffledDeck.remove(cardCounter));
            cardCounter += 2;
        }
    } // spreadToMe

    /*----------------------------------------------------------------------------------------------------------------*/

    public void printMyHand() {

        for (int i = 0; i < hand.size(); i++) {
            if (i == hand.size() - 1) {
                System.out.println(hand.get(i).getCard() + ".");
                break;
            }
            System.out.print(hand.get(i).getCard() + ", ");
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method spread cards to opponent's hand at start of the game and filling opponent's hand with the missing cards through the game then.
     */

    public void spreadToOpponent() {

        int cardCounter = 0;

        for (int i = opponentHand.size(); i < cardInHand; i++) {
            if (i == cardInHand - 1) {
                opponentHand.add(shuffledDeck.remove(cardCounter));
                break;
            }
            opponentHand.add(shuffledDeck.remove(cardCounter));
            cardCounter++;
        }
    } // spreadToOpponent

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method chooses suit of trumps for sourced game.
     */

    public void chooseTrumps() {
        int trump = (int) (Math.random() * shuffledDeck.size());
        Card trumpCard = shuffledDeck.get(trump);
        System.out.print("Trump card is " + trumpCard.getCard() + ". ");
        switch (trumpCard.suit) {
            case Hearts:
                trumps = Suit.Hearts;
                break;
            case Spades:
                trumps = Suit.Spades;
                break;
            case Diamonds:
                trumps = Suit.Diamonds;
                break;
            case Clubs:
                trumps = Suit.Clubs;
                break;
        }
        System.out.println("The trumps are " + trumps + " accordingly.");
        shuffledDeck.add(shuffledDeck.remove(trump)); // moving chosen card to the end of deck
    } // chooseTrumps

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method decides who moves the first by availability/absence of trumps and values of them.
     */

    public void moveOrder() {

        for (Card myCard : hand) {
            if (myCard.suit.equals(trumps)) {
                checkingTrumps.add(myCard);
            }
        }
        for (Card opponentCard : opponentHand) {
            if (opponentCard.suit.equals(trumps)) {
                checkingOpponentTrumps.add(opponentCard);
            }
        }

        if (checkingTrumps.size() == 0 && checkingOpponentTrumps.size() == 0) {
            System.out.println("Nobody has the trumps (!spoiler), so that Random will decide the first attacker.");
            int turn = (int) (Math.random() * 2);
            if (turn == 1) {
                System.out.println("First attacker: you.\n");
                marker = 1;
            } else {
                System.out.println("First attacker: opponent.\n");
            }
        } else if (checkingTrumps.size() == 0) {
            System.out.println("Your opponent goes the first.\n");
        } else if (checkingOpponentTrumps.size() == 0) {
            System.out.println("You go the first.\n");
            marker = 1;
        } else /*(checkingHand.size() >= 1 && checkingOpponentHand.size() >= 1)*/ {
            for (Card checkTrump : checkingTrumps) {
                for (Rank rank : Rank.values()) {
                    if (rank.equals(checkTrump.rank)) {
                        trumpsIndexes.add(rank.getRankIndex());
                    }
                }
            }
            for (Card checkOpponentTrump : checkingOpponentTrumps) {
                for (Rank rank : Rank.values()) {
                    if (rank.equals(checkOpponentTrump.rank)) {
                        trumpsOpponentIndexes.add(rank.getRankIndex());
                    }
                }
            }

            Collections.sort(trumpsIndexes);
            Collections.sort(trumpsOpponentIndexes);

            int minTrump = Collections.min(trumpsIndexes);
            int minOpponentTrump = Collections.min(trumpsOpponentIndexes);

            if (minTrump < minOpponentTrump) {
                System.out.println("Go the first.\n");
                marker = 1;
            } else {
                System.out.println("Your opponent goes the first.\n");
            }
        }
    } // moveOrder

    /*----------------------------------------------------------------------------------------------------------------*/
    /**
     * Interaction with user starts from here.
     * */
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of card choosing for user's move.
     */

    public void myMove() {

        flag = true;
        myMove = true;

        System.out.println("Your cards in hand:");
        if (hand.size() < cardInHand && shuffledDeck.size() != 0) {
            spreadToMe();
        }
        if (opponentHand.size() < cardInHand && shuffledDeck.size() != 0) {
            spreadToOpponent();
        }
        printMyHand();

        while (flag) {
            System.out.println("Choose the card (enter the card number):");
            int cardNumber = scanner.nextInt();

            if (cardNumber > 0 && cardNumber <= hand.size()) {
                cardNumber--;
                card = hand.get(cardNumber);
                System.out.println("You moved with " + card.getCard() + ".");
                beaten.add(card);
                hand.remove(card);
                flag = false;
            } else {
                System.out.println("Input error. Try again.");
            }
        }
    } // tossingCards

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of choosing suitable card for beating by filling the beatCards and beatTrumpCards arrays.
     */

    public void beatCardsArray() {

        beatCards.clear();
        beatTrumpCards.clear();

        if (card.suit.equals(trumps)) {
            for (Card opponent : opponentHand) {
                if (opponent.suit.equals(trumps) && opponent.rank.getRankIndex() > card.rank.getRankIndex()) {
                    beatTrumpCards.add(opponent);
                }
            }
        } else {
            for (Card opponent : opponentHand) {
                if (opponent.suit.equals(card.suit) && opponent.rank.getRankIndex() > card.rank.getRankIndex()
                        || opponent.suit.equals(trumps)) {
                    beatCards.add(opponent);
                }
            }
        }

        if (beatCards.size() != 0) {
            int minBeat = 15;
            int minBeatTrump = 15;
            minBeatCard = beatCards.get(0);

            for (Card opponent : beatCards) {
                opponentCard = opponent;
                if (opponentCard.suit.equals(trumps) && opponentCard.rank.getRankIndex() < minBeatTrump) {
                    minBeatTrumpCard = opponentCard;
                    minBeatTrump = opponent.rank.getRankIndex();
                } else if (opponentCard.rank.getRankIndex() < minBeat) {
                    minBeatCard = opponentCard;
                    minBeat = minBeatCard.rank.getRankIndex();
                }
            }
            myMove = false;
            opponentSuit = 1;
        } else if (beatTrumpCards.size() != 0) {
            int minBeatTrump = beatTrumpCards.get(0).rank.getRankIndex();
            minBeatTrumpCard = beatTrumpCards.get(0);

            for (Card opponent : beatTrumpCards) {
                opponentCard = opponent;
                if (opponentCard.rank.getRankIndex() < minBeatTrump) {
                    minBeatTrumpCard = opponentCard;
                    minBeatTrump = minOpponentTrumpCard.rank.getRankIndex();
                }
            }
            myMove = false;
            opponentSuit = 0;
        } else {
            if (shuffledDeck.size() != 0) {
                System.out.println("Your opponent took the cards.");
                opponentHand.addAll(beaten);
                beaten.clear();
                System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
                System.out.println("======================================================================================================");
                toss = false;
                myMove = true;
                anotherFlag = true;
            } else {
                System.out.println("Your opponent took the cards. Deck is empty.");
                opponentHand.addAll(beaten);
                beaten.clear();
                System.out.println("======================================================================================================");
                toss = false;
                myMove = true;
                anotherFlag = true;
                if (hand.size() == 0) {
                    System.out.println("Game over, you won!");
                    myMove = false;
                    opponentMove = false;
                    marker = 2;
                    game = false;
                }
            }
        }
    } // beatCardsArray

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of card choosing for opponent's response to user's move.
     */

    public void opponentResponse() {

        toss = true;

        if (card.suit.equals(trumps) && beatTrumpCards.size() != 0 || card.rank.getRankIndex() >= minBeatCard.rank.getRankIndex()) {
            opponentCard = minBeatTrumpCard;
            System.out.println("Your card has been beaten by " + opponentCard.getCard() + ".");
            beaten.add(opponentCard);
            opponentHand.remove(opponentCard);
        } else {

            if (card.suit.equals(minBeatCard.suit)) {
                opponentCard = minBeatCard;
                System.out.println("Your card has been beaten by " + opponentCard.getCard() + ".");
                beaten.add(opponentCard);
                opponentHand.remove(opponentCard);
                opponentSuit = 1;
            } else if (minBeatCard.suit.equals(trumps)) {
                opponentCard = minBeatTrumpCard;
                System.out.println("Your card has been beaten by " + opponentCard.getCard() + ".");
                beaten.add(opponentCard);
                opponentHand.remove(opponentCard);
                opponentSuit = 0;
            } else {
                System.out.println("Your opponent took the cards.");
                opponentHand.addAll(beaten);
                System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
                System.out.println("======================================================================================================");
                toss = false;
                myMove = true;
            }
        }
    } // opponentResponse

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of choosing suitable card for user for tossing by filling the tossCards array.
     */

    public void tossCardsArray() {

        toss = true;

        tossCards.clear();

        for (Card tossCard : hand) {
            for (Card beaten : beaten) {
                if (tossCard.rank.equals(beaten.rank) && Collections.frequency(tossCards, tossCard) == 0) {
                    tossCards.add(tossCard);
                }
            }
        }
        // I use frequency function for missing of adding the same card more than one time. For example, beaten array contains cards with ranks: 2, 3, 3, 7. Then we have two cards for tossing due to doubled "3" ranks.

    } // tossCardsArray

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes processing of tossCards array, wish of user to toss some cards and opponent's response.
     * */

    public void tossingCards() {

        flag = true;

        if (tossCards.size() != 0) {
            System.out.println("You have available cards for tossing. Do you want to toss one more card (1 - yes, 0 - no)?");
            int answer = scanner.nextInt();

            if (answer == 1) {
                while (flag) {
                    System.out.println("*if you have changed your mind about card tossing press 0*");
                    System.out.println("What card would you like to toss (enter the card number): " + Arrays.toString(getTossCards()));
                    int answer2 = scanner.nextInt();

                    if (answer2 > 0 && answer2 <= tossCards.size()) {
                        answer2--;
                        card = tossCards.get(answer2);
                        System.out.println("You tossed " + card.getCard() + ".");
                        hand.remove(card);
                        beaten.add(card);
                        flag = false;
                        anotherFlag = true;
                        beatCardsArray();
                        if (anotherFlag) {
                            opponentResponse();
                        }
                    } else if (answer2 == 0) {
                        System.out.println("Your move is done, your opponent moves.");
                        System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
                        System.out.println("======================================================================================================");
                        finalBeaten.addAll(beaten);
                        beaten.clear();
                        toss = false;
                        flag = false;
                        opponentMove = true;
                        marker = 0;
                    } else {
                        System.out.println("Input error. Try again.");
                    }
                }
            } else if (answer == 0) {
                if (shuffledDeck.size() != 0) {
                    System.out.println("Your move is done, your opponent moves.");
                    System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
                    System.out.println("======================================================================================================");
                    finalBeaten.addAll(beaten);
                    beaten.clear();
                    toss = false;
                    opponentMove = true;
                    marker = 0;
                } else {
                    System.out.println("Your move is done. Deck is empty.");
                    System.out.println("======================================================================================================");
                    finalBeaten.addAll(beaten);
                    beaten.clear();
                    toss = false;
                    marker = 0;
                    if (hand.size() == 0) {
                        System.out.println("Game over, you won!");
                        myMove = false;
                        opponentMove = false;
                        marker = 2;
                        game = false;
                    }
                }
            } else {
                System.out.println("Input error. Try again.");
                toss = false;
            }
        } else if (shuffledDeck.size() != 0) {
            System.out.println("You have no cards for tossing. Your opponent moves.");
            System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
            System.out.println("======================================================================================================");
            finalBeaten.addAll(beaten);
            beaten.clear();
            toss = false;
            opponentMove = true;
            marker = 0;
        } else {
            System.out.println("You have no for tossing. Deck is empty.");
            System.out.println("======================================================================================================");
            finalBeaten.addAll(beaten);
            beaten.clear();
            toss = false;
            opponentMove = true;
            marker = 0;
            if (hand.size() == 0) {
                System.out.println("Game over, you won!");
                myMove = false;
                opponentMove = false;
                marker = 2;
                game = false;
            }
        }
    } // tossingCards

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of choosing suitable card for opponent for moving by filling the opponentCards/opponentTrumps arrays.
     */

    public void opponentMove() {

        opponentMove = true;

        if (opponentHand.size() < cardInHand && shuffledDeck.size() != 0) {
            spreadToOpponent();
        }
        if (hand.size() < cardInHand && shuffledDeck.size() != 0) {
            spreadToMe();
        }

        opponentCards.clear();
        opponentTrumps.clear();

        for (Card opponent : opponentHand) {
            if (opponent.suit.equals(trumps)) {
                opponentTrumps.add(opponent);
            } else {
                opponentCards.add(opponent);
            }
        }

        if (opponentCards.size() != 0) {
            int minCard = opponentCards.get(0).rank.getRankIndex();
            Card minOpponentCard = opponentCards.get(0);

            for (Card opponent : opponentCards) {
                if (opponent.rank.getRankIndex() < minCard) {
                    minOpponentCard = opponent;
                    minCard = minOpponentCard.rank.getRankIndex();
                }
            }
            opponentCard = minOpponentCard;
            System.out.println("Opponent moved with " + minOpponentCard.getCard() + ".");
            opponentSuit = 1;
            beaten.add(minOpponentCard);
            opponentHand.remove(minOpponentCard);
            opponentMove = false;
        } else if (opponentTrumps.size() != 0) {
            int minOpponentTrump = opponentTrumps.get(0).rank.getRankIndex();
            minOpponentTrumpCard = opponentTrumps.get(0);

            for (Card opponent : opponentTrumps) {
                if (opponent.rank.getRankIndex() < minOpponentTrump) {
                    minOpponentTrumpCard = opponent;
                }
            }
            opponentCard = minOpponentTrumpCard;
            System.out.println("Opponent moved with " + minOpponentTrumpCard.getCard() + ".");
            opponentSuit = 0;
            beaten.add(minOpponentTrumpCard);
            opponentHand.remove(minOpponentTrumpCard);
            opponentMove = false;
        }
    } // opponentMove

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of card choosing for user's response to opponent's move.
     */

    public void myResponse() {

        flag = true;
        toss = true;

        while (flag) {
            System.out.println("Your cards in hand:");
            printMyHand();
            System.out.println("What card would you like to beat this card (enter the card number of press 0 to take cards)?");
            int answer = scanner.nextInt();

            if (answer > 0 && answer <= hand.size()) {
                answer--;
                card = hand.get(answer);

                if (opponentSuit == 1) {
                    if (card.suit.equals(opponentCard.suit) && card.rank.getRankIndex() > opponentCard.rank.getRankIndex()
                            || card.suit.equals(trumps)) {
                        System.out.println("You beat opponent's card with " + card.getCard() + ".");
                        beaten.add(card);
                        hand.remove(card);
                        flag = false;
                    } else {
                        System.out.println("Input error. Choose the correct card for beating.");
                    }
                } else {
                    if (card.suit.equals(trumps) && card.rank.getRankIndex() > opponentCard.rank.getRankIndex()) {
                        System.out.println("You beat opponent's card with " + card.getCard() + ".");
                        beaten.add(card);
                        hand.remove(card);
                        flag = false;
                    } else {
                        System.out.println("Input error. Choose the correct card for beating.");
                    }
                }
            } else if (answer == 0) {
                System.out.println("You took the cards. Your opponent moves.");
                hand.addAll(beaten);
                beaten.clear();
                System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
                System.out.println("======================================================================================================");
                flag = false;
                toss = false;
                opponentMove = true;
            } else {
                System.out.println("Input error. Try again.");
            }
        }
    } // myResponse

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes process of choosing suitable card for opponent for tossing by filling the tossOpponentCards array.
     */

    public void tossOpponentCardsArray() {

        tossOpponentCards.clear();

        if (opponentSuit == 1) {
            for (Card tossOpponentCard : opponentHand) {
                for (Card beaten : beaten) {
                    if (tossOpponentCard.rank.equals(beaten.rank)
                            && Collections.frequency(tossOpponentCards, tossOpponentCard) == 0) {
                        tossOpponentCards.add(tossOpponentCard);
                    }
                }
            }
        } else {
            for (Card tossOpponentCard : opponentHand) {
                for (Card beaten : beaten) {
                    if (tossOpponentCard.rank.equals(beaten.rank)
                            && Collections.frequency(tossOpponentCards, tossOpponentCard) == 0) {
                        tossOpponentCards.add(tossOpponentCard);
                    }
                }
            }
        }
    } // tossOpponentCardsArray

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * The method describes processing of tossOpponentCards array, wish of opponent to toss some cards and user's response.
     * */

    public void tossingOpponentCards() {

        toss = true;

        if (tossOpponentCards.size() != 0) {
            int minTossCard = tossOpponentCards.get(0).rank.getRankIndex();
            Card minOpponentToss = tossOpponentCards.get(0);

            for (Card opponent : tossOpponentCards) {
                if (opponent.rank.getRankIndex() <= minTossCard) {
                    minOpponentToss = opponent;
                }
            }
            opponentCard = minOpponentToss;
            System.out.println("Opponent tossed " + opponentCard.getCard() + ".");
            beaten.add(opponentCard);
            opponentHand.remove(opponentCard);
            toss = false;
        } else if (shuffledDeck.size() != 0) {
            System.out.println("Opponent has no cards for tossing. Your turn to move.");
            System.out.println("Deck has the " + shuffledDeck.size() + " cards.");
            System.out.println("======================================================================================================");
            finalBeaten.addAll(beaten);
            beaten.clear();
            toss = false;
            myMove = true;
            marker = 1;
        } else {
            System.out.println("Opponent has no cards for tossing. Deck is empty.");
            System.out.println("======================================================================================================");
            finalBeaten.addAll(beaten);
            beaten.clear();
            toss = false;
            myMove = true;
            marker = 1;
            if (opponentHand.size() == 0) {
                System.out.println("Game over, opponent won!");
                myMove = false;
                opponentMove = false;
                marker = 2;
                game = false;
            }
        }
    } // tossingOpponentCards
} // Deck

