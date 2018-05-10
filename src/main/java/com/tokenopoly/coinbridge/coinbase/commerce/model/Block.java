/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Embeddable
public class Block implements Serializable {

    private static final long serialVersionUID = -3043589997739991990L;

    private long height;
    
    private String hash;

    @JsonAlias("confirmations")
    private long confirmationsAccumulated;

    private long confirmationsRequired;

}
