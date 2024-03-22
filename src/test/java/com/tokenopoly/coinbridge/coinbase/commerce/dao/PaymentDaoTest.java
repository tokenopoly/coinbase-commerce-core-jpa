/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.dao;

import com.google.common.collect.ImmutableMap;

import com.tokenopoly.coinbridge.coinbase.DefaultTestApp;
import com.tokenopoly.coinbridge.coinbase.commerce.model.Block;
import com.tokenopoly.coinbridge.coinbase.commerce.model.Payment;
import com.tokenopoly.coinbridge.coinbase.commerce.model.PaymentPK;
import com.tokenopoly.coinbridge.coinbase.commerce.model.Price;
import com.tokenopoly.coinbridge.coinbase.commerce.model.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = DefaultTestApp.class)
public class PaymentDaoTest {

    @Autowired
    private PaymentDao dao;

    private PaymentPK paymentId;
    private Payment payment;

    @BeforeEach
    public void setUp() {

        paymentId = new PaymentPK("test", "fake");

        dao.deleteAll();

        Block blk = Block.builder()
            .confirmationsAccumulated(1)
            .confirmationsRequired(6)
            .hash("fakehash")
            .height(1234)
            .build();

        Map<String, Price> prices = new ImmutableMap.Builder<String, Price>()
            .put("local", new Price(BigDecimal.valueOf(8000), "USD"))
            .put("crypto", new Price(BigDecimal.ONE, "BTC"))
            .build();
            

        payment = Payment.builder()
            .paymentId(paymentId)
            .status(Status.StatusValue.PENDING)
            .value(prices)
            .block(blk)
            .build();

        payment = dao.saveAndFlush(payment);
    }

    @Test
    public void testFind() {
        Optional<Payment> optP2 = dao.findById(paymentId);

        assertTrue(optP2.isPresent());

        Payment p2 = optP2.get();
        assertEquals(payment, p2);

        assertEquals("USD", p2.getValue().get("local").getCurrency());
        assertEquals(0, BigDecimal.valueOf(8000).compareTo(p2.getValue().get("local").getAmount()));
    }

    @Test
    public void testFindAll() {
        List<Payment> payments = dao.findAll();

        assertEquals(1, payments.size());
        assertEquals(payment, payments.get(0));
    }

    @Test
    public void testUpdate() {
        Optional<Payment> optP2 = dao.findById(paymentId);
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Payment p2 = optP2.get();

        p2.getValue().put("local", new Price(BigDecimal.valueOf(10000), "USD"));
        p2 = dao.saveAndFlush(p2);

        assertEquals(0, BigDecimal.valueOf(10000).compareTo(p2.getValue().get("local").getAmount()));
    }


}