package App.Utils;

import java.util.Scanner;

/**
 * <h1>UserReader</h1>
 * <h3>Used for reading inputs from the command line.</h3>
 * It uses the java.util.Scanner to read a char input from the command line and evaluates it.
 *
 * @see java.util.Scanner
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.24
 * @version 0.1.1
 */
public class UserReader {

    /**
     * reference to the Scanner
     */
    static Scanner sc;

    /**
     * Constructor creating a new Scanner with param System.in
     */
    public UserReader() {
        sc = new Scanner(System.in);
    }

    /**
     * This method will ask the User a question wich can be answered with yes or no.<br>
     * The User will be asked as many times as needed until the User types in either 'y' (Yes) or 'n' (No).<br><br>
     *
     * @param text Information wich will be given to the User to know what about he needs to decide.
     * @return boolean value if either the user answered the question with yes or no.
     *
     * @apiNote
     * The case of the given input will be dismissed. So UpperChase and LowerChase will be accepted.
     */
    public boolean readBooleanJN(String text) {
        if (!text.equals("")) {
            System.out.println(text);
            System.out.print("Y/N ?   >");
        }
        boolean bool;
        String input = sc.next();
        if (input.equalsIgnoreCase("y")) {
            bool = true;
        } else if (input.equalsIgnoreCase("n")) {
            bool = false;
        } else {
            System.out.println("Type in Y or N!");
            System.out.print("   >");
            bool = readBooleanJN("");
        }
        sc.nextLine();
        return bool;
    }
}