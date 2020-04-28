package com.arturdevmob.keepmoney.data.database.models;

public enum CurrencyType {
    // Этот список валют связан с values/strings/currency_type
    RUB(0),
    EUR(1),
    USD(2),
    BYN(3),
    KZT(4),
    UAH(5);

    private int id;

    CurrencyType(int id) {
        this.id = id;
    }

    public static CurrencyType getById(int id) {
        for (CurrencyType c : values()) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public int getId() {
        return id;
    }
}
