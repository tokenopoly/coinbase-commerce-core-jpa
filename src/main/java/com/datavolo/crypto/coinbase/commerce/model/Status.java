/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.datavolo.crypto.coinbase.commerce.model;

import com.google.common.collect.ComparisonChain;

import com.datavolo.util.CompareUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Embeddable
public class Status implements Serializable, Comparable<Status> {

    public static final Comparator<StatusValue> StatusNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());
    public static final Comparator<ContextValue> ContextNaturalNullsLast =
        Comparator.nullsLast(Comparator.naturalOrder());
    private static final long serialVersionUID = 656845959161022248L;
    @Nonnull
    private Date time;
    @Nonnull
    @Enumerated(EnumType.STRING)
    private StatusValue status;
    @Enumerated(EnumType.STRING)
    private ContextValue context;

    @Override
    public int compareTo(@NotNull Status o) {
        return ComparisonChain.start()
            .compare(this.time, o.time, CompareUtil.DateNaturalNullsLast)
            .compare(this.status, o.status, StatusNaturalNullsLast)
            .compare(this.context, o.context, ContextNaturalNullsLast)
            .result();
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
