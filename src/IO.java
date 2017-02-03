import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IO {

    private static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static int getNumberFromUser(int upperBound) throws IOException {
        int number = 0;
        while (number == 0) {
            try {
                String line = bf.readLine();
                Scanner sc = new Scanner(line);
                number = sc.nextInt();
                if (number > upperBound) {
                    System.out.println("Please enter a number between 1 and "+upperBound);
                    number = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                number = 0;
            }
        }
        return number;
    }

    public static String readLine() throws IOException {
        return bf.readLine();
    }
}
