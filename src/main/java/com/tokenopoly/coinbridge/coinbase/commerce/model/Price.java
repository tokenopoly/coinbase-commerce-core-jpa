/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Price implements Serializable {

    private static final long serialVersionUID = -737739062044621361L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

}
