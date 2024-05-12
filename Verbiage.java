import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Vanity {
    // colors matched with IntelliJ IDEA Terminal's default theme
    public String tx_green = "\u001B[32m";
    public String tx_red = "\u001B[31m";
    public String tx_blue = "\u001B[34m";
    public String tx_magenta = "\u001B[35m";
    public String tx_cyan = "\u001B[36m";
    public String bg_black = "\u001B[40m";
    public String bg_white = "\u001B[47m";
    public String bg_yellow = "\u001B[43m";
    public String bg_red = "\u001B[41m";
    public String bg_magenta = "\u001B[45m";
    public String bg_blue = "\u001B[44m";
    public String bg_green = "\u001B[42m";
    public String bg_tx_reset = "\u001B[0m";

    public void screen_clear() {
        System.out.print("\033[H\033[2J");
    }

    public void dash() {
        for (int i = 0; i <= 104; ++i) {
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
    protected int id;

    public Bug() {
        bug = new TreeMap<>();
    }

    public boolean contains_key(int id) {
        return bug.containsKey(id);
    }

    public void bug_add(String platform, String version, String title, String description, String steps, String status) {
        Report report = new Report(platform, version, title, description, steps, status);
        bug.put(++id, report);
    }
    
    public void bug_status(Map.Entry<Integer, Report> level) {
        switch (level.getValue().status) {
            case "Pending Review":
            System.out.printf(ui.bg_black + " %-15s " + ui.bg_tx_reset, level.getValue().status);
                break;
            case "Low":
                System.out.printf(ui.bg_white + " %-15s " + ui.bg_tx_reset + ui.bg_tx_reset, level.getValue().status);
                break;
            case "Medium":
                System.out.printf(ui.bg_yellow + " %-15s " + ui.bg_tx_reset, level.getValue().status);
                break;
            case "High":
                System.out.printf(ui.bg_red + " %-15s " + ui.bg_tx_reset, level.getValue().status);
                break;
            case "Critical":
                System.out.printf(ui.bg_magenta + " %-15s " + ui.bg_tx_reset, level.getValue().status);
                break;
            case "Resolved":
                System.out.printf(ui.bg_green + " %-15s " + ui.bg_tx_reset, level.getValue().status);
                break;
            case "WAI":
                System.out.printf(ui.bg_blue + " %-15s " + ui.bg_tx_reset, level.getValue().status);
                break;
            default:
                break;
        }
    }

    public void bug_display() {
        ui.dash();
        System.out.printf("| %-3s | %-15s | %-12s | %-8s | %-51s |%n","ID", "Priority", "Platform", "Version", "Title");
        ui.dash();

        for (Map.Entry<Integer, Report> entry : bug.entrySet()) {
            System.out.printf("| %3s |", entry.getKey());

            bug_status(entry);

            System.out.printf("| %-12s | %-8s | %-51.50s |%n", entry.getValue().platform, entry.getValue().version, entry.getValue().title);
        }
        ui.dash();
    }

    void bug_details(int id) {
        ui.screen_clear();

        ui.dash();
        System.out.println("ID: " + bug.ceilingKey(id) + " | Title: " + ui.bg_white + bug.get(id).title + ui.bg_tx_reset);
        System.out.println(ui.tx_cyan + "Description: " + ui.bg_tx_reset + bug.get(id).description);
        System.out.println(ui.tx_cyan + "\nSteps to Recreate: \n" + ui.bg_tx_reset + bug.get(id).steps);
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

    public void bug_details_update(int id, Scanner vlt) {
        System.out.println("What is the new priority level of this report?");
        System.out.print(ui.tx_cyan + "1. Pending Review | 2. Low | 3. Medium | 4. High | 5. Critical | 6. Resolved | 7. Working As Intended\n-> " + ui.bg_tx_reset);
        int choice_update = vlt.nextInt();

        Report update = bug.get(id);

        switch (choice_update) {
            case 1:
                update.status = "Pending Review";
                break;
            case 2:
                update.status = "Low";
                break;
            case 3: 
                update.status = "Medium";
                break;
            case 4:
                update.status = "High";
                break;
            case 5:
                update.status = "Critical";
                break;
            case 6: 
                update.status = "Resolved";
                break;
            case 7: 
                update.status = "WAI";
                break;
            default:
                bug_details_update(id, vlt);
                break;
        }
        bug.put(id, update);

        System.out.println("Status successfully updated.");

        System.out.print(ui.tx_cyan + "Press any number to Manage Bugs -> " + ui.bg_tx_reset);
        vlt.nextInt();

        Verbiage.bug_manage();
    }

    public void bug_details_delete(int id, Scanner vlt) {
        System.out.println(ui.tx_red + "Deleting a report." + ui.bg_tx_reset + " Are you sure?");
        System.out.print(ui.tx_cyan + "1. Yes | 2. No (Back to List) -> " + ui.bg_tx_reset);
        int choice_ask = vlt.nextInt();

        switch (choice_ask) {
            case 1:
                bug.remove(id);
                System.out.println("Report successfully removed.");
                break;
            case 2:
                Verbiage.bug_manage();
                break;
            default:
                bug_details_delete(id, vlt);
                break;
        }

        Verbiage.bug_manage();
    }
}
public class Verbiage {
    public static Scanner vlt = new Scanner(System.in);
    public static Vanity ui = new Vanity();
    public static Bug report = new Bug();
    private static void welcome_message() {
        System.out.println("\nWelcome to " + ui.tx_cyan + "Verbiage" + ui.bg_tx_reset + "!");
    }
    private static void main_menu() {
        System.out.println(ui.tx_blue + "\n1. Report a Bug" + ui.tx_green + " | 2. See Existing Bugs" + ui.tx_magenta + " | 3. Manage Bugs (Login as a Developer)" + ui.bg_tx_reset + " | 4. Read Manual " + "| 5. Exit");

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
                bug_manage_login();
                break;
            case 4:
                bug_manual();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                main_menu();
                break;
        }
    }
    private static void bug_report() {
        ui.screen_clear();

        System.out.println("You want to report a bug. Is that correct?\n" + ui.tx_green + "1. Yes (Proceed) | " + ui.tx_magenta + "2. No (Back to Menu)" + ui.bg_tx_reset);

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

        System.out.print("1. What platform are you on?" + ui.tx_cyan + " (1. Android | 2. Windows | 3. iOS | 4. Mac)" + "\n-> " + ui.bg_tx_reset);
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
                bug_report_write();
                break;
        }

        System.out.print("2. What is the version of the application you are using?" + ui.tx_cyan + " (App version can be found on the settings of the app) \n-> " + ui.bg_tx_reset);
        String version = vlt.next();

        System.out.print(ui.tx_cyan + "3. What is the bug about?\n-> " + ui.bg_tx_reset);
        vlt.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        String title = vlt.nextLine();

        System.out.print(ui.tx_cyan + "4. What is the issue or the problem? \n-> " + ui.bg_tx_reset);
        String description = vlt.nextLine();

        System.out.print(ui.tx_cyan + "5. How can this bug be recreated? \n-> " + ui.bg_tx_reset);
        String steps = vlt.nextLine();

        bug_report_review(platform, version, title, description, steps);
    }
    private static void bug_report_review(String platform, String version, String title, String description, String steps) {
        ui.screen_clear();

        System.out.println("Are these information correct?\n");

        System.out.println(ui.tx_cyan + "Platform: " + ui.bg_tx_reset + platform);
        System.out.println(ui.tx_cyan + "Version: " + ui.bg_tx_reset + version);
        System.out.println(ui.tx_cyan + "Title: " + ui.bg_tx_reset + title);
        System.out.println(ui.tx_cyan + "Description: " + ui.bg_tx_reset + description);
        System.out.println(ui.tx_cyan + "Steps: " + ui.bg_tx_reset + steps);

        String status = "Pending Review";

        System.out.print(ui.tx_cyan + "\n1. Yes (Submit) | 2. No (Disregard the report)" + ui.bg_tx_reset + " -> ");
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
        System.out.println(ui.tx_green + "\nReport successfully submitted!" + ui.bg_tx_reset);
    }
    private static void bug_report_confirm(String platform, String version, String title, String description, String steps, String status) {
        System.out.print("\nAre you sure?" + ui.tx_cyan + "\n1. Yes (Publish) | 2. No (Re-write the report) -> " + ui.bg_tx_reset);
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
        System.out.print(ui.tx_cyan + "\nPress 1 to access Menu | 2 to Bug Lists\n-> " + ui.bg_tx_reset);
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

        System.out.print(ui.tx_cyan + "\n1. Back to Menu | 2. See Report \n-> " + ui.bg_tx_reset);
        int choice_list = vlt.nextInt();

        switch (choice_list) {
            case 1:
                ui.screen_clear();
                main_menu();
                break;
            case 2:
                bug_report_details();
                break;
            default:
                bug_list();
                break;
        }
    }
    private static int bug_details_display() {
        int id;
        do {
            System.out.print("\nEnter the ID to see details -> ");
            id = vlt.nextInt();
            if (report.contains_key(id)) {
                report.bug_details(id);
            } else {
                System.out.println(ui.tx_magenta + "Please enter a valid ID." + ui.bg_tx_reset);
            }
        } while (!report.contains_key(id));

        return id;
    }
    private static void bug_report_details() {
        bug_details_display();

        System.out.print(ui.tx_cyan + "\n\n1. Back to Menu | 2. Back to List -> " + ui.bg_tx_reset);
        int choice_details = vlt.nextInt();

        switch (choice_details) {
            case 1:
                main_menu();
                break;
            case 2:
                bug_list();
                break;
            default:
                bug_report_details();
                break;
        }

    }
    private static void bug_manage_login() {
        ui.screen_clear();

        System.out.println("To manage bugs, you must login.");
        System.out.println(ui.tx_cyan + "Enter 0 to return to menu.\n" + ui.bg_tx_reset);

        System.out.print("Username: ");
        vlt.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        String username = vlt.nextLine();
        System.out.print("Password: ");
        String password = vlt.next();

        if (username.equals("Vault Air") && password.equals("0000")) {
            bug_manage();
        } else if (username.equals("0")) {
            main_menu();
        } else {
            bug_manage_login();
        }
    }
    protected static void bug_manage() {
        ui.screen_clear();

        System.out.println(ui.tx_green + "Welcome! " + ui.bg_tx_reset + "Here are all of the reported bugs as of the moment.");

        report.bug_display();

        System.out.print(ui.tx_cyan + "\n1. See Report & Manage Report | 2. Logout \n-> " + ui.bg_tx_reset);
        int choice_manage = vlt.nextInt();

        switch (choice_manage) {
            case 1:
                bug_manage_details(0);
                break;
            case 2:
                main_menu();
                break;
            default:
                bug_manage();
                break;
        }
    }
    protected static void bug_manage_details(int id) {
        id = bug_details_display();

        System.out.print(ui.tx_cyan + "\n1. Update Status | 2. Delete Report | 3. Return to List\n-> " + ui.bg_tx_reset);
        int choice = vlt.nextInt();

        switch (choice) {
            case 1:
                report.bug_details_update(id, vlt);
                break;
            case 2:
                report.bug_details_delete(id, vlt);
                break;
            case 3:
                bug_manage();
                break;
            default:
                bug_manage_details(id);
                break;
        }
    }
    private static void bug_manual() {
        ui.screen_clear();

        System.out.println(ui.tx_cyan + "Verbiage Manual\n" + ui.bg_tx_reset);

        System.out.println(ui.tx_cyan + "Verbiage" + ui.bg_tx_reset + ", an issue tracking system for developers.\n");

        System.out.println("This system simply asks the users about the issue they encountered when using a particular application. The developers of the app can then track the issues encountered by their users easily.\n");

        System.out.println("How it works: " + ui.tx_magenta + "Reporter POV" + ui.bg_tx_reset);

        System.out.println("Upon welcome screen, select 1 to write a report, or 2 to see the list of bugs. \nUpon selecting 1, the user will be asked a series of questions. Answer accordingly. \nAfterwards, a confirmation message will be prompted whether to publish the report or disregard.\n");

        System.out.println("How it works: " + ui.tx_blue + "Developer POV" + ui.bg_tx_reset);

        System.out.println("The Developer POV is similar to the Reporter POV, but with elevated permissions.\nUpon selecting 3, the user will be prompted with a password login before being permitted system access.\n");

        report.bug_display();

        System.out.println("\nSpecific operations may be done by the developer.");

        System.out.println(ui.tx_cyan + "Update status, Read a report, Delete a report" + ui.bg_tx_reset);

        System.out.println("\nAbove operations will be based on the ID of the report.");

        System.out.print(ui.tx_cyan + "Proceed to Menu? (1. Yes) \n-> " + ui.bg_tx_reset);
        int choice_manual = vlt.nextInt();

        if (choice_manual == 1) {
            ui.screen_clear();
            main_menu();
        } else {
            bug_manual();
        }
    }
    private static void roach() {
        ui.screen_clear();
        System.out.println("                     ░░");
        System.out.println("                       ░░                                    ░░");
        System.out.println("                         ░░                                  ░░");
        System.out.println("                           ░░                              ░░");
        System.out.println("                             ░░                ░░        ▒▒");
        System.out.println("                                               ░░      ▒▒");
        System.out.println("                                       ░░      ▒▒░░░░░░▒▒        ▓▓▒▒");
        System.out.println("                                         ░░▓▓▓▓░░██▓▓▓▓▓▓▓▓▒▒░░░░▒▒  ▒▒░░");
        System.out.println("                                         ▓▓▓▓▓▓▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▒    ░░▓▓░░");
        System.out.println("                     ░░░░░░░░░░░░░░░░░    ████▒▒▓▓▓▓▓▓▓▓▓▓▒▒▓▓▓▓▓▓▓▓▒▒░░    ░░▓▓");
        System.out.println("                                         ▒▒▓▓▓▓██▒▒▓▓▒▒▒▒▒▒▒▒▓▓▓▓▒▒▓▓▓▓▒▒      ░░");
        System.out.println("                                           ░░▓▓▓▓▓▓██▓▓▓▓▒▒▒▒▓▓▒▒▒▒▓▓▓▓▒▒▒▒▒▒");
        System.out.println("                                           ░░▒▒▒▒▓▓▓▓██▓▓▓▓▓▓▓▓▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒▒");
        System.out.println("                                           ▒▒  ░░▒▒▒▒▒▒▓▓██▓▓▓▓▓▓▒▒▓▓▓▓▓▓▓▓▒▒▒▒░░");
        System.out.println("                                         ░░      ▒▒░░░░░░▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▒░░");
        System.out.println("                                         ░░      ░░▒▒    ░░▓▓▒▒▒▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒░░");
        System.out.println("                                                   ░░░░  ░░▓▓░░  ░░░░░░▒▒░░░░░░");
        System.out.println("                                                     ░░    ▓▓▓▓▒▒░░");
        System.out.println("                                                         ░░      ░░░░▒▒▓▓");
        System.out.println("                                                                             ░░");
    }
    public static void main (String[] args) {
        // Draft Data
        report.bug_add("Android", "v0.1a", "Duplicate Reports Upon Submission", "When submitting reports, the submission gets duplicated when it should not.", "1. File a report. 2. Submit the report. 3. See the duplicated report.", "Resolved");
        report.bug_add("Mac", "v0.2a", "Cannot Login with Proper Credentials", "I am locked out of my account.", "Try to login, nothing will happen", "High");
        report.bug_add("iOS", "v0.2a", "Crash Upon Startup", "Opening the program exits instantly.", "Open the application", "Critical");
        report.bug_add("Windows", "v0.2a", "Table is one pixel off", "The table is lacking one space in the See Reports tab.", "Start the app and see the reports.", "Low");
        report.bug_add("Android", "v0.3a", "User inputs have no validation", "There are cases when a number is required, a user can input a text and the program will accept it.", "Open a part of the program then try putting a character instead of a number.", "Medium");
        report.bug_add("Windows", "v0.3a", "Working as Intended Test", "TESTING WaI", "none", "WAI");
        report.bug_add("Android", "v0.3a", "Invalid user inputs crashes the program", "The program ends unexpectedly when a wrong input is entered.", "Open a part of the program then put a character when a number is asked.", "Pending Review");                                                                            
        roach();
        welcome_message();
        main_menu();
    }
}