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

    public LocalDateTime getPurchase() {
        return purchase;
    }

    public void setPurchase(LocalDateTime purchase) {
        this.purchase = purchase;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
