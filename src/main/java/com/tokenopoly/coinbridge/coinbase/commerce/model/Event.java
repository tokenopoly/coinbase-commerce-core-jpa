/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.coinbridge.coinbase.commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * The event which triggered the webhook callback.  See the Coinbase Commerce API docs for details.
 */
@Data
@EqualsAndHashCode(of = {"id"})
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuppressWarnings("unused")
public class Event implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 3205413059133644670L;

    public static final String Charge_Type_Prefix = "charge:";

    private String id;   // guid
    private String type;  // charge:created, charge:confirmed, charge:failed
    private String apiVersion;   //  2018-03-22
    private Date createdAt;

    /**
     * The primary payload of this Event.
     */
    private Charge data;

    @JsonIgnore
    public EventType getEventType() {
        if ((type != null) && type.toLowerCase().startsWith(Charge_Type_Prefix)) {
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
