/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.datavolo.crypto.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Date;

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
public class Webhook implements Serializable {

    private static final long serialVersionUID = -367624435630683620L;

    @Nonnull
    private String id;

    @Nonnull
    private Date scheduledFor;

    private Event event;

}
