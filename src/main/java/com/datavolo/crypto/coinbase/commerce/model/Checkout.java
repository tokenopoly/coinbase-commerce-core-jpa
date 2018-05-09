/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.datavolo.crypto.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Checkout implements Serializable {

    private static final long serialVersionUID = 4136748974630732575L;

    @Nonnull
    private String id;

    private String name;

    private String description;

    private String logoUrl;

    private List<String> requestedInfo;

    private String pricingType;

    private Price localPrice;
}
