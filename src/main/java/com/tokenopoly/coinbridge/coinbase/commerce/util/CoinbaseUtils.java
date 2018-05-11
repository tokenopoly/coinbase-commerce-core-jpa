/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.util;

import com.google.common.io.BaseEncoding;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@SuppressWarnings("WeakerAccess")
public class CoinbaseUtils {

    private static final String SIGNING_ALGORITHM = "HmacSHA256";
    private static final Charset UTF8 = Charset.forName("UTF8");

    private final Mac sha256Hmac;
    private final BaseEncoding signatureEncoding;

    public CoinbaseUtils(String sharedSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        final SecretKeySpec secretKey = new SecretKeySpec(sharedSecret.getBytes(UTF8), SIGNING_ALGORITHM);
        this.sha256Hmac = Mac.getInstance(SIGNING_ALGORITHM);
        sha256Hmac.init(secretKey);
        this.signatureEncoding = BaseEncoding.base16().lowerCase();
    }

    public String computeHmacSha256Signature(final String message) {
        final String nullSafeMessage = (message == null) ? "" : message;
        return signatureEncoding.encode(sha256Hmac.doFinal(nullSafeMessage.getBytes(UTF8)));
    }

    public boolean isValidSignature(final String signature, final String message, boolean withLogging) {
        if (signature == null) {
            if (null == message) {
                if (withLogging) {
                    log.debug("Header signature and message are both null.");
                }
                return true;
            } else {
                if (withLogging) {
                    log.warn("Header signature is null but message is {}", message);
                }
                return false;
            }
        } else {
            final String computedSignature = computeHmacSha256Signature(message);

            if (computedSignature.equals(signature)) {
                if (withLogging) {
                    log.debug("  Header signature: {}", signature);
                    log.debug("Computed signature: {}", computedSignature);
                }
                return true;
            } else {
                if (withLogging) {
                    log.warn("  Header signature: {}", signature);
                    log.warn("Computed signature: {}", computedSignature);
                }
                return false;
            }
        }
    }

    public boolean isValidSignature(final String signature, final String message) {
        return this.isValidSignature(signature, message, false);
    }

}
