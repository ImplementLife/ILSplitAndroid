package com.impllife.split.data.constant;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

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

}