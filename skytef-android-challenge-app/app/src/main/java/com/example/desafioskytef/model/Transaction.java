package com.example.desafioskytef.model;

import java.math.BigDecimal;

public class Transaction {
    private Long id;
    private String establishmentName;
    private String cardNumber;
    private BigDecimal value;

    public Transaction() {
    }

    public Transaction(String establishmentName, String cardNumber, BigDecimal value) {
        this.establishmentName = establishmentName;
        this.cardNumber = cardNumber;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", establishmentName='" + establishmentName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", value=" + value +
                '}';
    }

}
