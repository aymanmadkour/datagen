package com.aymanmadkour.datagen;

final class RandomIntegerDataGen extends DataGen {
    private final int minValue;
    private final int maxValue;

    public RandomIntegerDataGen(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public final String generate(Context context) {
        return String.valueOf(this.minValue + context.generateInt(this.maxValue - this.minValue + 1));
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
