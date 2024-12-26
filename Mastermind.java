// Author: Puneet Bhat
// File Name: Main.java
// Project Name: PASS2_Mastermind
// Creation Date: Dec. 18, 2023
// Modified Date: Jan. 12, 2023
// Description: A program that simulates the popular game of the same name

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class Main 
{
  // Declaring the input scanner for user input
  static Scanner input = new Scanner(System.in);

  // Declaring the required red and green foreground colours
  public static final String RED_FG = "\u001B[31m";
  public static final String GREEN_FG = "\u001B[32m";

  // Declaring the string color that resets all colors back to white
  public static final String RESET_ALL_COLOURS = "\u001B[0m";

  public static void main(String[] args) 
  {
    // Declare the array that will later carry the values of the secret code
    int[] secretCode = {0,0,0,0};

    // Declare the variable to keep track of the number of elements
    int lengthCounter = 4;

    // Declare the variable that will represent the randomly entered integers in the array carrying the values of the digits of the secret code
    int randNumForSecretCode = -1;

    // Declare an empty string that will later be used for the user's guess
    String guess = "";

    // Declare an empty string for the output
    String output = "";

    // Declaring all four integers that will later represent the integer values of the digits of the guess to -1
    int firstIntegerInGuess = -1;
    int secondIntegerInGuess = -1;
    int thirdIntegerInGuess = -1;
    int fourthIntegerInGuess = -1;

    // Until the value of count (or in other words, the length of secretCode) is equal to zero, keep looping
    while (lengthCounter > 0) 
    {
      // Declaring the minimum value of any of the random unique values in the secret code to be one and the maximum value to be eight
      int minimumValue = 1;
      int maximumValue = 8;

      // Generating a random integer between one and eight for the secret code
      randNumForSecretCode = ThreadLocalRandom.current().nextInt(minimumValue, maximumValue + 1);

      // Declaring a variable representing whether or not the randomly generated integer is already present in the array containing the digits of the secret code
      int isPresent = IsPresentInArray(secretCode, randNumForSecretCode);

      // In order to ensure that all digits of the secret code are unique, make sure that the randomly generated integer is not already present in the array storing the digits of the secret code. If so, add it to the array carrying the digits of the secret code and decrement the counter
      if (isPresent == 0) 
      {
        secretCode[secretCode.length - lengthCounter] = randNumForSecretCode;
        --lengthCounter;
      }
    }

    // Clear the screen
    System.out.print("\033[H\033[2J");

    // Loop ten times for the guesses
    for (int guessCounter = 0; guessCounter < 10; ++guessCounter)
    {
      // Declare all elements of the array that will store all the user guess information and store all matches (both number and perfect) as zero
      int[] userGuessInfo = {0,0,0,0,0,0};
      int[] arrWithAllMatches = {0,0,0,0};

      // Declare the variables that will act as counters for number and perfect matches as zero
      int numberOfNumberMatches = 0;
      int numberOfPerfectMatches = 0;

      // If guessCounter is equal to zero, output all components of the header and also output the entire gameboard
      if (guessCounter == 0) 
      {
        OutputGameHeaderComponents();
        OutputEntireGameBoard();
      }

      // Prompt the user to enter in their guess
      System.out.print("Enter your guess: ");
      guess = input.nextLine();

      // Clear the screen
      System.out.print("\033[H\033[2J");

      // Output the header of the game once again
      OutputGameHeaderComponents();

      // Change the value of each of the following variables to the integer form of the proper digits of the secret code
      firstIntegerInGuess = Integer.parseInt(guess.substring(0, 1));
      secondIntegerInGuess = Integer.parseInt(guess.substring(1, 2));
      thirdIntegerInGuess = Integer.parseInt(guess.substring(2, 3));
      fourthIntegerInGuess = Integer.parseInt(guess.substring(3, 4));

      // Set the value of the first four indices of userGuessInfo to its corresponding integer
      userGuessInfo[0] = firstIntegerInGuess;
      userGuessInfo[1] = secondIntegerInGuess;
      userGuessInfo[2] = thirdIntegerInGuess;
      userGuessInfo[3] = fourthIntegerInGuess;

      // Loop through every element in secretCode
      for (int elem = 0; elem < secretCode.length; ++elem) 
      {
        // Declare a variable representing whether the elemth element of userGuessInfo is in the arr carrying all the matches and a variable representing whether the elemth element is in secretCode
        int isElementInArrWithAllMatches = IsPresentInArray(arrWithAllMatches, userGuessInfo[elem]);
        int isElementInSecretCode = IsPresentInArray(secretCode, userGuessInfo[elem]);

        // If the specified element of secretCode is equal to the element with the same indices in userGuessInfo and is also not present in the array carrying all matches, increment the number of perfect matches and make that certain indice of the array carrying all matches the element with the same indices in userGuessInfo
        if (secretCode[elem] == userGuessInfo[elem] && isElementInArrWithAllMatches == 0) 
        {
          ++numberOfPerfectMatches;
          arrWithAllMatches[elem] = userGuessInfo[elem];
        }

        // If the specified element of secretCode is not equal to the element with the same indices in userGuessInfo and is present in the array carrying the digits of the secret code but not present in the array carrying all the matches to that point, increment the variable representing the number of number matches and make that indices of the array carrying all matches equal to the element with the same indices in userGuessInfo
        if (secretCode[elem] != userGuessInfo[elem] && isElementInSecretCode == 1 && isElementInArrWithAllMatches == 0) 
        {
          ++numberOfNumberMatches;
          arrWithAllMatches[elem] = userGuessInfo[elem];
        }
      }

      // Set the values of the last two indices of userGuessInfo as the number of perfect and number matches
      userGuessInfo[4] = numberOfPerfectMatches;
      userGuessInfo[5] = numberOfNumberMatches;

      // Add all information about the user's guess to the output string in order to properly output the user guess information
      output += "   " +userGuessInfo[0] + "    " + userGuessInfo[1] + "    " + userGuessInfo[2] + "    " + userGuessInfo[3] + "   "  + "|     " + GREEN_FG + userGuessInfo[4] + RESET_ALL_COLOURS + "            " + RED_FG + userGuessInfo[5] + RESET_ALL_COLOURS + "\n----------------------+----------------------\n";

      // If the number of perfect matches doesn't equal four and it's the user's last guess, output the user guess information, thank the user for playing, and tell them the secret code. Or if the number of perfect matches doesn't equal four and it's not the user's last guess, output the user guess information and the rest of the board. Or if at any point the number of perfect matches equals four (signifying that the user guessed the code correctly), output the rest of the board (if possible) and congratulate the user for doing so
      if (numberOfPerfectMatches != 4 && guessCounter == 9) 
      {
        System.out.println(output);
        System.out.println("\nGreat job for trying!\nThe secret code was " + secretCode[0] + secretCode[1] + secretCode[2] + secretCode[3]);
      }
      else if (numberOfPerfectMatches != 4 && guessCounter < 9)
      {
        System.out.print(output);
        for (int count = 0; count < 9 - guessCounter; ++count) 
        {
          System.out.println("   " + 0 + "    " + 0 + "    " + 0 + "    " + 0 + "   " + "|     " + GREEN_FG + 0 + RESET_ALL_COLOURS + "            " + RED_FG + 0 + RESET_ALL_COLOURS + "\n----------------------+----------------------");
        }
      }
      else if (numberOfPerfectMatches == 4)
      {
        System.out.print(output);
        for (int count = 0; count < 9 - guessCounter; ++count) 
        {
          System.out.println("   " + 0 + "    " + 0 + "    " + 0 + "    " + 0 + "   " + "|     " + GREEN_FG + 0 + RESET_ALL_COLOURS + "            " + RED_FG + 0 + RESET_ALL_COLOURS + "\n----------------------+----------------------");
        }
        System.out.println("\nCongratulations, you have guessed the code correctly!");
        break;
      }
    }
  }

  //Pre: numbers is an array of any length, n is the value of any integer declared
  //Post: Return an integer value depicting whether n is present in the array
  //Desc: Given an integer n, find out whether the integer is in the array that's defined in the parameter
  private static int IsPresentInArray(int[] numbers, int n) 
  { 
    // Set the counter to zero
    int counter = 0;

    // Loop through every element in numbers
    for (int element = 0; element < numbers.length; ++element) 
    {
      // If an element of numbers equals n, increment the value of the counter
      if (numbers[element] == n) 
      {
        ++counter;
      }
    }
    return counter;
  }

  //Pre: None
  //Post: None
  //Desc: Output all components of the header of the game
  private static void OutputGameHeaderComponents() 
  {
    // Output all desired components of the header
    System.out.println("   ---Mastermind---\n\nTO NOTE: The objective of the game is to guess the secret unique four digit code. The digits of the code range from one to eight.\n\n                        PERFECTS      NUMBERS\n   ?    ?    ?    ?\n=============================================");
  }

  //Pre: None
  //Post: None
  //Desc: Output the entire Mastermind gameboard
  private static void OutputEntireGameBoard() 
  {
    // Loop ten times
    for (int count = 0; count < 10; ++count) 
    {
      // Output components of the gameboard
      System.out.println("   " + 0 + "    " + 0 + "    " + 0 + "    " + 0 + "   " + "|     " + GREEN_FG + 0 + RESET_ALL_COLOURS + "            " + RED_FG + 0 + RESET_ALL_COLOURS + "\n----------------------+----------------------");
    }
  }
}
