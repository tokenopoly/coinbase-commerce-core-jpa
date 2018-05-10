/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.dao;

import com.tokenopoly.coinbridge.coinbase.commerce.model.Charge;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface TestChargeDao extends JpaRepository<Charge, String> {

}
