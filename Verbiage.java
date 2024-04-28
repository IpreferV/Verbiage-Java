import java.util.Scanner;

public class Verbiage {
    private static void welcome_message () {
        System.out.println("Welcome to " + "Verbiage");
    }
    private static void main_menu () {
        System.out.println("\n1. Report a Bug\n2. See Existing Bugs\n3. Manage Bugs");
    }
    public static void main (String[] args) {
        Scanner vlt = new Scanner(System.in);

        welcome_message();

        main_menu();

        vlt.close();
    }
}