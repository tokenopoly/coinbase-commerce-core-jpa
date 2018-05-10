/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.util;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 *
 */
public class CoinbaseTest {

    public static final String payload = "{\"attempt_number\":4,\"event\":{\"api_version\":\"2018-03-22\",\"created_at\":\"2018-04-23T22:59:44Z\",\"data\":{\"code\":\"ABC123PQ\",\"name\":\"Testing\",\"metadata\":{\"ref\":\"freely\",\"email\":\"some.user@mac.me\",\"ethAddress\":\"12345678901005FACE\"},\"payments\":[{\"block\":{\"hash\":\"0000000000000000003fe62782061346bf61476613bb72ca302d8a7ef968a5f4\",\"height\":519641,\"confirmations\":0,\"confirmations_required\":1},\"value\":{\"local\":{\"amount\":\"0.77\",\"currency\":\"USD\"},\"crypto\":{\"amount\":\"0.00008629\",\"currency\":\"BTC\"}},\"status\":\"CONFIRMED\",\"network\":\"bitcoin\",\"transaction_id\":\"alsoafaketransactionid\"}],\"timeline\":[{\"time\":\"2018-04-23T22:56:44Z\",\"status\":\"NEW\"},{\"time\":\"2018-04-23T22:59:13Z\",\"status\":\"PENDING\",\"payment\":{\"network\":\"bitcoin\",\"transaction_id\":\"alsoafaketransactionid\"}},{\"time\":\"2018-04-23T22:59:43Z\",\"status\":\"COMPLETED\",\"payment\":{\"network\":\"bitcoin\",\"transaction_id\":\"alsoafaketransactionid\"}}],\"addresses\":{\"bitcoin\":\"notshowingtheaddress\",\"litecoin\":\"notshowingtheaddress\",\"bitcoincash\":\"notshowingtheaddress\"},\"created_at\":\"2018-04-23T22:56:44Z\",\"expires_at\":\"2018-04-23T23:11:44Z\",\"hosted_url\":\"https://commerce.coinbase.com/charges/ABC123PQ\",\"description\":\"The Token\",\"confirmed_at\":\"2018-04-23T22:59:43Z\",\"pricing_type\":\"no_price\",\"redirect_url\":\"https://some.site.com/buy\"},\"id\":\"27abe13d-e015-4d99-85ca-293e7408bea7\",\"type\":\"charge:confirmed\"},\"id\":\"54c98af4-cfce-4a66-9e16-ac6cd38e3608\",\"scheduled_for\":\"2018-04-23T23:02:11Z\"}";


    private final Coinbase coinbase;

    public CoinbaseTest() throws InvalidKeyException, NoSuchAlgorithmException {
        coinbase = new Coinbase("ourlittlesecret");
    }

    @Test
    public void isValidSignature() {
        assertTrue(coinbase.isValidSignature(null, null, true));

        assertTrue(coinbase.isValidSignature("c497bf782d17eef1f0530d0572fb1d28cb441122925d5d4507f262f4b7874ff7",
                   payload, true));

        assertFalse(coinbase.isValidSignature("82837c799a2a99e1cd4865468af914b014fca5289f549803b899ca45b2a1ba63",
                                             payload, true));

        assertFalse(coinbase.isValidSignature(null, "foo", true));

        assertFalse(coinbase.isValidSignature("foo", payload, true));
    }

    @Test
    public void isValidSignature1() {
        assertTrue(coinbase.isValidSignature(null, null));

        assertTrue(coinbase.isValidSignature("c497bf782d17eef1f0530d0572fb1d28cb441122925d5d4507f262f4b7874ff7",
                                             payload));

        assertFalse(coinbase.isValidSignature("82837c799a2a99e1cd4865468af914b014fca5289f549803b899ca45b2a1ba63",
                                              payload));

        assertFalse(coinbase.isValidSignature(null, "foo"));

        assertFalse(coinbase.isValidSignature("foo", payload));
    }
}