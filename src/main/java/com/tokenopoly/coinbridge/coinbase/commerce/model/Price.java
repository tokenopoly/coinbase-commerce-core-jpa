/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Simple Price object which represents a price (or payment) in a currency.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Price implements Serializable {

    @Serial
    private static final long serialVersionUID = -737739062044621361L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

}
