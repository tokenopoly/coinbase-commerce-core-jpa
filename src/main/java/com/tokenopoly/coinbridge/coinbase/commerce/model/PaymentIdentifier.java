/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.google.common.collect.ComparisonChain;

import com.tokenopoly.util.CompareUtils;

import java.util.Comparator;

/**
 *
 */
public interface PaymentIdentifier extends Comparable<PaymentIdentifier> {

    Comparator<PaymentIdentifier> PaymentIdentifierNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());
    
    String getNetwork();

    String getTransactionId();

    @SuppressWarnings("NullableProblems")
    @Override
    default int compareTo(PaymentIdentifier o) {
        return ComparisonChain.start()
            .compare(this.getNetwork(), o.getNetwork(), CompareUtils.StringNaturalNullsLast)
            .compare(this.getTransactionId(), o.getTransactionId(), CompareUtils.StringNaturalNullsLast)
            .result();
    }

}
