package com.impllife.split.data.constant;

import com.impllife.split.R;

public enum DefaultAccountImg implements ImgDbMapping {
    img_1(R.drawable.ic_png_default_card_1, "default_card_1"),
    img_2(R.drawable.ic_png_default_card_2, "default_card_2"),
    img_3(R.drawable.ic_png_default_card_3, "default_card_3"),
    img_4(R.drawable.ic_png_default_card_4, "default_card_4"),
    img_5(R.drawable.ic_png_default_card_5, "default_card_5"),
    img_6(R.drawable.ic_png_default_card_6, "default_card_6"),
    img_7(R.drawable.ic_png_default_card_7, "default_card_7"),
    img_8(R.drawable.ic_png_default_card_8, "default_card_8"),
    img_9(R.drawable.ic_png_default_card_9, "default_card_9"),
    ;
    public final int id;
    public final String name;

    DefaultAccountImg(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DefaultAccountImg parse(String name) {
        for (DefaultAccountImg value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getResId() {
        return id;
    }
}
