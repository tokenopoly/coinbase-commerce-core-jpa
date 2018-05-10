/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.google.common.collect.ComparisonChain;

import com.tokenopoly.util.CompareUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.SortNatural;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(of = {"code"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Entity
@Table(schema = "coinbase")
@DynamicUpdate
@SelectBeforeUpdate
public class Charge implements Serializable, Comparable<Charge> {

    private static final long serialVersionUID = -5689772303429773407L;

    @Id
    private String code;

    private String name;
    private String description;
    private String logoUrl;
    private String hostedUrl;
    private String redirectUrl;

    private Date createdAt;
    private Date expiresAt;
    private Date confirmedAt;

    @Transient
    private Checkout checkout;

    @ElementCollection(fetch = FetchType.EAGER)
    @SortNatural
    @OrderBy("time, status, context")
    @CollectionTable(schema = "coinbase")
    private List<Status> timeline;

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @CollectionTable(schema = "coinbase")
    private Map<String, String> metadata;

    private String pricingType;

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @CollectionTable(schema = "coinbase")
    private Map<String, Price> pricing;

    @Fetch(FetchMode.JOIN)
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(schema = "coinbase", name = "charge_payment",
        inverseJoinColumns = {
            @JoinColumn(name = "network", referencedColumnName = "network"),
            @JoinColumn(name = "transaction_id", referencedColumnName = "transactionId")
        }
    )
    private Set<Payment> payments = new HashSet<>();

    @Fetch(FetchMode.JOIN)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "network")
    @Column(name = "address")
    @CollectionTable(schema = "coinbase")
    private Map<String, String> addresses;

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(@NotNull Charge o) {
        return ComparisonChain.start()
            .compare(this.confirmedAt, o.confirmedAt, CompareUtil.DateNaturalNullsLast)
            .compare(this.expiresAt, o.expiresAt, CompareUtil.DateNaturalNullsLast)
            .compare(this.createdAt, o.createdAt, CompareUtil.DateNaturalNullsLast)

            // If we need we can do this in separate steps: if we don't have an ordering here,
            // then take the most recent Status records for each Charge and use those.

            .compare(this.code, o.code, CompareUtil.StringNaturalNullsLast)
            .result();
    }

}
