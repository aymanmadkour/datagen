package com.aymanmadkour.datagen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class DefaultContext implements Context {
    private final Random random;
    private final Map<String, String> values = new HashMap<>();

    public DefaultContext() {
        this(new Random());
    }

    public DefaultContext(Random random) {
        this.random = random;
    }

    @Override
    public final int generateInt(int max) {
        return this.random.nextInt(max);
    }

    @Override
    public final long generateLong(long max) {
        return Math.abs(this.random.nextLong()) % max;
    }

    @Override
    public final double generateDouble(double max) {
        return this.random.nextDouble() * max;
    }

    @Override
    public final String get(String name) {
        return this.values.getOrDefault(name, "");
    }

    @Override
    public final void set(String name, String value) {
        this.values.put(name, value != null ? value : "");
    }

    public final void reset() {
        this.values.clear();
    }
}
