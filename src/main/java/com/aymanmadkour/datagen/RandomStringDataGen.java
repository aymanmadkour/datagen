package com.aymanmadkour.datagen;

import java.util.Random;

final class RandomStringDataGen extends DataGen {
    private final int minLength;
    private final int maxLength;
    private Spec[] specs;
    private final double totalWeight;

    public RandomStringDataGen(int minLength, int maxLength, Spec ... specs) {
        this.minLength = minLength;
        this.maxLength = maxLength;

        if (specs.length == 0) {
            specs = new Spec[] {
                    new Spec(CharacterClass.LOWER_CASE, 0.9),
                    new Spec(CharacterClass.SPACE, 0.1),
            };
        }

        this.specs = specs;

        double totalWeight = 0.0;
        for (Spec spec : specs) {
            totalWeight += spec.getWeight();
        }

        if (totalWeight == 0) {
            totalWeight = 1.0;
        }

        this.totalWeight = totalWeight;
    }

    @Override
    public final String generate(Context context) {
        StringBuilder s = new StringBuilder();
        this.generate(context, s);
        return s.toString();
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        int length = this.minLength + context.generateInt(this.maxLength - this.minLength + 1);

        for (int i = 0; i < length; i++) {
            double x = context.generateDouble(this.totalWeight);
            double upperLimit = 0;

            for (Spec spec : this.specs) {
                upperLimit += spec.getWeight();

                if (x < upperLimit) {
                    CharacterClass cc = spec.getCharacterClass();
                    int index = context.generateInt(cc.getCount());
                    s.append(cc.getCharacter(index));
                    break;
                }
            }
        }
    }

    public static final class Spec {
        private final CharacterClass characterClass;
        private final double weight;

        public Spec(CharacterClass characterClass, double weight) {
            this.characterClass = characterClass;
            this.weight = weight;
        }

        public final CharacterClass getCharacterClass() {
            return characterClass;
        }

        public final double getWeight() {
            return weight;
        }
    }
}
