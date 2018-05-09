/*
 * Copyright (c) 2018. DataVolo, Inc.
 */

package com.tokenopoly.crypto.coinbase.commerce.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(of = {"id"})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Event implements Serializable {

    public static final String Charge_Type_Prefix = "charge:";
    private static final long serialVersionUID = 3205413059133644670L;
    private String id;   // guid
    private String type;  // charge:created, charge:confirmed, charge:failed
    private String apiVersion;   //  2018-03-22
    private Date createdAt;
    private Charge data;

    public EventType getEventType() {
        if (type != null && type.toLowerCase().startsWith(Charge_Type_Prefix)) {
            return EventType.valueOf(type.toLowerCase().substring(Charge_Type_Prefix.length()));
        } else {
            return null;
        }
    }

    public enum EventType {
        created,
        confirmed,
        failed
    }

}
