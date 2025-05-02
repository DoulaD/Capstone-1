package com.pluralsight.financetracker;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.System.exit;

public class FinanceTracker {
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        boolean running = true;

        while (running) {


            System.out.println("Welcome to the finance tracker");
            showMenuOption();
            System.out.print("Enter a command: ");
            String userInput = scanner.nextLine().toUpperCase();


            switch (userInput) {
                case "D":
                    addDeposits();
                    break;
                case "P":
                    makePayments();
                    break;
                case "L":
                    viewLedger();
                    break;
                case "X":
                    exit();
                    System.out.println("Budget like a boss, See you soon");
                    running = false;
                    break;
                default:
                    System.out.println("Command is not recognized ...But your budget still is!");

            }

        }

    }


    private static void showMenuOption() {
        System.out.println("\nFinance tracker Menu");
        System.out.println("-----------------");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payments");
        System.out.println("L) View Ledger");
        System.out.println("X) Exit");


    }


    public static void addDeposits() {


        System.out.println("\nAdd Deposit");
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        LocalDateTime now = LocalDateTime.now();
        String date = now.toLocalDate().toString();
        String time = now.toLocalTime().withNano(0).toString();

        String entry = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);

        // Save to file using a FileHandler class (you can implement next)
        TransactionFileHandler.saveTransaction(entry);
        System.out.println("Deposit added and saved!");


        promptReturnToMenu();
    }


    public static void makePayments() {
        System.out.println("\nMake Payment");

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // Ensure payments are saved as negative values
        if (amount > 0) {
            amount *= -1;
        }

        LocalDateTime now = LocalDateTime.now();
        String date = now.toLocalDate().toString();
        String time = now.toLocalTime().withNano(0).toString();

        String entry = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);


        TransactionFileHandler.saveTransaction(entry);
        System.out.println("***** Payment recorded and saved! *******");

        promptReturnToMenu();


    }

    public static void viewLedger() {
        System.out.println("\nView Ledger Options");
        System.out.println("-----------------");
        System.out.println("A) Display All Entries");
        System.out.println("D) Deposits Only");
        System.out.println("P) Payments Only");
        System.out.println("R) Reports ");
        System.out.println("Choose an option: ");
        String option = scanner.nextLine().trim().toUpperCase();

        if (option.equals("R")) {
            runReportsMenu();
            return;

        }
        try {
            File file = new File("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv");
            if (!file.exists()) {
                System.out.println("No Transactions found yet.");
                return;
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv"));
            String line;
            List<String> transaction = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                transaction.add(line);
            }
            Collections.reverse(transaction);

            for (String entry : transaction) {
                String[] tokens = entry.split("\\|");
                if (tokens.length != 5) continue; // skip malformed lines

                String date = tokens[0];
                String time = tokens[1];
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);

                boolean show = switch (option) {
                    case "A" -> true;
                    case "D" -> amount > 0;
                    case "P" -> amount < 0;
                    default -> false;
                };

                if (show)
                    System.out.printf("%s %s | %-20s | %-15s | %10.2f\n", date, time, description, vendor, amount);

            }


            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in file.");
        }


        promptReturnToMenu();
    }

    private static void promptReturnToMenu() {
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    private static void exit() {
    }

    private static void runReportsMenu() {
        boolean inReportsMenu = true;

        while (inReportsMenu) {
            System.out.println("/\n======Reports Menu=======");
            System.out.println("1) Month to Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back ");
            System.out.println("H) Home ");

            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "1":
                    filterByMonthToDate();
                    break;
                case "2":
                    filterByPreviousMonth();
                    break;
                case "3":
                    filterByYearToDate();
                    break;
                case "4":
                    filterByPreviousYear();
                    break;
                case "5":
                    filterByVendor();
                    break;
                case "0":
                    inReportsMenu = false;
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }

    private static void filterByMonthToDate() {
        LocalDateTime now = LocalDateTime.now();
        String currentMonth = String.format("%d-%02d", now.getYear(), now.getMonthValue());
        File file = new File(TransactionFileHandler.FILE_PATH);
        if (!file.exists()) {
            System.out.println("No transaction found.");
            return;
        }
        System.out.println("\n=====> Month To Date Transactions <======");

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 5) continue;

                String date = parts[0];
                if (date.startsWith(currentMonth)) {
                    System.out.printf("%s %s | %-20s | %-15s | %10s\n", parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transaction: " + e.getMessage());
        }
        promptReturnToMenu();

    }

    private static void filterByPreviousMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime previousMonthDate = now.minusMonths(1);
        String previousMonth = String.format("%d-%02d", previousMonthDate.getYear(), previousMonthDate.getMonthValue());

        File file = new File(TransactionFileHandler.FILE_PATH);
        if (!file.exists()) {
            System.out.println("No Transactions found.");
            return;
        }

        System.out.println("\n===> Previous Month Transactions <===");

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 5) continue;

                String date = parts[0]; // Format: yyyy-MM-dd
                if (date.startsWith(previousMonth)) {
                    System.out.printf("%s %s | %-20s | %-15s | %10s\n",
                            parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
        promptReturnToMenu();

    }


    private static void filterByYearToDate() {

        int currentYear = LocalDateTime.now().getYear();

        File file = new File(TransactionFileHandler.FILE_PATH);
        if (!file.exists()) {
            System.out.println("No Transactions found.");
            return;
        }

        System.out.println("\n===> Year To Date Transactions <===");

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 5) continue;

                String date = parts[0]; // Format: yyyy-MM-dd
                if (date.startsWith(String.valueOf(currentYear))) {
                    System.out.printf("%s %s | %-20s | %-15s | %10s\n",
                            parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        promptReturnToMenu();
    }


    private static void filterByPreviousYear() {
        int currentYear = LocalDateTime.now().getYear();
        int previousYear = currentYear - 1;

        File file = new File(TransactionFileHandler.FILE_PATH);
        if (!file.exists()) {
            System.out.println("No Transactions found.");
            return;
        }

        System.out.println("\n===> Previous Year Transactions <===");

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 5) continue;

                String date = parts[0]; // Format: yyyy-MM-dd
                if (date.startsWith(String.valueOf(previousYear))) {
                    System.out.printf("%s %s | %-20s | %-15s | %10s\n",
                            parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        promptReturnToMenu();
    }


    private static void filterByVendor() {

        System.out.print("\nEnter vendor name to search: ");
        String vendorSearch = scanner.nextLine().trim().toLowerCase();

        File file = new File(TransactionFileHandler.FILE_PATH);
        if (!file.exists()) {
            System.out.println("No Transactions found.");
            return;
        }

        System.out.println("\n=== Transactions for Vendor: " + vendorSearch + " ===");

        try (BufferedReader reader = new BufferedReader(new FileReader("/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 5) continue;

                String vendor = parts[3].toLowerCase();
                if (vendor.contains(vendorSearch)) {
                    System.out.printf("%s %s | %-20s | %-15s | %10s\n",
                            parts[0], parts[1], parts[2], parts[3], parts[4]);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions found for vendor: " + vendorSearch);
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        promptReturnToMenu();
    }


    static class TransactionFileHandler {
        public static final String FILE_PATH = "/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv";


        public static void saveTransaction(String entry) {
            try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
                writer.write(entry + "\n");
            } catch (IOException e) {
                System.out.println("Error saving transaction: " + e.getMessage());
            }
        }
    }

}
