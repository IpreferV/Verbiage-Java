import java.util.Scanner;

class Vanity {
    public String text_reset = "\u001B[0m";
    public String text_green = "\u001B[32m";
    public String text_blue = "\u001B[34m";
    public String text_yellow = "\u001B[35m";
    public String text_cyan = "\u001B[36m";
    public String screen_clear = "\033[H\033[2J";
}
public class Verbiage {
    public static Scanner vlt = new Scanner(System.in);
    public static Vanity vanity = new Vanity();
    private static void welcome_message() {
        System.out.print(vanity.screen_clear);

        System.out.println("Welcome to " + vanity.text_cyan + "Verbiage" + vanity.text_reset + "!");
    }
    private static void main_menu() {
        System.out.println(vanity.text_blue + "\n1. Report a Bug" + vanity.text_green + "\n2. See Existing Bugs" + vanity.text_yellow + "\n3. Manage Bugs" + vanity.text_reset);

        System.out.print("Select an Operation: \n-> ");
        int choice = vlt.nextInt();

        if (choice == 1) {

        }
        else if (choice == 2) {

        }
        else if (choice == 3) {
            
        }
    }
    public static void main (String[] args) {
        welcome_message();
        main_menu();
    }
}