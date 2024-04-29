import java.util.Scanner;

class Vanity {
    public String text_reset = "\u001B[0m";
    public String text_green = "\u001B[32m";
    public String text_blue = "\u001B[34m";
    public String text_magenta = "\u001B[35m";
    public String text_cyan = "\u001B[36m";
    public String text_black = "\u001B[30m";

    public void screen_clear() {
        System.out.print("\033[H\033[2J");
    }
}
class Report {
    protected String platform;
    protected String version;
    protected String title;
    protected String description;
    protected String steps;
    protected String status;
}
public class Verbiage {
    public static Scanner vlt = new Scanner(System.in);
    public static Vanity ui = new Vanity();
    private static void welcome_message() {
        ui.screen_clear();

        System.out.print("Welcome to " + ui.text_cyan + "Verbiage" + ui.text_reset + "!\n");
    }
    private static void main_menu() {
        System.out.println(ui.text_blue + "\n1. Report a Bug" + ui.text_green + " | 2. See Existing Bugs" + ui.text_magenta + " | 3. Manage Bugs" + ui.text_reset + " | 4. Read Manual");

        System.out.print("Select an Operation: \n-> ");
        int choice_menu = vlt.nextInt();

        switch (choice_menu) {
            case 1:
                bug_report();
                break;
            case 2:
                bug_list();
                break;
            case 3:
                bug_manage();
                break;
            case 4:
                bug_manual();
                break;
            default:
                main_menu();
                break;
        }
    }
    private static void bug_report() {
        ui.screen_clear();

        System.out.println("You want to report a bug. Is that correct?\n" + ui.text_green + "1. Yes (Proceed) | " + ui.text_magenta + "2. No (Back to Menu)" + ui.text_reset);

        System.out.print("Select an Operation:\n-> ");
        int choice_report = vlt.nextInt();

        switch (choice_report) {
            case 1:
                bug_report_write();
                break;
            case 2:
                ui.screen_clear();
                main_menu();
                break;
            default:
                bug_report();
        }
    }
    private static void bug_report_write() {
        ui.screen_clear();

        System.out.print("What platform are you on?" + ui.text_black + " (1. Android | 2. Windows | 3. iOS | 4. Mac)" + "\n-> " + ui.text_reset);
        vlt.nextLine();
        int choice_write = vlt.nextInt();

        String platform;
        switch (choice_write) {
            case 1:
                platform = "Android";
                break;
            case 2:
                platform = "Windows";
                break;
            case 3:
                platform = "iOs";
                break;
            case 4:
                platform = "Mac";
                break;
            default:
                platform = "Select";
                break;
        }

        System.out.print(ui.text_black + "What is the version of the application you are using? \n-> " + ui.text_reset);
        String version = vlt.next();

        System.out.print(ui.text_black + "What is the issue or the problem? \n-> " + ui.text_reset);
        vlt.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        String description = vlt.nextLine();

        System.out.print(ui.text_black + "How can this bug be recreated? \n-> " + ui.text_reset);
        String steps = vlt.nextLine();

        System.out.print(ui.text_black + "How would you summarize this bug as its title? \n-> " + ui.text_reset);
        String title = vlt.nextLine();

        bug_report_review(platform, version, title, description, steps);
    }
    private static void bug_report_review(String platform, String version, String title, String description, String steps) {
        ui.screen_clear();

        System.out.println("Are these information correct?\n");

        System.out.println(ui.text_black + "Platform: " + ui.text_reset + platform);
        System.out.println(ui.text_black + "Version: " + ui.text_reset + version);
        System.out.println(ui.text_black + "Title: " + ui.text_reset + title);
        System.out.println(ui.text_black + "Description: " + ui.text_reset + description);
        System.out.println(ui.text_black + "Steps: " + ui.text_reset + steps);

        System.out.print(ui.text_black + "\n1. Yes (Submit) | 2. No (Disregard the report)" + ui.text_reset + " -> ");
        int choice_review = vlt.nextInt();
        switch (choice_review) {
            case 1:
                //NEW OBJECT FOR THE HASHMAP
                break;
            case 2:
                bug_report();
                break;
            default:
                bug_report_review(platform, version, title, description, steps);
                break;
        }
    }
    private static void bug_list() {
        ui.screen_clear();
    }
    private static void bug_manage() {
        ui.screen_clear();
    }
    private static void bug_manual() {
        ui.screen_clear();

        System.out.println(ui.text_cyan + "Verbiage Manual\n" + ui.text_reset);

        System.out.println(ui.text_cyan + "Verbiage" + ui.text_reset + ", an issue tracking system for developers.\n");

        System.out.println("This system simply asks the users about the issue they encountered when using a particular application. The developers of the app can then track the issues encountered by their users easily.\n");

        System.out.println("How it works: " + ui.text_magenta + "Reporter POV" + ui.text_reset);

        System.out.println("Upon welcome screen, select 1 to write a report, or 2 to see the list of bugs. \nUpon selecting 1, the user will be asked a series of questions. Answer accordingly. \nAfterwards, a confirmation message will be prompted whether to publish the report or disregard.\n");

        System.out.println("How it works: " + ui.text_blue + "Developer POV" + ui.text_reset);

        System.out.println("The Developer POV is similar to the Reporter POV, but with elevated permissions.\nUpon selecting 3, the user will be prompted with a password login before being permitted system access.\n");

        System.out.println("table form here");

        System.out.println("\nSpecific operations may be done by the developer.");

        System.out.println(ui.text_black + "1. Update report status\n2. Read a report\n3. Delete a report" + ui.text_reset);

        System.out.println();

        System.out.print(ui.text_black + "Proceed to Menu? (1. Yes) \n-> " + ui.text_reset);
        int choice_manual = vlt.nextInt();

        switch (choice_manual) {
            case 1:
                main_menu();
                break;
            default:
                bug_manual();
                break;
        }
    }
    public static void main (String[] args) {
        welcome_message();
        main_menu();

        //pending review, minor, critical, resolved
    }
}