package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.entity.type.BudgetPeriod;
import com.impllife.split.data.jpa.provide.BudgetDao;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.NavFragment;

import java.math.BigDecimal;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.service.util.Util.isBlank;

public class BudgetSetupFragment extends NavFragment {
    private static final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private Spinner spPeriod;
    private EditText etName;
    private EditText etSum;
    private CheckBox cbShowInTrn;

    private Budget entity;

    public BudgetSetupFragment() {
        super(R.layout.fragment_budget_setup, "Budget");
    }

    @Override
    protected void init() {
        spPeriod = findViewById(R.id.sp_period);
        ArrayAdapter<BudgetPeriod> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, BudgetPeriod.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriod.setAdapter(adapter);
        spPeriod.setSelection(1);
        etName = findViewById(R.id.et_name);
        etSum = findViewById(R.id.et_sum);
        cbShowInTrn = findViewById(R.id.cb_show_in_trn);

        findViewById(R.id.btn_ok).setOnClickListener(v -> save());

    }

    @Override
    protected void argumentProcessing(Bundle args) {
        int trnId = args.getInt(ENTITY_ID, -1);
        if (trnId >= 0) load(trnId);
    }

    private void load(int id) {
        runAsync(() -> {
            entity = budgetDao.findById(id);

            etName.setText(entity.getName());
            etSum.setText(entity.getSumForPeriod().toString());
            spPeriod.setSelection(entity.getPeriod().getKey());
            cbShowInTrn.setChecked(entity.isShowInTransaction());
        });
    }

    private void save() {
        if (entity == null) {
            entity = new Budget();
        }
        entity.setPeriod((BudgetPeriod) spPeriod.getSelectedItem());
        entity.setName(etName.getText() != null ? etName.getText().toString() : null);
        String sum = etSum.getText().toString();
        entity.setSumForPeriod(!isBlank(sum) ? new BigDecimal(sum) : null);
        entity.setShowInTransaction(cbShowInTrn.isChecked());

        runAsync(() -> {
            boolean isValid = !isBlank(entity.getName()) && entity.getSumForPeriod() != null;
            if (isValid) {
                if (entity.getId() == null) {
                    budgetDao.insert(entity);
                } else {
                    budgetDao.update(entity);
                }
                post(() -> navController.navigateUp());
            }
        });
    }
}