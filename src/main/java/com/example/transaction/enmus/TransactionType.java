package com.example.transaction.enmus;

public enum TransactionType {

    DEPOSIT(1, "存款交易"),
    Transfer(2, "转账交易"),
    PAY(3, "支付交易");

    private int type;
    private String descr;

    TransactionType(int type, String descr) {
        this.type = type;
        this.descr = descr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public static TransactionType valueOfKey(int type) {
        for (TransactionType transactionType : values()) {
            if (transactionType.type == type) {
                return transactionType;
            }
        }
        return null;
    }

    public static TransactionType valueOfVal(String descr) {
        for (TransactionType transactionType : values()) {
            if (transactionType.descr.equals(descr)) {
                return transactionType;
            }
        }
        return null;
    }

}
