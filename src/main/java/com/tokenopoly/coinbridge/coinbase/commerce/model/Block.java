/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Attributes of a Payment that correspond to the blockchain Block in which the payment transaction is recorded.
 */
// Lombok annotation(s)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// Jackson annotation(s)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

// Hibernate & JPA annotation(s)
@Embeddable
public class Block implements Serializable {

    @Serial
    private static final long serialVersionUID = -3043589997739991990L;

    private long height;
    
    private String hash;

    @JsonAlias("confirmations")
    private long confirmationsAccumulated;

    private long confirmationsRequired;

}
