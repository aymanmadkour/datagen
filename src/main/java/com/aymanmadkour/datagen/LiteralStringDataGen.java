package com.aymanmadkour.datagen;

final class LiteralStringDataGen extends DataGen {
    private final String value;

    public LiteralStringDataGen(String value) {
        this.value = value != null ? value : "";
    }

    @Override
    public final String generate(Context context) {
        return this.value;
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.value);
    }
}
