package com.desafio.skytef.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "tb_transaction")
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "establishment_name")
    private String establishmentName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "transaction_value")
    private BigDecimal value;
}
