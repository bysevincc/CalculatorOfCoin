package com.calculator.coin.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calculator_coin")
@Getter
@Setter
public class CalculatorCoinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input", nullable = false)
    private String sourceType;

    @Column(name = "output", nullable = false)
    private String targetType;

    @Column(name = "input_type_amount", nullable = false)
    private double sourceAmount;

    @Column(name = "output_type_amount", nullable = false)
    private double targetAmount;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;
}
