package App;

import java.util.Scanner;

/**
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.01.24
 * @version 0.1.1
 */
public class UserReader {

    // attributes
    static Scanner sc;

    // constructor
    public UserReader() {
        sc = new Scanner(System.in);
    }

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