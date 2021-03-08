package com.aymanmadkour.datagen;

import static com.aymanmadkour.datagen.DataGen.*;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.TreeSet;

public class RecordGenTest {
    @Test
    public void testGeneration() {
        RecordGen rg = RecordGen.builder()
                .add(string().minLength(10).maxLength(20).characterClass(CharacterClass.LOWER_CASE).build())
                .add(integer(1000))
                .add(valueList("a", "b", "c"))
                .build();

        for (int i = 0; i < 1000; i++) {
            String[] record = rg.generate();

            assertEquals(3, record.length);
            assertTrue(record[0].matches("[a-z]{10,20}"));

            int intValue = Integer.parseInt(record[1]);
            assertTrue(intValue >= 0 && intValue <= 1000);

            assertTrue(Arrays.asList("a", "b", "c").contains(record[2]));
        }
    }

    @Test
    public void testUnique() {
        RecordGen rg = RecordGen.builder()
                .add(named("value", integer(1000)))
                .unique("value")
                .build();

        TreeSet<Integer> generatedValues = new TreeSet<>();

        for (int i = 0; i < 1000; i++) {
            generatedValues.add(Integer.parseInt(rg.generate()[0]));
        }

        assertEquals("Expecting 1000 unique values", 1000, generatedValues.size());
    }
}
