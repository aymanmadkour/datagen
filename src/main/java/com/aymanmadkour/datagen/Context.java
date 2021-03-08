package com.aymanmadkour.datagen;

public interface Context {
    int generateInt(int max);
    long generateLong(long max);
    double generateDouble(double max);
    void set(String name, String value);
    String get(String name);
}
