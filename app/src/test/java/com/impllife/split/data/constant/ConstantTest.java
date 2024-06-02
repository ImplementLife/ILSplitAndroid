package com.impllife.split.data.constant;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConstantTest {
    @Test
    public void testUnique() {
        Field[] fields = Constant.class.getFields();
        Set<String> set = Arrays.stream(fields).map(f -> {
            try {
                return (String) f.get(f);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());
        assertEquals(fields.length, set.size());
    }

    @Test
    public void testDefaultUserIconUniqueValues() {
        Set<DefaultUserIcon> uniqueValues = new HashSet<>();
        Set<DefaultUserIcon> duplicateValues = new HashSet<>();

        for (DefaultUserIcon value : DefaultUserIcon.values()) {
            boolean isUnique = true;
            for (DefaultUserIcon uniqueValue : uniqueValues) {
                if (value.id == uniqueValue.id || Objects.equals(value.name, uniqueValue.name)) {
                    isUnique = false;
                    duplicateValues.add(uniqueValue);
                    break;
                }
            }
            if (isUnique) {
                uniqueValues.add(value);
            } else {
                duplicateValues.add(value);
            }
        }

        if (!duplicateValues.isEmpty()) {
            fail("Duplicate values found: " + duplicateValues);
        }
    }

}