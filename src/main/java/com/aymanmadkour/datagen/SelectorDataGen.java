package com.aymanmadkour.datagen;

final class SelectorDataGen extends DataGen {
    private final Option[] options;
    private final double totalWeight;

    public SelectorDataGen(Option ... options) {
        this.options = options;

        double totalWeight = 0.0;

        for (Option option : options) {
            totalWeight += option.getWeight();
        }

        this.totalWeight = totalWeight;
    }

    @Override
    public String generate(Context context) {
        return select(context).generate(context);
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        select(context).generate(context, s);
    }

    private final DataGen select(Context context) {
        double x = context.generateDouble(this.totalWeight);
        double upperLimit = 0.0;

        for (Option option : this.options) {
            upperLimit += option.getWeight();

            if (x < upperLimit) {
                return option.getDataGen();
            }
        }

        return null;
    }

    public static final class Option {
        private final double weight;
        private final DataGen dataGen;

        public Option(DataGen dataGen, double weight) {
            this.dataGen = dataGen;
            this.weight = weight;
        }

        public final DataGen getDataGen() {
            return dataGen;
        }

        public final double getWeight() {
            return weight;
        }
    }
}
