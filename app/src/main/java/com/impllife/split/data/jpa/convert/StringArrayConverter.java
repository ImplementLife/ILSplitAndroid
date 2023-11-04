package com.impllife.split.data.jpa.convert;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringArrayConverter {
    @TypeConverter
    public static String toString(List<String> data) {
        return String.join("\n", data);
    }

    @TypeConverter
    public static List<String> toList(String data) {
        return Arrays.stream(data.split("\n")).collect(Collectors.toList());
    }
}
