/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.expression.function;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.String.format;

public class FunctionDefinition {
    /**
     * Converts an {@link UnresolvedFunction} into the a proper {@link Function}.
     */
    @FunctionalInterface
    public interface Builder {
        Function build(UnresolvedFunction uf, boolean distinct, TimeZone tz);
    }
    private final String name;
    private final List<String> aliases;
    private final Class<? extends Function> clazz;
    /**
     * Is this a datetime function comaptible with {@code EXTRACT}.
     */
    private final boolean datetime;
    private final Builder builder;
    private final FunctionType type;

    FunctionDefinition(String name, List<String> aliases, Class<? extends Function> clazz,
            boolean datetime, Builder builder) {
        this.name = name;
        this.aliases = aliases;
        this.clazz = clazz;
        this.datetime = datetime;
        this.builder = builder;
        this.type = FunctionType.of(clazz);
    }

    public String name() {
        return name;
    }

    public List<String> aliases() {
        return aliases;
    }

    public FunctionType type() {
        return type;
    }

    Class<? extends Function> clazz() {
        return clazz;
    }

    Builder builder() {
        return builder;
    }

    /**
     * Is this a datetime function comaptible with {@code EXTRACT}.
     */
    boolean datetime() {
        return datetime;
    }

    @Override
    public String toString() {
        return format(Locale.ROOT, "%s(%s)", name, aliases.isEmpty() ? "" : aliases.size() == 1 ? aliases.get(0) : aliases );
    }
}
