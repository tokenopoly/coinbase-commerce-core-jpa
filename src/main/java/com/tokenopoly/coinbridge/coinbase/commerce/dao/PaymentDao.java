/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.dao;

import com.tokenopoly.coinbridge.coinbase.commerce.model.Payment;
import com.tokenopoly.coinbridge.coinbase.commerce.model.PaymentPK;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
@SuppressWarnings("WeakerAccess")
public interface PaymentDao extends JpaRepository<Payment, PaymentPK> {

}
