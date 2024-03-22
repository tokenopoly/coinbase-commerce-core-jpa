/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class PaymentPKTest {

    private PaymentPK p1;
    private PaymentPK p2;

    @BeforeEach
    public void setUp() {
        p1 = new PaymentPK("bitcoin", "afaketransactionnonid");
        p2 = PaymentPK.newPaymentId(p1);
    }

    @Test
    public void newPaymentId() {
        assertNotSame(p1, p2);
        assertEquals(0, p1.compareTo(p2));
        assertEquals(p1, p2);
    }

    @Test
    public void compareTo() {
        final PaymentPK p3 = p1;
        assertEquals(-1, p1.compareTo(null));    // nulls last
        assertEquals(0, p1.compareTo(p3));

        p2.setTransactionId("different");
        assertNotSame(p1, p2);
        assertNotEquals(p1, p2);
        assertNotEquals(0, p1.compareTo(p2));

        p2.setTransactionId(p1.getTransactionId());
        p2.setNetwork("litecoin");
        assertNotSame(p1, p2);
        assertNotEquals(p1, p2);
        assertNotEquals(0, p1.compareTo(p2));
    }
}