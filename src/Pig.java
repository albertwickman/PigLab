import java.util.Random;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import static java.lang.System.*;

/*
 * The Pig game
 * See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
 *
 */
public class Pig {

    public static void main(String[] args) {
        new Pig().program();
    }


    // The only allowed instance variables (i.e. declared outside any method)
    // Accessible from any method
    final Scanner sc = new Scanner(in);
    final Random rand = new Random();


    void program() {
        //test();                 // <-------------- Uncomment to run tests!

        final int winPts = 100;    // Points to win (decrease if testing)
        Player[] players;         // The players (array of Player objects)
        Player current;            // Current player for round (must use)
        boolean aborted = false;   // Game aborted by player?


        welcomeMsg(winPts);
        players = getPlayers();
        statusMsg(players);
        int nextPlayer = rand.nextInt(players.length);
        current = players[nextPlayer];


        while (!aborted) {

            String choice = getPlayerChoice(current);

            if (choice.equals("r")) {
                int result = 1;
                if (result > 1) {
                    current.roundPts = getRoundsTotal(current, result);
                    roundMsg(result, current);
                    if (checkWin(current, winPts)) {
                        current.totalPts = getPointsTotal(current);
                        break;
                    }
                }

                else {
                        current.roundPts = 0;
                        current = next(nextPlayer, players);
                        nextPlayer++;
                        roundMsg(result, current);
                        statusMsg(players);
                    }
            }

                else if (choice.equals("n")) {
                    current.totalPts = getPointsTotal(current);
                    current.roundPts = 0;
                    nextPlayer++;
                    current = next(nextPlayer, players);
                    statusMsg(players);
                }

                else if (choice.equals("q")) {
                    aborted = true;
                }
        }

        gameOverMsg(current, aborted);
    }

    // ---- Game logic methods --------------

    boolean checkWin(Player current, int winPts) {
        return (getPointsTotal(current) >= winPts);
    }

    Player next(int nextPlayer, Player[] players) {
        return players[nextPlayer % players.length];
    }

    int rollDice() {
        return rand.nextInt(6) + 1;
    }

    int getPointsTotal(Player current) {
        return current.totalPts + current.roundPts;
    }

    int getRoundsTotal(Player current, int result) {
        return current.roundPts + result;
    }

    // ---- IO methods ------------------

    void welcomeMsg(int winPoints) { // Welcome message
        out.println("Welcome to PIG!");
        out.println("First player to get " + winPoints + " points will win!");
        out.println("Commands are: r = roll , n = next, q = quit");
        out.println();
    }

    void statusMsg(Player[] players) { // Status message
        out.print("Points: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " = " + players[i].totalPts + " ");
        }
        out.println();
    }

    void roundMsg(int result, Player current) { // Round message
        if (result > 1) {
            out.println("Got " + result + " running total is " + current.roundPts);
        } else {
            out.println("Got 1 lost it all!");
        }
    }

    void gameOverMsg(Player player, boolean aborted) { // Game over message
        if (aborted) {
            out.println("The session has been aborted...");
        } else {
            out.println("Game over! Winner is player " + player.name + " with "
                    + (player.totalPts) + " points");
        }
    }

    String getPlayerChoice(Player player) { // Takes choice from player
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    Player[] getPlayers() { // Creates array of players
        out.println("How many players? > ");
        int numberOfPlayers = sc.nextInt();
        sc.nextLine();

        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < players.length; i++) {
            Player p = new Player();
            out.println("Name for player " + (i + 1) + " > ");
            p.name = sc.nextLine();
            players[i] = p;

            out.println("Name of player " + (i + 1) + " is: " + p.name);
            out.println("");
        }
        return players;
    }

    // ---------- Class -------------
    // Class representing the concept of a player
    // Use the class to create (instantiate) Player objects                     // Class representing players
    class Player {
        String name;     // Default null
        int totalPts;    // Total points for all rounds, default 0
        int roundPts;    // Points for a single round, default 0

    }

    // ----- Testing -----------------
    // Here you run your tests i.e. call your game logic methods
    // to see that they really work (IO methods not tested here)
    void test() {
        // This is hard coded test data
        // An array of (no name) Players (probably don't need any name to test)
        Player[] players = {new Player(), new Player(), new Player()};


        exit(0);   // End program
    }
}



/* Assignment 1 The Pig game

        We are going to implement the dice game "Pig".
        See http://en.wikipedia.org/wiki/Pig_%28dice_game%29
        A run of the program is shown below.

        If new to programming isn't assumed you have done week1 exercises 1-6 (to do IO with the Player class you may want to
        do week2 exercise 1)

        1.  Study the functional decomposition of the program in pig.png and the code in Pig until you've got
        a reasonable grasp of the "big picture". If confused by the sketch, try to make an own that fits better
        to your mind set (anything that may help).

        2.  Start implementing the "=r"-branch only i.e. the leftmost part of the second level in the diagram.
        If unsure of methods, do test them!

        3.  Add the over all if-structure over the "=r"-branch and implement the other branches.

        4.  Add a loop over the if-structure. That should finish the program (also see optional below).

        A run of the game:

        Welcome to PIG!
        First player to get 20 points will win!
        Commands are: r = roll , n = next, q = quit

        How many players? > 2
        Name for player 1 > olle
        Name for player 2 > fia

        Points: olle = 0 fia = 0
        Player is olle > r
        Got 6 running total are 6
        Player is olle > r
        Got 6 running total are 12
        Player is olle > r
        Got 3 running total are 15
        Player is olle > r
        Got 1 lost it all!
        Points: olle = 0 fia = 0
        Player is fia > r
        Got 2 running total are 2
        Player is fia > r
        Got 1 lost it all!
        Points: olle = 0 fia = 0
        Player is olle > r
        Got 2 running total are 2
        Player is olle > r
        Got 2 running total are 4
        Player is olle > r
        Got 1 lost it all!
        Points: olle = 0 fia = 0
        Player is fia > r
        Got 5 running total are 5
        Player is fia > r
        Got 3 running total are 8
        Player is fia > n
        Points: olle = 0 fia = 8
        Player is olle > r
        Got 6 running total are 6
        Player is olle > r
        Got 3 running total are 9
        Player is olle > n
        Points: olle = 9 fia = 8
        Player is fia > r
        Got 1 lost it all!
        Points: olle = 9 fia = 8
        Player is olle > r
        Got 1 lost it all!
        Points: olle = 9 fia = 8
        Player is fia > r
        Got 2 running total are 2
        Player is fia > r
        Got 5 running total are 7
        Player is fia > r
        Got 6 running total are 13
        Game over! Winner is player fia with 21 points (if q chosen prints: Game aborted)

        6. (Optional). Add a computer player (AI) to the program. A simple approach i just to name
        some player "computer". More complex solution would be to create a class for the Computer
        (possibly exchange a computer player with someone else's and let them play against each other).
        Make a copy of the existing program and continue from there.

        A run of program might look like:

        Welcome to PIG!
        First player to get 20 points will win!
        Commands are: r = roll , n = next, q = quit

        How many players? > 2
        Name for player 1 > olle
        Name for player 2 > fia
        Points: computer = 0 olle = 0 fia = 0
        Player is fia > r
        Got 3 running total are 3
        Player is fia > n
        Points: computer = 0 olle = 0 fia = 3
        Player is computer
        computer choose: r
        Got 5 running total are 5
        Player is computer
        computer choose: r
        Got 2 running total are 7
        Player is computer
        computer choose: n
        Points: computer = 7 olle = 0 fia = 3
        Player is olle > r
        Got 6 running total are 6
        Player is olle > n
        Points: computer = 7 olle = 6 fia = 3
        Player is fia > r
        Got 2 running total are 2
        etc...*/