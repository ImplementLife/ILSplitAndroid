package com.impllife.split.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.view.BtnDate;
import com.impllife.split.ui.view.PeopleView;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static android.view.Gravity.FILL_HORIZONTAL;
import static com.impllife.split.ui.fragment.DateSelectFragment.RESULT_KEY;

public class TransactionSetupFragment extends NavFragment {
    private final DataService dataService = DataService.getInstance();
    private Date dateCreate;

    private BtnDate btnToday;
    private BtnDate btnYesterday;
    private BtnDate btnSelectDate;
    private GridLayout grid;

    private EditText etSum;
    private EditText etDscr;
    private Transaction transaction = new Transaction();
    private ViewPager2 pagerCardFrom;
    private ViewPager2 pagerCardTo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_transaction_setup, inflater, container);
        setNavTitle("New transaction");

        init();
        initDateBtns();
        initPager();

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            runAsync(() -> {
                save();
                view.post(() -> navController.navigateUp());
            });
        });

        return view;
    }

    private void initPager() {
        runAsync(() -> {
            List<Account> allAccounts = dataService.getAllAccounts();
            List<Account> allAccounts2 = dataService.getAllAccounts();
            allAccounts.add(0, null);
            post(() -> {
                class Ad extends FragmentStateAdapter {
                    private List<Account> accounts;
                    public Ad(Fragment fragment, List<Account> accounts) {
                        super(fragment);
                        this.accounts = accounts;
                    }

                    public Fragment createFragment(int position) {
                        Account account = accounts.get(position);
                        if (account == null) {
                            return new TransactionSetupPeopleSelectFragment();
                        } else {
                            return new TransactionSetupAccountFragment();
                        }
                    }
                    public int getItemCount() {
                        return accounts.size();
                    }
                }
                prepareViewPage(pagerCardFrom, new Ad(this, allAccounts), 1);
                prepareViewPage(pagerCardTo, new Ad(this, allAccounts2), 0);
            });
        });
    }

    private void prepareViewPage(ViewPager2 pager, FragmentStateAdapter pageAdapter, int pos) {
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(pageAdapter);
        pager.setCurrentItem(pos);
        final float nextItemVisiblePx = getResources().getDimension(R.dimen.viewpager_next_item_visible);
        final float currentItemHorizontalMarginPx = getResources().getDimension(R.dimen.viewpager_current_item_horizontal_margin);
        final float pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        pager.setPageTransformer((page, position) -> {
            page.setTranslationX(-pageTranslationX * position);
            page.setScaleY(1 - (0.10f * Math.abs(position)));
        });
        pager.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int horizontalMarginInPx = (int) getResources().getDimension(R.dimen.viewpager_current_item_horizontal_margin);
                outRect.right = horizontalMarginInPx;
                outRect.left = horizontalMarginInPx;
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundleProcessing();
    }

    private void initDateBtns() {
        Calendar calendar = Calendar.getInstance();
        btnToday.setName("Today");
        btnToday.setDate(calendar.getTime());
        btnToday.setOnClickListener(v -> {
            btnToday.select();
            btnYesterday.unselect();
            btnSelectDate.unselect();

            dateCreate = btnToday.getDate();
        });

        dateCreate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        btnYesterday.setName("Yesterday");
        btnYesterday.setDate(calendar.getTime());
        btnYesterday.setOnClickListener(v -> {
            btnYesterday.select();
            btnToday.unselect();
            btnSelectDate.unselect();

            dateCreate = btnYesterday.getDate();
        });

        btnSelectDate.setName("Select");
        initDateSelect();
        btnSelectDate.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_date_select);
        });

        getParentFragmentManager().setFragmentResultListener(RESULT_KEY, this, (key, bundle) -> {
            if (RESULT_KEY.equals(key)) {
                btnSelectDate.select();
                btnToday.unselect();
                btnYesterday.unselect();

                long selected = bundle.getLong("date");
                calendar.setTimeInMillis(selected);
                dateCreate = calendar.getTime();
                btnSelectDate.setDate(dateCreate);
            }
        });

        setBtn(btnToday.getRoot(), 0, 0);
        setBtn(btnYesterday.getRoot(), 0, 1);
        setBtn(btnSelectDate.getRoot(), 0, 2);
    }

    private void bundleProcessing() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int trn_id = arguments.getInt("trn_id");
            boolean fn = arguments.getBoolean("focus_need", false);
            if (fn) MainActivity.getInstance().showKeyboard(etSum);
            runAsync(() -> {
                Optional<Transaction> trnById = dataService.findTrnById(trn_id);
                root.post(() -> {
                    if (trnById.isPresent()) {
                        transaction = trnById.get();
                        etSum.setText(transaction.getSum());
                        etDscr.setText(transaction.getDescription());
                    }
                });
            });
        }
    }

    private void init() {
        grid = findViewById(R.id.group_btn);
        btnToday = new BtnDate(inflater, grid);
        btnYesterday = new BtnDate(inflater, grid);
        btnSelectDate = new BtnDate(inflater, grid);
        etSum = findViewById(R.id.field_sum);
        etDscr = findViewById(R.id.field_dscr);
        pagerCardFrom = findViewById(R.id.pager_card_from);
        pagerCardTo = findViewById(R.id.pager_card_to);
    }

    private void save() {
        transaction.setSum(etSum.getText().toString());
        transaction.setDateCreate(dateCreate);
        transaction.setDescription(etDscr.getText().toString());

        dataService.insert(transaction);
    }

    private void initDateSelect() {
        long dateCreateAsLong = 0;
        if (getArguments() != null) {
            dateCreateAsLong = getArguments().getLong("date");
        }
        if (dateCreateAsLong != 0) {
            this.dateCreate = new Date(dateCreateAsLong);
            this.btnSelectDate.setDate(dateCreate);
        } else {
            this.dateCreate = new Date();
            this.btnSelectDate.setDate(null);
        }
    }

    private void setBtn(View view, int row, int col) {
        androidx.gridlayout.widget.GridLayout.LayoutParams layoutParams = new androidx.gridlayout.widget.GridLayout.LayoutParams(
            androidx.gridlayout.widget.GridLayout.spec(row, 1f),
            androidx.gridlayout.widget.GridLayout.spec(col, 1f));
        layoutParams.setGravity(FILL_HORIZONTAL);
        layoutParams.height = 100;

        grid.addView(view, layoutParams);
    }
}