/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"network", "transactionId"})

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

@Entity
@IdClass(PaymentPK.class)
@Table(schema = "coinbase")
@DynamicUpdate

@SuppressWarnings({"unused", "WeakerAccess"})
public class Payment implements PaymentIdentifier, Serializable {

    @Serial
    private static final long serialVersionUID = -5853451019519546806L;

    public static final String LOCAL_VALUE_KEY = "local";
    public static final String CRYPTO_VALUE_KEY = "crypto";

    @Builder
    public Payment(PaymentPK paymentId, Status.StatusValue status, Map<String, Price> value, Block block) {
        this.network = paymentId == null ? null : paymentId.getNetwork();
        this.transactionId = paymentId == null ? null : paymentId.getTransactionId();
        this.status = status;
        this.value = (value == null) ? new HashMap<>() : value;
        this.block = block;
    }

    @Id
    private String network;

    @Id
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private Status.StatusValue status;

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "payment_values", schema = "coinbase"
        , joinColumns = {@JoinColumn(name = "network"), @JoinColumn(name = "transaction_id")}
    )
    @MapKeyColumn(name = "type")
    private Map<String, Price> value;

    @Embedded
    private Block block;

    @Transient
    protected Price getPriceForKey(final String key) {
        return (value != null && value.containsKey(key))
               ? value.get(key)
               : null;
    }

    @Transient
    public Price getLocalPrice() {
        return getPriceForKey(LOCAL_VALUE_KEY);
    }

    @Transient
    public Price getCryptoPrice() {
        return getPriceForKey(CRYPTO_VALUE_KEY);
    }
    
}
