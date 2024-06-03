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
    ic_png_contact_icon_6(R.drawable.ic_png_contact_icon_6, "ic_png_contact_icon_6"),
    ic_png_contact_icon_7(R.drawable.ic_png_contact_icon_7, "ic_png_contact_icon_7"),
    ic_png_contact_icon_8(R.drawable.ic_png_contact_icon_8, "ic_png_contact_icon_8"),
    ic_png_contact_icon_9(R.drawable.ic_png_contact_icon_9, "ic_png_contact_icon_9"),
    ic_png_contact_icon_10(R.drawable.ic_png_contact_icon_10, "ic_png_contact_icon_10"),
    ic_png_contact_icon_11(R.drawable.ic_png_contact_icon_11, "ic_png_contact_icon_11"),
    ic_png_contact_icon_12(R.drawable.ic_png_contact_icon_12, "ic_png_contact_icon_12"),
    ic_png_contact_icon_13(R.drawable.ic_png_contact_icon_13, "ic_png_contact_icon_13"),
    ic_png_contact_icon_14(R.drawable.ic_png_contact_icon_14, "ic_png_contact_icon_14"),
    ic_png_contact_icon_15(R.drawable.ic_png_contact_icon_15, "ic_png_contact_icon_15"),
    ic_png_contact_icon_16(R.drawable.ic_png_contact_icon_16, "ic_png_contact_icon_16"),
    ic_png_contact_icon_17(R.drawable.ic_png_contact_icon_17, "ic_png_contact_icon_17"),
    ic_png_contact_icon_18(R.drawable.ic_png_contact_icon_18, "ic_png_contact_icon_18"),
    ic_png_contact_icon_19(R.drawable.ic_png_contact_icon_19, "ic_png_contact_icon_19"),
    ic_png_contact_icon_20(R.drawable.ic_png_contact_icon_20, "ic_png_contact_icon_20"),
    ic_png_contact_icon_21(R.drawable.ic_png_contact_icon_21, "ic_png_contact_icon_21"),
    ic_png_contact_icon_22(R.drawable.ic_png_contact_icon_22, "ic_png_contact_icon_22"),
    ic_png_contact_icon_23(R.drawable.ic_png_contact_icon_23, "ic_png_contact_icon_23"),
    ic_png_contact_icon_24(R.drawable.ic_png_contact_icon_24, "ic_png_contact_icon_24"),
    ic_png_contact_icon_25(R.drawable.ic_png_contact_icon_25, "ic_png_contact_icon_25"),
    ic_png_contact_icon_26(R.drawable.ic_png_contact_icon_26, "ic_png_contact_icon_26"),
    ic_png_contact_icon_27(R.drawable.ic_png_contact_icon_27, "ic_png_contact_icon_27"),
    ic_png_contact_icon_28(R.drawable.ic_png_contact_icon_28, "ic_png_contact_icon_28"),
    ic_png_contact_icon_29(R.drawable.ic_png_contact_icon_29, "ic_png_contact_icon_29"),
    ic_png_contact_icon_30(R.drawable.ic_png_contact_icon_30, "ic_png_contact_icon_30"),
    ic_png_contact_icon_31(R.drawable.ic_png_contact_icon_31, "ic_png_contact_icon_31"),
    ic_png_contact_icon_32(R.drawable.ic_png_contact_icon_32, "ic_png_contact_icon_32"),
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

    public static Optional<DefaultUserIcon> parse(int id) {
        for (DefaultUserIcon value : values()) {
            if (value.id == id) {
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
