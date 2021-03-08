package com.aymanmadkour.datagen;

import static com.aymanmadkour.datagen.DataGen.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DataGenTest {
    private final DefaultContext context = new DefaultContext();

    @Before
    public void setUp() {
        this.context.reset();
    }

    @Test
    public void testInteger() {
        DataGen dataGen = integer(-1000, 1000);

        for (int i = 0; i < 1000; i++) {
            String value = dataGen.generate(context);
            int intValue = Integer.parseInt(value);
            assertTrue(intValue >= -1000 && intValue <= 1000);
        }
    }
    @Test
    public void testReal() {
        DataGen dataGen = real(-1.5, 5.7);

        for (int i = 0; i < 1000; i++) {
            String value = dataGen.generate(context);
            double doubleValue = Double.parseDouble(value);
            assertTrue(doubleValue >= -1.5 && doubleValue <= 5.7);
        }
    }

    @Test
    public void testDateTime() throws Exception {
        String dateFormatStr = "yyyyMMddHHmmssSSS";
        String minValueStr = "20000101000000000";
        String maxValueStr = "20301231235959999";

        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        Date minValue = dateFormat.parse(minValueStr);
        Date maxValue = dateFormat.parse(maxValueStr);

        DataGen dataGen = DataGen.dateTime(minValueStr, maxValueStr, dateFormatStr);

        for (int i = 0; i < 1000; i++) {
            String valueStr = dataGen.generate(context);
            Date value = dateFormat.parse(valueStr);
            assertTrue(value.getTime() >= minValue.getTime() && value.getTime() <= maxValue.getTime());
        }
    }

    @Test
    public void testString() {
        DataGen dataGen = string()
                .minLength(10)
                .minLength(20)
                .characterClass(CharacterClass.LOWER_CASE)
                .characterClass(CharacterClass.UPPER_CASE)
                .characterClass(CharacterClass.DIGITS)
                .build();

        for (int i = 0; i < 1000; i++) {
            String value = dataGen.generate(context);
            assertTrue(value.matches("^[a-zA-Z0-9]{10,20}$"));
        }
    }

    @Test
    public void testValueList() {
        DataGen dataGen = valueList("a", "b", "c");
        Set<String> values = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            values.add(dataGen.generate(context));
        }

        assertEquals(3, values.size());
        assertTrue(values.contains("a"));
        assertTrue(values.contains("b"));
        assertTrue(values.contains("c"));
    }

    @Test
    public void testSelector() {
        DataGen dataGen = selector()
                .add(literal("a"))
                .add(literal("b"))
                .add(literal("c"))
                .build();

        Set<String> values = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            values.add(dataGen.generate(context));
        }

        assertEquals(3, values.size());
        assertTrue(values.contains("a"));
        assertTrue(values.contains("b"));
        assertTrue(values.contains("c"));
    }

    @Test
    public void testLiteral() {
        String value = literal("test").generate(context);
        assertEquals("test", value);
    }

    @Test
    public void testConcat() {
        String value = concat(literal("a"), literal("b"), literal("c")).generate(context);
        assertEquals("abc", value);
    }

    @Test
    public void testLowerCase() {
        String value = lowerCase(literal("ABC")).generate(context);
        assertEquals("abc", value);
    }

    @Test
    public void testUpperCase() {
        String value = upperCase(literal("abc")).generate(context);
        assertEquals("ABC", value);
    }

    @Test
    public void testRef() {
        DataGen namedDataGen = named("x", literal("abc"));
        DataGen refDataGen = ref("x");

        namedDataGen.generate(context);
        String value = refDataGen.generate(context);

        assertEquals("abc", value);
    }
}
