package com.aymanmadkour.datagen;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public abstract class DataGen {
    protected DataGen() {}

    public abstract String generate(Context context);
    public abstract void generate(Context context, StringBuilder s);

    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static DataGen integer(int maxValue) { return new RandomIntegerDataGen(0, maxValue); }
    public static DataGen integer(int minValue, int maxValue) { return new RandomIntegerDataGen(minValue, maxValue); }
    public static DataGen real(double minValue, double maxValue) { return new RandomRealDataGen(minValue, maxValue); }
    public static DataGen real(double maxValue) { return new RandomRealDataGen(0.0, maxValue); }
    public static DataGen dateTime(long minTimestamp, long maxTimestamp) { return new RandomDateTimeDataGen(minTimestamp, maxTimestamp, DEFAULT_DATE_TIME_FORMAT); }
    public static DataGen dateTime(long minTimestamp, long maxTimestamp, String format) { return new RandomDateTimeDataGen(minTimestamp, maxTimestamp, format); }
    public static DataGen valueList(String ... values) { return new ValueListDataGen(values); }
    public static DataGen dateTime(String minTimestamp, String maxTimestamp, String format) { return new RandomDateTimeDataGen(minTimestamp, maxTimestamp, format); }
    public static RandomStringBuilder string() { return new RandomStringBuilder(); }
    public static SelectorBuilder selector() { return new SelectorBuilder(); }
    public static DataGen concat(DataGen ... elements) { return new ConcatDataGen(elements); }
    public static DataGen literal(String value) { return new LiteralStringDataGen(value); }
    public static DataGen lowerCase(DataGen input) { return new LowerCaseDataGen(input); }
    public static DataGen upperCase(DataGen input) { return new UpperCaseDataGen(input); }
    public static DataGen named(String name, DataGen input) { return new NamedDataGen(name, input); }
    public static DataGen ref(String name) { return new RefDataGen(name); }

    public static final class RandomStringBuilder {
        private int minLength = 5;
        private int maxLength = 10;
        private final List<RandomStringDataGen.Spec> specs = new ArrayList<>();

        public final RandomStringBuilder length(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("Length must be zero or more");
            }

            this.minLength = length;
            this.maxLength = length;
            return this;
        }

        public final RandomStringBuilder minLength(int minLength) {
            if (minLength < 0) {
                throw new IllegalArgumentException("Minimum length must be zero or more");
            }

            this.minLength = minLength;

            if (this.maxLength < this.minLength) {
                this.maxLength = this.minLength;
            }

            return this;
        }

        public final RandomStringBuilder maxLength(int maxLength) {
            if (maxLength < 0) {
                throw new IllegalArgumentException("Maximum length must be zero or more");
            }

            this.maxLength = maxLength;

            if (this.minLength > this.maxLength) {
                this.minLength = this.maxLength;
            }

            return this;
        }

        public final RandomStringBuilder characterClass(CharacterClass characterClass) {
            return this.characterClass(characterClass, 1.0);
        }

        public final RandomStringBuilder characterClass(CharacterClass characterClass, double weight) {
            if (characterClass == null) {
                throw new IllegalArgumentException("Character class cannot be null");
            }

            if (weight <= 0) {
                throw new IllegalArgumentException("Weight must be positive");
            }

            for (RandomStringDataGen.Spec spec : this.specs) {
                if (spec.getCharacterClass() == characterClass) {
                    throw new IllegalArgumentException("Cannot use the same character class twice");
                }
            }

            this.specs.add(new RandomStringDataGen.Spec(characterClass, weight));
            return this;
        }

        public final RandomStringDataGen build() {
            return new RandomStringDataGen(this.minLength, this.maxLength, this.specs.stream().toArray(RandomStringDataGen.Spec[]::new));
        }
    }

    public static final class SelectorBuilder {
        private List<SelectorDataGen.Option> options = new ArrayList<>();

        public final SelectorBuilder add(DataGen dataGen) {
            return this.add(dataGen, 1.0);
        }

        public final SelectorBuilder add(DataGen dataGen, double weight) {
            if (weight <= 0) {
                throw new IllegalArgumentException("Weight must be positive");
            }

            this.options.add(new SelectorDataGen.Option(dataGen, weight));
            return this;
        }

        public final DataGen build() {
            if (this.options.isEmpty()) {
                throw new IllegalArgumentException("Selector must have at least one option");
            }

            return new SelectorDataGen(this.options.stream().toArray(SelectorDataGen.Option[]::new));
        }
    }
}
