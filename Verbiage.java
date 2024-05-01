import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Vanity {
    public String text_reset = "\u001B[0m";
    public String text_green = "\u001B[32m";
    public String text_blue = "\u001B[34m";
    public String text_magenta = "\u001B[35m";
    public String text_cyan = "\u001B[36m";
    public String text_black = "\u001B[30m";
    public String background_black = "\u001B[40m";
    public String background_white = "\u001B[47m";
    public String background_yellow = "\u001B[43m";
    public String background_red = "\u001B[41m";
    public String background_magenta = "\u001B[45m";
    public String background_green = "\u001B[42m";
    public String background_reset = "\u001B[0m";

    public void screen_clear() {
        System.out.print("\033[H\033[2J");
    }

    public void dash() {
        for (int i = 0; i <= 95; ++i) {
            System.out.print("-");
        }
        System.out.println();
    }
} 
class Report {
    protected String platform;
    protected String version;
    protected String title;
    protected String description;
    protected String steps;
    protected String status;

    public Report(String platform, String version, String title, String description, String steps, String status) {
        this.platform = platform;
        this.version = version;
        this.title = title;
        this.description = description;
        this.steps = steps;
        this.status = status;
    }
}
class Bug {
    protected TreeMap<Integer, Report> bug;

    public Bug() {
        bug = new TreeMap<>();
    }

    public void bug_add(String platform, String version, String title, String description, String steps, String status) {
        Report report = new Report(platform, version, title, description, steps, status);
        bug.put(bug.size() + 1, report);
    }

    public void bug_display() {
        Vanity ui = new Vanity();
        
        ui.dash();
        System.out.printf("| %-5s | %-15s | %-12s | %-51s |%n","ID", "Priority", "Platform", "Title");
        ui.dash();

        for (Map.Entry<Integer, Report> entry : bug.entrySet()) {

            // System.out.printf("| %-5s | %-15s | %-12s | %-51s |%n", entry.getKey(), entry.getValue().status, entry.getValue().platform, entry.getValue().title);

            System.out.printf("| %-5s | %-15s | %-12s | %-51s |%n", entry.getKey(), 
            ((entry.getValue().status.equals("Pending Review")) ? ui.background_black + ui.text_black + "Pending Review" + ui.background_reset + ui.text_reset : 
            (entry.getValue().status.equals("Low")) ? ui.background_white + ui.text_black + "Low" + ui.background_reset + ui.text_reset : 
            (entry.getValue().status.equals("Medium")) ? ui.background_yellow + ui.text_black + "Medium" + ui.background_reset + ui.text_reset : 
            (entry.getValue().status.equals("High")) ? ui.background_red + ui.text_black + "High" + ui.background_reset + ui.text_reset :
            (entry.getValue().status.equals("Critical")) ? ui.background_magenta + ui.text_black + "Critical" + ui.background_reset + ui.text_reset :
            (entry.getValue().status.equals("Resolved")) ? ui.background_green + "Resolved" + ui.background_reset : "null"), entry.getValue().platform, entry.getValue().title);

            // System.out.printf("| %-5s ", entry.getKey());

            // switch (entry.getValue().status) {
            //     case "Pending Review":
            //     System.out.printf("|" + ui.background_black + ui.text_black + " %-15s " + ui.background_reset + ui.text_reset, entry.getValue().status);
            //         break;
            //     case "Low":
            //         System.out.printf("|" + ui.background_black + ui.text_black + " %-15s " + ui.background_reset + ui.text_reset, entry.getValue().status);
            //             break;
            //     default:
            //         break;
            // }

            // System.out.printf("| %-25s ", ((entry.getValue().status.equals("Pending Review")) ? ui.background_black + ui.text_black + entry.getValue().status + ui.background_reset + ui.text_reset : 
            // (entry.getValue().status.equals("Low")) ? ui.background_white + ui.text_black + entry.getValue().status + ui.background_reset + ui.text_reset : 
            // (entry.getValue().status.equals("Medium")) ? ui.background_yellow + ui.text_black + entry.getValue().status + ui.background_reset + ui.text_reset : 
            // (entry.getValue().status.equals("High")) ? ui.background_red + ui.text_black + entry.getValue().status + ui.background_reset + ui.text_reset :
            // (entry.getValue().status.equals("Critical")) ? ui.background_magenta + ui.text_black + entry.getValue().status + ui.background_reset + ui.text_reset :
            // (entry.getValue().status.equals("Resolved")) ? ui.background_green + entry.getValue().status + ui.background_reset : "null"));
            // System.out.printf("| %-12s ", entry.getValue().platform);
            // System.out.printf("| %-51s |%n", entry.getValue().title);
        }
    }
}
public class Verbiage {
    public static Scanner vlt = new Scanner(System.in);
    public static Vanity ui = new Vanity();
    public static Bug report = new Bug();
    private static void welcome_message() {
        ui.screen_clear();

        System.out.print("Welcome to " + ui.text_cyan + "Verbiage" + ui.text_reset + "!\n");
    }
    private static void main_menu() {
        System.out.println(ui.text_blue + "\n1. Report a Bug" + ui.text_green + " | 2. See Existing Bugs" + ui.text_magenta + " | 3. Manage Bugs (Login as a Developer)" + ui.text_reset + " | 4. Read Manual");

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

        System.out.print("1. What platform are you on?" + ui.text_black + " (1. Android | 2. Windows | 3. iOS | 4. Mac)" + "\n-> " + ui.text_reset);
        int choice_write = vlt.nextInt();

        String platform = "null";
        switch (choice_write) {
            case 1:
                platform = "Android";
                break;
            case 2:
                platform = "Windows";
                break;
            case 3:
                platform = "iOS";
                break;
            case 4:
                platform = "Mac";
                break;
            default:
                bug_report_write();;
                break;
        }

        System.out.print("2. What is the version of the application you are using?" + ui.text_black + " (App version can be found on the settings of the app) \n-> " + ui.text_reset);
        String version = vlt.next();

        System.out.print(ui.text_black + "3. What is the bug about?\n-> " + ui.text_reset);
        vlt.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        String title = vlt.nextLine();

        System.out.print(ui.text_black + "4. What is the issue or the problem? \n-> " + ui.text_reset);
        String description = vlt.nextLine();

        System.out.print(ui.text_black + "5. How can this bug be recreated? \n-> " + ui.text_reset);
        String steps = vlt.nextLine();

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

        String status = "Pending Review";

        System.out.print(ui.text_black + "\n1. Yes (Submit) | 2. No (Disregard the report)" + ui.text_reset + " -> ");
        int choice_review = vlt.nextInt();
        switch (choice_review) {
            case 1:
                bug_report_confirm(platform, version, title, description, steps, status);
                break;
            case 2:
                bug_report();
                break;
            default:
                bug_report_review(platform, version, title, description, steps);
                break;
        }
    }
    private static void bug_report_confirm(String platform, String version, String title, String description, String steps, String status) {
        report.bug_add(platform, version, title, description, steps, status);

        System.out.println(ui.text_green + "\nReport successfully published." + ui.text_reset);

        System.out.print(ui.text_black + "\nPress 1 to return to Menu | 2 to Bug Lists\n-> " + ui.text_reset);
        int choice_confirm = vlt.nextInt();
        switch (choice_confirm) {
            case 1:
                main_menu();
                break;
            default:
                bug_report_confirm(platform, version, title, description, steps, status);
                break;
        }
    }
    private static void bug_list() {
        ui.screen_clear();

        report.bug_display();

        System.out.print(ui.text_black + "\n1. See Report | 2. Back to Menu \n-> " + ui.text_reset);
        int choice_list = vlt.nextInt();

        switch (choice_list) {
            case 1:
                bug_view();
                break;
            case 2:
                ui.screen_clear();
                main_menu();
                break;
            default:
                bug_view();
                break;
        }
    }
    private static void bug_view() {
        //specific bug ID info is here
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

        System.out.println("\nAbove operations will be based on the ID of the report.");

        System.out.print(ui.text_black + "Proceed to Menu? (1. Yes) \n-> " + ui.text_reset);
        int choice_manual = vlt.nextInt();

        switch (choice_manual) {
            case 1:
                ui.screen_clear();
                main_menu();
                break;
            default:
                bug_manual();
                break;
        }
    }
    public static void main (String[] args) {

        report.bug_add("Android", "1", "Title 1", "1", "1", "Pending Review");
        report.bug_add(null, null, null, null, null, "Low");
        report.bug_add(null, null, null, null, null, "Medium");
        report.bug_add(null, null, null, null, null, "High");
        report.bug_add(null, null, null, null, null, "Critical");
        report.bug_add(null, null, null, null, null, "Resolved");
        
        welcome_message();
        main_menu();
    }
}