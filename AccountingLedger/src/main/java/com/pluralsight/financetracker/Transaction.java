package com.pluralsight.financetracker;

import java.time.LocalDateTime;

public class Transaction {
    LocalDateTime purchase;
    String notes;
    String vendor;
    Double amount;

    public Transaction(LocalDateTime purchase, String notes, String vendor, Double amount) {
        this.purchase = purchase;
        this.notes = notes;
        this.vendor = vendor;
        this.amount = amount;
    }
}
