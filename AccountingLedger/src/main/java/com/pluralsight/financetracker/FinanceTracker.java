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
        System.out.println("Payment recorded and saved!");


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

        private static void promptReturnToMenu () {
            System.out.println("\nPress Enter to return to the main menu...");
            scanner.nextLine();
        }

    private static void exit() {
    }
}


class TransactionFileHandler {
    public static final String FILE_PATH = "/Users/doul/Pluralsight/capstone-1/Capstone-1/AccountingLedger/src/data/Transactions.csv";


    public static void saveTransaction(String entry) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(entry + "\n");
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }


    }
}





