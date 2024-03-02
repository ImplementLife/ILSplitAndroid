package com.impllife.split.data.constant;

import com.impllife.split.R;
import com.impllife.split.service.util.Optional;

public enum DefaultUserIcon implements ImgDbMapping {
    ic_png_contact_default(R.drawable.ic_png_contact_default, "ic_png_contact_default"),
    ic_png_contact_icon_1(R.drawable.ic_png_contact_icon_1, "ic_png_contact_icon_1"),
    ic_png_contact_icon_2(R.drawable.ic_png_contact_icon_2, "ic_png_contact_icon_2"),
    ic_png_contact_icon_3(R.drawable.ic_png_contact_icon_3, "ic_png_contact_icon_3"),
    ic_png_contact_icon_4(R.drawable.ic_png_contact_icon_4, "ic_png_contact_icon_4"),
    ic_png_contact_icon_5(R.drawable.ic_png_contact_icon_5, "ic_png_contact_icon_5"),
    ;

    public final int id;
    public final String name;


    DefaultUserIcon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Optional<DefaultUserIcon> parse(String name) {
        for (DefaultUserIcon value : values()) {
            if (value.name.equals(name)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
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
