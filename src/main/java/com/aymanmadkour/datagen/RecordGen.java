package com.aymanmadkour.datagen;

import java.util.*;

public final class RecordGen {
    private final DefaultContext context;
    private final DataGen[] fieldDataGens;
    private final DataGen[] uniqueIndexDataGens;
    private final Set<String>[] uniqueIndices;

    private RecordGen(Collection<DataGen> fields, Collection<DataGen> uniqueIndices) {
        this.context = new DefaultContext();
        this.fieldDataGens = fields.stream().toArray(DataGen[]::new);
        this.uniqueIndexDataGens = uniqueIndices.stream().toArray(DataGen[]::new);
        this.uniqueIndices = new Set[uniqueIndexDataGens.length];

        for (int i = 0; i < uniqueIndexDataGens.length; i++) {
            this.uniqueIndices[i] = new HashSet<>();
        }
    }

    public final String[] generate() {
        DefaultContext context = this.context;
        DataGen[] fieldDataGens = this.fieldDataGens;
        DataGen[] uniqueIndexDataGens = this.uniqueIndexDataGens;

        int fieldsLength = fieldDataGens.length;
        int uniqueIndicesLength = uniqueIndexDataGens.length;

        String[] record = new String[fieldsLength];
        context.reset();

        for (int i = 0; i < fieldsLength; i++) {
            record[i] = fieldDataGens[i].generate(context);
        }

        if (uniqueIndicesLength == 0) {
            return record;
        }

        Set<String>[] uniqueIndices = this.uniqueIndices;
        String[] uniqueValues = new String[uniqueIndicesLength];
        boolean unique = false;

        while (!unique) {
            unique = true;

            for (int i = 0; i < uniqueIndicesLength; i++) {
                String value = uniqueIndexDataGens[i].generate(context);

                if (uniqueIndices[i].contains(value)) {
                    unique = false;
                    break;

                } else {
                    uniqueValues[i] = value;
                }
            }

            if (unique) {
                for (int i = 0; i < uniqueIndicesLength; i++) {
                    uniqueIndices[i].add(uniqueValues[i]);
                }

                break;

            } else {
                context.reset();

                for (int i = 0; i < fieldsLength; i++) {
                    record[i] = fieldDataGens[i].generate(context);
                }
            }
        }

        return record;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<DataGen> fields = new ArrayList<>();
        private final List<DataGen> uniqueIndices = new ArrayList<>();

        public final Builder add(DataGen dataGen) {
            this.fields.add(dataGen);
            return this;
        }

        public final Builder unique(String name) {
            this.uniqueIndices.add(DataGen.ref(name));
            return this;
        }

        public final RecordGen build() {
            if (this.fields.isEmpty()) {
                throw new IllegalArgumentException("Must add at least one field");
            }

            return new RecordGen(this.fields, this.uniqueIndices);
        }
    }
}
