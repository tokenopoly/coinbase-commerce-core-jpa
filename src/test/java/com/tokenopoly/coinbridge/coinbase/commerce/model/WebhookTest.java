/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenopoly.coinbridge.coinbase.commerce.util.CoinbaseUtilsTest;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

/**
 *
 */
@Slf4j
public class WebhookTest {

    private final ObjectMapper objectMapper;

    public WebhookTest() {
        objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public Webhook deserializeWebhookPayload() {
        try {
            return objectMapper.readValue(CoinbaseUtilsTest.payload, Webhook.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error deserializing test webhook payload", e);
        }
    }
    
    @Test
    public void deserializeWebhookPayloadTest() {
        final Webhook webhook = deserializeWebhookPayload();
        final Event event = webhook.getEvent();
        assertEquals(Event.EventType.confirmed, event.getEventType());
        log.trace("{}", webhook.toString());
        final Charge charge = event.getData();
        final Set<Payment> payments = charge.getPayments();
        assertEquals(1, payments.size());
        final Payment payment = payments.iterator().next();
        assertTrue("USD".equalsIgnoreCase(payment.getLocalPrice().getCurrency()));
        assertTrue("BTC".equalsIgnoreCase(payment.getCryptoPrice().getCurrency()));

        final Event e2 = new Event();
        assertNull(e2.getEventType());

        assertNotSame(event, e2);
        assertNotEquals(event, e2);

        final Webhook remade = new Webhook();
        remade.setId(webhook.getId());
        remade.setScheduledFor(new Date());

        assertNotSame(webhook, remade);
        assertEquals(webhook,remade);
    }

}