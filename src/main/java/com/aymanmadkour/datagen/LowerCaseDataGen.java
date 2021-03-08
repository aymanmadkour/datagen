package com.aymanmadkour.datagen;

public class LowerCaseDataGen extends DataGen {
    private final DataGen input;

    public LowerCaseDataGen(DataGen input) {
        this.input = input;
    }

    @Override
    public final String generate(Context context) {
        return this.input.generate(context).toLowerCase();
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
