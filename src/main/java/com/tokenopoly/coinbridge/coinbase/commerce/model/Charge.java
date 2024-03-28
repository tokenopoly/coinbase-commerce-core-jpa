/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ComparisonChain;
import com.tokenopoly.util.CompareUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a Coinbase Commerce Charge object, per that API.
 * <p>
 *     Charges are the first of the API objects (counting hierarchically) that are treated
 *     as entities to support persistence of state.
 * </p>
 */
// Lombok annotation(s)
@Data
@EqualsAndHashCode(of = {"code"})

// Jackson annotation(s)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

// Hibernate & JPA annotation(s)
@Entity
@Table(schema = "coinbase")  // TODO : replace me with .hbm.xml file(s)
@DynamicUpdate
public class Charge implements Serializable, Comparable<Charge> {

    @Serial
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

    /**
     * Some Charge instances were created from a Checkout. Others may have been created directly via
     * the Coinbase Commerce API.
     * <p>
     *     Marked as {@code @Transient} as we don't persist that part of the context.
     *     The intention with persistence is to be able to process Charges, in particular against invoices,
     *     and not to persist the entire state and context of the corresponding Coinbase account -
     *     Coinbase does that already.
     * </p>
     */
    @Transient
    private Checkout checkout;

    @ElementCollection(fetch = FetchType.EAGER)
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

    @Override
    public int compareTo(@NotNull Charge o) {
        if (o == null) {
            return -1;
        } else if (this == o) {
            return 0;
        } else {
            return ComparisonChain.start()
                .compare(this.confirmedAt, o.confirmedAt, CompareUtils.DateNaturalNullsLast)
                .compare(this.expiresAt, o.expiresAt, CompareUtils.DateNaturalNullsLast)
                .compare(this.createdAt, o.createdAt, CompareUtils.DateNaturalNullsLast)

                // If we need we can do this in separate steps: if we don't have an ordering here,
                // then take the most recent Status records for each Charge and use those.

                .compare(this.code, o.code, CompareUtils.StringNaturalNullsLast)
                .result();
        }
    }

}
