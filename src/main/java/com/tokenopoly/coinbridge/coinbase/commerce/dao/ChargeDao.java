/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.dao;

import com.tokenopoly.coinbridge.coinbase.commerce.model.Charge;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
@SuppressWarnings("WeakerAccess")
public interface ChargeDao extends JpaRepository<Charge, String> {

}
