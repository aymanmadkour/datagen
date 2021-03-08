package com.aymanmadkour.datagen;

final class RandomRealDataGen extends DataGen {
    private final double minValue;
    private final double maxValue;

    public RandomRealDataGen(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public final String generate(Context context) {
        return String.valueOf(this.minValue + context.generateDouble(this.maxValue - this.minValue));
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
