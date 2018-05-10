/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.crypto.coinbase.commerce.model;

/**
 *
 */
public interface PaymentIdentifier {

    String getNetwork();

    String getTransactionId();

}
