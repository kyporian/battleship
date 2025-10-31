/**
* Kyprian Knopp
* 300250510 
* COSC 111 002
* October 31, 2025
* 
* 
*
*
* 
**/


import java.util.Scanner;

public class Battleship {

    static Scanner input = new Scanner(System.in); //Scanner gets user input
    static String[] boatPositions = createBoatLocations(); //Array containing the positions of the boats in form of indexes. Ex: "52"
    static String[] boatPositionsHit = new String[9]; //Keeps track if a boatPosition has already been hit
    static String[][] playingBoard = createBoard(6); //Multi-Dimensional array to visually represent the game

    public static void main(String[] args) {

        //Initialize Game Pieces

        //Patrol Boat
        String patrolBoat = "Patrol Boat";
        int patrolHealth = 2;
        //Destroyer
        String destroyer = "Destroyer";
        int destroyerHealth = 3;
        //Battleship
        String battleship = "Battleship";
        int battleshipHealth = 4;
        //Misc Variables
        int totalHealth = 9; //Keeps track of boat health, -1 each time a boat has been hit
        int boatAtIndex; //boatAtIndex of boatPositions represent boat type
        boolean gameOver = false;
        
        
        System.out.println(); //Formatting println

        while (!gameOver) { //Loops until totalHealth is 0
            formattedPrintBoard(playingBoard);
            boatAtIndex = takeTurn(); //takeTurn() returns which boat has been hit if any
            

            if(boatAtIndex >= 0) { //Skips if taketurn() returns -1
                if(boatAtIndex < 2) { //Patrol boat indexes
                    patrolHealth = boatHit(patrolHealth, patrolBoat);
                    totalHealth --;
                } else if (boatAtIndex < 5) { //Destroyer indexes
                    destroyerHealth = boatHit(destroyerHealth, destroyer);
                    totalHealth --;
                } else if (boatAtIndex < 9) { //Battleship indexes
                    battleshipHealth = boatHit(battleshipHealth, battleship);
                    totalHealth --;
                }
            }
            
            

            if (totalHealth == 0) { //Game ends if all boats get destroyed
                formattedPrintBoard(playingBoard);
                System.out.println("Congrats! You win!");
                gameOver = true; 
            }
        }
    }

    //Create Board
    public static String[][] createBoard(int length) {
        String[][] board = new String[length][length];
        for (String[] row : board) {
            for (int i = 0; i < board.length; i++) {
                row[i] = "_";
            }
        }
        return board;
    }

    //Create Boat Location Array
    public static String[] createBoatLocations() {
        String[] boatLocations = new String[9];
        int arrayIndex = 0; //Indexes of boatLocations represents boat type. 
        
        for(int i = 0; i < 2; i++) { //Initialize patrol boat hit positions with 00, 10
            boatLocations[arrayIndex] = i + "0";
            arrayIndex ++; //Index 0-1 = Patrol Boat
        }

        for(int i = 1; i < 4; i++) { //Initialize destroyer hit positions with 14, 24, 34
            boatLocations[arrayIndex] = i + "4";
            arrayIndex ++; //Index 2-4 = Destroyer
        }
        for(int i = 2; i < 6; i++) { //Initialize battleship hit positions with 52, 53, 54, 55
            boatLocations[arrayIndex] = "5" + i;
            arrayIndex ++; //Index 5-8 = Battleship
        }
        return boatLocations;
    }

    //Take Turn
    public static int takeTurn() {
        System.out.print("Please enter a guess in the form 'B5': "); //Prompt
        String guess = input.nextLine();
        System.out.println();


        String r = guess.toUpperCase().substring(0,1); //Seperates the letter from number
        String c = guess.substring(1,2);

        int rowNum = r.charAt(0) - 'A'; //Converts substrings to int, then subtracts 1 to represent indexes
        int colNum = Integer.parseInt(c) - 1;

        String row = String.valueOf(rowNum); //Coverts back into string
        String col = String.valueOf(colNum);

        guess = row + col; //Concatenated position
        
        int boatIndex = hitDetection(guess); //Fix for using hitDetection(guess) multiple times after adding repeat hit detection
        if (boatIndex >= 0) { //Checks if guess is a hit or miss
            playingBoard[rowNum][colNum] = "X";
            System.out.println(r + c + " is a hit");
            return boatIndex;
        } else if(hitDetection(guess) == -2) { //If boat has been hit, provides feedback and returns no boat hit
            System.out.println(r + c + " has already been hit!");
            return -1;
        } else {
            playingBoard[rowNum][colNum] = "O";
            System.out.println(r + c + " is a miss");
            return -1;
        }
    }

    //Is hit?
    public static int hitDetection(String guess) {
        for (int i = 0; i < boatPositions.length; i++) { //Iterates over boatPositions and checks if the guess string matches AND guess has not already been guessed
            if(boatPositions[i].equals(guess) && boatPositionsHit[i] == null) { //Case where hit has NOT already been guessed
                boatPositionsHit[i] = ""; //Replaces null with empty string
                return i;
            }
            if(boatPositions[i].equals(guess)) { //Case where hit has already been guessed
                return -2;
            }
        }
        return -1;
    }

    //Boat Health Modifier
    public static int boatHit(int boatHealth, String boat) {
        boatHealth --;
        if(boatHealth == 0) { //Feedback if boat sunk
            System.out.println("You sunk the " + boat);
        }
        return boatHealth;
    }

    //Formatted Print Board
    public static void formattedPrintBoard(String[][] board) {
        System.out.println("    1   2   3   4   5   6"); //Numbered columns
        System.out.println("   ___ ___ ___ ___ ___ ___"); //Grid top
        char let = 'A'; //Letter for rows
        for (String[] row : board) {
            System.out.print(let++ + " "); //Changes to next letter each row
            for (String square : row) {
                System.out.print("|_" + square + "_"); //Each individual box
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(); //Extra println for formatting
    }
}
