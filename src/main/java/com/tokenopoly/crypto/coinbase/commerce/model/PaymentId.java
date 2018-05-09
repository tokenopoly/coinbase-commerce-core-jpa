/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.crypto.coinbase.commerce.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class PaymentId implements IPaymentId, Serializable {

    private static final long serialVersionUID = 6917567371063229215L;
    private String network;
    private String transactionId;

    public static PaymentId newPaymentId(IPaymentId payment) {
        return new PaymentId(payment.getNetwork(), payment.getTransactionId());
    }

}
