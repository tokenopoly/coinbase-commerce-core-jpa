/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

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

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

@Embeddable
public class PaymentPK implements PaymentIdentifier, Serializable {

    private static final long serialVersionUID = 6917567371063229215L;

    private String network;
    private String transactionId;

    @SuppressWarnings("unused")
    public static PaymentPK newPaymentId(PaymentIdentifier payment) {
        return new PaymentPK(payment.getNetwork(), payment.getTransactionId());
    }

}
