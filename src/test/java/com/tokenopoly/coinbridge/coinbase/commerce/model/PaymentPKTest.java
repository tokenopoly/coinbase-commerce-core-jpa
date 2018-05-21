/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PaymentPKTest {

    @Test
    public void newPaymentId() {
        final PaymentPK p1 = new PaymentPK("bitcoin", "afaketransactionnonid");
        final PaymentPK p2 = PaymentPK.newPaymentId(p1);

        assertNotSame(p1, p2);
        assertEquals(0, p1.compareTo(p2));
        assertEquals(p1, p2);
    }
}