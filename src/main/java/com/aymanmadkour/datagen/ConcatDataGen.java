package com.aymanmadkour.datagen;

final class ConcatDataGen extends DataGen {
    private DataGen[] elements;

    public ConcatDataGen(DataGen ... elements) {
        this.elements = elements;
    }

    @Override
    public final String generate(Context context) {
        StringBuilder s = new StringBuilder();
        this.generate(context, s);
        return s.toString();
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        for (DataGen element : this.elements) {
            element.generate(context, s);
        }
    }
}
