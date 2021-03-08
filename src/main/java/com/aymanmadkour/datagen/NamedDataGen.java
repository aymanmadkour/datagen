package com.aymanmadkour.datagen;

public class NamedDataGen extends DataGen {
    private final String name;
    private final DataGen input;

    public NamedDataGen(String name, DataGen input) {
        this.name = name;
        this.input = input;
    }

    @Override
    public String generate(Context context) {
        String value = this.input.generate(context);
        context.set(this.name, value);
        return value;
    }

    @Override
    public void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
