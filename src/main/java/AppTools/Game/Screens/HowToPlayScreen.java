package AppTools.Game.Screens;

import java.io.IOException;

public class HowToPlayScreen {
        public static void showHowToPlay() throws IOException {

                System.out.println(
                                "*********************************************** How to play? ***********************************************");
                System.out.println(
                                "The object of this game is to be the first player to get rid of all the cards in your hand.\n");
                System.out.println(
                                "The game requires 4 players to get the full experiance but don't worry, you can play with bots \nto fill out the remaining players needed :D.\n");
                System.out.println(
                                "The deck of cards will be shuffled and 7 random cards will be dealed for every player.\n");
                System.out.println(
                                "The remaining cards will be placed as a draw deck, and the top card will be placed to start a discard pile.\n");
                System.out.println(
                                "If the card is an action or wild card, it will be ignored and another card will be drawn \nuntil a non-action, non-wild card is on top of the discard pile.\n");
                System.out.println(
                                "When the game starts, a random player will be selected to play first, then the play initially proceeds clockwise.\n");
                System.out.println(
                                "On your turn, you may play one card from your hand to the discard pile only if it matches \none attribute of the top card on the discard pile, color, number or symbole\n");
                System.out.println(
                                "If you can't make a play, you have to draw the top card from the draw deck and add it to your hand.\n");

                System.out.println(
                                "************************************************************************************************************");
        }
}
