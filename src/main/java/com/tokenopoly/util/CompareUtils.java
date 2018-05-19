/*
 * Copyright (c) 2018  Tokenopoly Financial Technology Inc.
 * All rights not explicitly granted in the LICENSE attached to this project are hereby reserved.
 */

package com.tokenopoly.util;

import java.util.Comparator;
import java.util.Date;

/**
 *
 */
@SuppressWarnings("unused")
public abstract class CompareUtils {

    public static final Comparator<String> StringNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

    public static final Comparator<Date> DateNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

    public static final Comparator<Long> LongNaturalNullsLast = Comparator.nullsLast(Comparator.naturalOrder());

}
