/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenopoly.coinbridge.coinbase.commerce.util.CoinbaseTest;

import org.junit.Test;

import java.io.IOException;

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
            return objectMapper.readValue(CoinbaseTest.payload, Webhook.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error deserializing test webhook payload", e);
        }
    }
    
    @Test
    public void deserializeWebhookPayloadTest() {
        Webhook webhook = deserializeWebhookPayload();
        assertEquals(1, webhook.getEvent().getData().getPayments().size());
    }

}