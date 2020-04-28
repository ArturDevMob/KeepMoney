package com.arturdevmob.keepmoney.data.database.models;

public enum TransactionType {
    EXPENSE(0),
    INCOME(1),
    TRANSFER(2);

    private int id;

    TransactionType(int id) {
        this.id = id;
    }

    public static TransactionType getById(int id) {
        for (TransactionType t : values()) {
            if (t.getId() == id) return t;
        }

        return null;
    }

    public int getId() {
        return id;
    }
}
