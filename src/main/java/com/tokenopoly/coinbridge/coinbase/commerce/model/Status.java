/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ComparisonChain;
import com.tokenopoly.util.CompareUtils;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 *
 */
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Embeddable
@SuppressWarnings("unused")
public class Status implements Serializable, Comparable<Status> {

    @Serial
    private static final long serialVersionUID = 656845959161022248L;

    public static final Comparator<StatusValue> StatusNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());
    public static final Comparator<ContextValue> ContextNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());

    @Nonnull
    private Date time;
    @Nonnull
    @Enumerated(EnumType.STRING)
    private StatusValue status;
    @Enumerated(EnumType.STRING)
    private ContextValue context;

    private PaymentPK payment;

    @Override
    public int compareTo(@NotNull Status o) {
        if (o == null) {
            return -1;
        } else if (this == o) {
            return 0;
        } else {
            return ComparisonChain.start()
                .compare(this.time, o.time, CompareUtils.DateNaturalNullsLast)
                .compare(this.status, o.status, StatusNaturalNullsLast)
                .compare(this.context, o.context, ContextNaturalNullsLast)
                .compare(this.payment, o.payment, PaymentIdentifier.PaymentIdentifierNaturalNullsLast)
                .result();
        }
    }

    public enum StatusValue {
        NEW,
        PENDING,
        CONFIRMED,
        COMPLETED,
        EXPIRED,
        UNRESOLVED,
        RESOLVED
    }


    public enum ContextValue {
        UNDERPAID, OVERPAID, DELAYED, MULTIPLE, MANUAL, OTHER
    }
}
