package com.impllife.split.ui.custom;

import java.util.ArrayList;
import java.util.List;

public class BtnRadioGroup {
    private final List<RadioBtn> btns = new ArrayList<>();

    public void add(RadioBtn item) {
        btns.add(item);
        item.setGroup(this);
    }

    public void select(RadioBtn target) {
        for (RadioBtn btn : btns) {
            if (!target.equals(btn)) {
                btn.unselect();
            }
        }
    }

    public interface RadioBtn {
        void select();
        void unselect();
        void setGroup(BtnRadioGroup group);
    }
}
