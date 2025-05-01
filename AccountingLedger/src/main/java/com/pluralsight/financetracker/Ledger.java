package com.pluralsight.financetracker;

import java.time.LocalDateTime;

public class Ledger {
    LocalDateTime purchase;
    String description;
    String vendor;
    Double amount;

    public Ledger(LocalDateTime purchase, String description, String vendor, Double amount) {
        this.purchase = purchase;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getPurchase() {
        return purchase;
    }

    public void setPurchase(LocalDateTime purchase) {
        this.purchase = purchase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Ledger{" +
                "purchase=" + purchase +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                '}';


    }
}