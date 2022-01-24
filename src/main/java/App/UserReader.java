package App;

import java.util.Scanner;

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
            System.out.print("J/N ?   >");
        }
        boolean bool;
        String input = sc.next();
        if (input.equalsIgnoreCase("j")) {
            bool = true;
        } else if (input.equalsIgnoreCase("n")) {
            bool = false;
        } else {
            System.out.println("Type in J or N!");
            System.out.print("   >");
            bool = readBooleanJN("");
        }
        sc.nextLine();
        return bool;
    }
}