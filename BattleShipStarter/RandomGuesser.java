public class RandomGuesser{
    public static String makeGuess(char[][] guesses)
    {
        int row, col;
        do{
            row = (int)(Math.random() * 10);
            col = (int)(Math.random() * 10);
        }while(guesses[row][col] != '.');
        
        char a = (char)((int)'A' + row);

        String guess = a + Integer.toString(col+1);
        return guess;

    }
}