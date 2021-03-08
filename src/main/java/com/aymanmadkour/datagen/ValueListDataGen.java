package com.aymanmadkour.datagen;

import java.util.Random;

public final class ValueListDataGen extends DataGen {
    private final String[] values;

    public ValueListDataGen(String ... values) {
        this.values = values;
    }

    @Override
    public final String generate(Context context) {
        int index = context.generateInt(this.values.length);
        return this.values[index];
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
