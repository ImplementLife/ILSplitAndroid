package com.impllife.split.ui.custom;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;

/**
 * Base class for scenarios where user wants to implement only one method of
 * {@link TextWatcher}.
 *
 * @hide
 */
public class TextWatcherAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(@NonNull CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(@NonNull Editable s) {
    }
}
