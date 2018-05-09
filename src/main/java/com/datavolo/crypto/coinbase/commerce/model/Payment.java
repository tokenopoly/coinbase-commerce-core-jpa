/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.datavolo.crypto.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(of = {"network", "transactionId"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Entity
@Table(schema = "coinbase")
@IdClass(PaymentId.class)
@DynamicUpdate
@SelectBeforeUpdate
public class Payment implements IPaymentId, Serializable {

    private static final long serialVersionUID = -5853451019519546806L;

    @Id
    private String network;

    @Id
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private Status.StatusValue status;

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "payment_values", schema = "coinbase"
        , joinColumns = {@JoinColumn(name = "network"), @JoinColumn(name = "transaction_id")}
    )
    @MapKeyColumn(name = "type")
    private Map<String, Price> value;

    @Embedded
    private Block block;

}
