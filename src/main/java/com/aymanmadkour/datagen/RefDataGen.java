package com.aymanmadkour.datagen;

public class RefDataGen extends DataGen {
    private final String name;

    public RefDataGen(String name) {
        this.name = name;
    }

    @Override
    public final String generate(Context context) {
        return context.get(this.name);
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
