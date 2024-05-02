import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

class Vanity {
    public String tx_reset = "\u001B[0m";
    public String tx_green = "\u001B[32m";
    public String tx_blue = "\u001B[34m";
    public String tx_magenta = "\u001B[35m";
    public String tx_cyan = "\u001B[36m";
    public String tx_black = "\u001B[30m";
    public String bg_black = "\u001B[40m";
    public String bg_white = "\u001B[47m";
    public String bg_yellow = "\u001B[43m";
    public String bg_red = "\u001B[41m";
    public String bg_magenta = "\u001B[45m";
    public String bg_green = "\u001B[42m";
    public String bg_reset = "\u001B[0m";

    public void screen_clear() {
        System.out.print("\033[H\033[2J");
    }

    public void dash() {
        for (int i = 0; i <= 93; ++i) {
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
    Vanity ui = new Vanity();
    protected TreeMap<Integer, Report> bug;

    public Bug() {
        bug = new TreeMap<>();
    }

    public boolean contains_key(int id) {
        return bug.containsKey(id);
    }

    public void bug_add(String platform, String version, String title, String description, String steps, String status) {
        Report report = new Report(platform, version, title, description, steps, status);
        bug.put(bug.size() + 1, report);
    }
    
    public void bug_status(Map.Entry<Integer, Report> level) {
        switch (level.getValue().status) {
            case "Pending Review":
            System.out.printf("|" + ui.bg_black + ui.tx_black + " %-15s " + ui.bg_reset + ui.tx_reset, level.getValue().status);
                break;
            case "Low":
                System.out.printf("|" + ui.bg_white + ui.tx_black + " %-15s " + ui.bg_reset + ui.bg_reset + ui.tx_reset, level.getValue().status);
                break;
            case "Medium":
                System.out.printf("|" + ui.bg_yellow + ui.tx_black + " %-15s " + ui.bg_reset + ui.tx_reset, level.getValue().status);
                break;
            case "High":
                System.out.printf("|"  + ui.bg_red + " %-15s " + ui.bg_reset, level.getValue().status);
                break;
            case "Critical":
                System.out.printf("|" + ui.bg_magenta + ui.tx_black + " %-15s " + ui.bg_reset + ui.tx_reset, level.getValue().status);
                break;
            case "Resolved":
                System.out.printf("|" + ui.bg_green + ui.tx_black + " %-15s " + ui.bg_reset + ui.tx_reset, level.getValue().status);
            default:
                break;
        }
    }

    public void bug_display() {
        ui.dash();
        System.out.printf("| %-3s | %-15s | %-12s | %-51s |%n","ID", "Priority", "Platform", "Title");
        ui.dash();

        for (Map.Entry<Integer, Report> entry : bug.entrySet()) {
            System.out.printf("| %3s ", entry.getKey());

            bug_status(entry);

            System.out.printf("| %-12s | %-51.50s |%n", entry.getValue().platform, entry.getValue().title);
        }
    }

    public void bug_details(int id) {
        ui.screen_clear();

        ui.dash();
        System.out.println("Title: " + bug.get(id).title);
        System.out.println("Description: " + bug.get(id).description);
        System.out.println("\nSteps to Recreate: \n" + bug.get(id).steps);
        ui.dash();
        System.out.println("Platform: " + bug.get(id).platform);
        System.out.println("Version: " + bug.get(id).version);
        System.out.print("Status: ");

        for (Map.Entry<Integer, Report> detail : bug.entrySet()) {
            if (detail.getKey().equals(id)) {
                bug_status(detail);
            }
        }
    }
}
public class Verbiage {
    public static Scanner vlt = new Scanner(System.in);
    public static Vanity ui = new Vanity();
    public static Bug report = new Bug();
    private static void welcome_message() {
        ui.screen_clear();

        System.out.print("Welcome to " + ui.tx_cyan + "Verbiage" + ui.tx_reset + "!\n");
    }
    private static void main_menu() {
        System.out.println(ui.tx_blue + "\n1. Report a Bug" + ui.tx_green + " | 2. See Existing Bugs" + ui.tx_magenta + " | 3. Manage Bugs (Login as a Developer)" + ui.tx_reset + " | 4. Read Manual");

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

        System.out.println("You want to report a bug. Is that correct?\n" + ui.tx_green + "1. Yes (Proceed) | " + ui.tx_magenta + "2. No (Back to Menu)" + ui.tx_reset);

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

        System.out.print("1. What platform are you on?" + ui.tx_black + " (1. Android | 2. Windows | 3. iOS | 4. Mac)" + "\n-> " + ui.tx_reset);
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

        System.out.print("2. What is the version of the application you are using?" + ui.tx_black + " (App version can be found on the settings of the app) \n-> " + ui.tx_reset);
        String version = vlt.next();

        System.out.print(ui.tx_black + "3. What is the bug about?\n-> " + ui.tx_reset);
        vlt.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        String title = vlt.nextLine();

        System.out.print(ui.tx_black + "4. What is the issue or the problem? \n-> " + ui.tx_reset);
        String description = vlt.nextLine();

        System.out.print(ui.tx_black + "5. How can this bug be recreated? \n-> " + ui.tx_reset);
        String steps = vlt.nextLine();

        bug_report_review(platform, version, title, description, steps);
    }
    private static void bug_report_review(String platform, String version, String title, String description, String steps) {
        ui.screen_clear();

        System.out.println("Are these information correct?\n");

        System.out.println(ui.tx_black + "Platform: " + ui.tx_reset + platform);
        System.out.println(ui.tx_black + "Version: " + ui.tx_reset + version);
        System.out.println(ui.tx_black + "Title: " + ui.tx_reset + title);
        System.out.println(ui.tx_black + "Description: " + ui.tx_reset + description);
        System.out.println(ui.tx_black + "Steps: " + ui.tx_reset + steps);

        String status = "Pending Review";

        System.out.print(ui.tx_black + "\n1. Yes (Submit) | 2. No (Disregard the report)" + ui.tx_reset + " -> ");
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
    private static void bug_report_submit(String platform, String version, String title, String description, String steps, String status) {
        report.bug_add(platform, version, title, description, steps, status);
        System.out.println(ui.tx_green + "\nReport successfully submitted!" + ui.tx_reset);
    }
    private static void bug_report_confirm(String platform, String version, String title, String description, String steps, String status) {
        System.out.print("\nAre you sure?" + ui.tx_black + "\n1. Yes (Publish) | 2. (No, re-write the report) -> " + ui.tx_reset);
        int choice_confirm = vlt.nextInt();

        switch (choice_confirm) {
            case 1:
                bug_report_submit(platform, version, title, description, steps, status);
                break;
            case 2:
                bug_report_write();
                break;
            default:
                bug_report_confirm(platform, version, title, description, steps, status);
                break;
        }

        bug_report_confirm_exit();
    }
    private  static void bug_report_confirm_exit() {
        System.out.print(ui.tx_black + "\nPress 1 to return to Menu | 2 to Bug Lists\n-> " + ui.tx_reset);
        int choice_confirm = vlt.nextInt();
        switch (choice_confirm) {
            case 1:
                main_menu();
                break;
            case 2:
                bug_list();
                break;
            default:
                bug_report_confirm_exit();
                break;
        }
    }
    private static void bug_list() {
        ui.screen_clear();

        report.bug_display();

        System.out.print(ui.tx_black + "\n1. See Report | 2. Back to Menu \n-> " + ui.tx_reset);
        int choice_list = vlt.nextInt();

        switch (choice_list) {
            case 1:
                bug_report_details();
                break;
            case 2:
                ui.screen_clear();
                main_menu();
                break;
            default:
                bug_list();
                break;
        }
    }
    private static void bug_report_details() {
        int id;
        do {
            System.out.print("\nEnter the ID to see details -> ");
            id = vlt.nextInt();
            if (report.contains_key(id)) {
                report.bug_details(id);
            } else {
                System.out.println(ui.tx_magenta + "Please enter a valid ID." + ui.tx_reset);
            }
        } while (!report.contains_key(id));

        System.out.println(ui.tx_black + "\n\nBack to List | Back to Menu" + ui.tx_reset);


    }
    private static void bug_manage() {
        ui.screen_clear();
    }
    private static void bug_manual() {
        ui.screen_clear();

        System.out.println(ui.tx_cyan + "Verbiage Manual\n" + ui.tx_reset);

        System.out.println(ui.tx_cyan + "Verbiage" + ui.tx_reset + ", an issue tracking system for developers.\n");

        System.out.println("This system simply asks the users about the issue they encountered when using a particular application. The developers of the app can then track the issues encountered by their users easily.\n");

        System.out.println("How it works: " + ui.tx_magenta + "Reporter POV" + ui.tx_reset);

        System.out.println("Upon welcome screen, select 1 to write a report, or 2 to see the list of bugs. \nUpon selecting 1, the user will be asked a series of questions. Answer accordingly. \nAfterwards, a confirmation message will be prompted whether to publish the report or disregard.\n");

        System.out.println("How it works: " + ui.tx_blue + "Developer POV" + ui.tx_reset);

        System.out.println("The Developer POV is similar to the Reporter POV, but with elevated permissions.\nUpon selecting 3, the user will be prompted with a password login before being permitted system access.\n");

        System.out.println("table form here");

        System.out.println("\nSpecific operations may be done by the developer.");

        System.out.println(ui.tx_black + "1. Update report status\n2. Read a report\n3. Delete a report" + ui.tx_reset);

        System.out.println("\nAbove operations will be based on the ID of the report.");

        System.out.print(ui.tx_black + "Proceed to Menu? (1. Yes) \n-> " + ui.tx_reset);
        int choice_manual = vlt.nextInt();

        if (choice_manual == 1) {
            ui.screen_clear();
            main_menu();
        } else {
            bug_manual();
        }
    }
    public static void main (String[] args) {

        report.bug_add("Android", "v1.0a", "Title 1", "Description 1", "1", "Pending Review");
        report.bug_add("Windows", "v1.1a", "Title 2", "Description 2", null, "Low");
        report.bug_add(null, null, null, null, null, "Medium");
        report.bug_add(null, null, null, null, null, "High");
        report.bug_add(null, null, null, null, null, "Critical");
        report.bug_add(null, null, null, null, null, "Resolved");
        
        welcome_message();
        main_menu();
    }
}