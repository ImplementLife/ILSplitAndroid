package com.impllife.split.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.impllife.split.service.Util.isBlank;

public class NotifyProcessingDialog extends Dialog {
    private Spinner spinner;
    private NotificationInfo info;
    private Consumer<String> callback;

    public NotifyProcessingDialog(NotificationInfo info) {
        super(MainActivity.getInstance());
        this.info = info;
    }
    public NotifyProcessingDialog(NotificationInfo info, Consumer<String> callback) {
        this(info);

        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notify_processing);

        getWindow().setLayout(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tvData = findViewById(R.id.tv_data);
        String text = info.getText();
        tvData.setText(text);

        spinner = findViewById(R.id.spinner);
        text = text.replace('\n', ' ');

        List<String> data = new ArrayList<>();
        String regexDigit = "(\\+?\\d+\\.\\d+)\\s*";
        // forming like -> "\\b(UAH|USD|EUR)\\b"
        String regexCurrencies = /*"\\b(" + */String.join("|", extractCurrencySymbols(text))/* + ")\\b"*/;
        //Pattern pattern = Pattern.compile("(\\d+\\.\\d+)\\s*UAH");
//        Pattern pattern = Pattern.compile(regexDigit + regexCurrencies);
        Pattern pattern = Pattern.compile("\\b(\\d+(?:\\.\\d+)?)\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String res = matcher.group(1);
            if (!isBlank(res)) {
                data.add(res);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.btn_cancel).setOnClickListener(v -> hide());
        findViewById(R.id.btn_ok).setOnClickListener(v -> {
            hide();
            if (callback != null) callback.accept(spinner.getSelectedItem().toString());
        });
    }

    private List<String> extractCurrencySymbols(String text) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b([A-Z]{3})\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) result.add(matcher.group(1));
        return result;
    }
}
