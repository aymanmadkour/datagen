package com.aymanmadkour.datagen;

public class UpperCaseDataGen extends DataGen {
    private final DataGen input;

    public UpperCaseDataGen(DataGen input) {
        this.input = input;
    }

    @Override
    public final String generate(Context context) {
        return this.input.generate(context).toUpperCase();
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
