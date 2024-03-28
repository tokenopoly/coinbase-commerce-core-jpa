/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * The concrete implementation of {@link PaymentIdentifier} for persistence purposes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

@Embeddable
public class PaymentPK implements PaymentIdentifier, Serializable {

    @Serial
    private static final long serialVersionUID = 6917567371063229215L;

    private String network;
    private String transactionId;

    @SuppressWarnings("unused")
    public static PaymentPK newPaymentId(final PaymentIdentifier payment) {
        return new PaymentPK(payment.getNetwork(), payment.getTransactionId());
    }

}
