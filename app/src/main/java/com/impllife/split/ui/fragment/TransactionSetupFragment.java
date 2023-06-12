package com.impllife.split.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.TransactionDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.TransactionService;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.adapter.GridListAdapter;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.dialog.CalendarDialog;
import com.impllife.split.ui.view.BtnDate;

import java.math.BigDecimal;
import java.util.*;

import static com.impllife.split.data.constant.Constant.*;
import static com.impllife.split.service.Util.isBlank;

public class TransactionSetupFragment extends NavFragment {
    private final TransactionDao transactionDao = new TransactionService();
    private final DataService dataService = DataService.getInstance();

    private EditText etSum;
    private EditText etDscr;
    private Button btnSave;
    private BtnDate btnToday;
    private BtnDate btnYesterday;
    private BtnDate btnSelectDate;
    private GridView btnDateGroup;
    private ViewPager2 pagerFrom;
    private ViewPager2 pagerTo;

    private Transaction transaction;
    private Date dateCreate;
    private boolean update;
    private final PagerDataHolder from = new PagerDataHolder();
    private final PagerDataHolder to = new PagerDataHolder();

    public TransactionSetupFragment() {
        super(R.layout.fragment_transaction_setup, "New transaction");
    }

    @Override
    protected void init() {
        btnDateGroup = findViewById(R.id.group_btn_date);
        btnToday = new BtnDate(inflater, btnDateGroup);
        btnYesterday = new BtnDate(inflater, btnDateGroup);
        btnSelectDate = new BtnDate(inflater, btnDateGroup);
        etSum = findViewById(R.id.field_sum);
        etDscr = findViewById(R.id.field_dscr);
        btnSave = findViewById(R.id.btn_create);
        pagerFrom = findViewById(R.id.pager_from);
        pagerTo = findViewById(R.id.pager_to);

        initDateBtns();
        fillPager();

        Button btnNegate = findViewById(R.id.btn_negate);
        btnNegate.setOnClickListener(v -> {
            String sum = etSum.getText().toString();
            if (!isBlank(sum)) {
                BigDecimal decimal = new BigDecimal(sum).negate();
                etSum.setText(decimal.toString());
                etSum.setSelection(etSum.getText().length());
                btnNegate.setText(decimal.compareTo(new BigDecimal(0)) > 0 ? "-" : "+");
            }
        });
        findViewById(R.id.btn_create).setOnClickListener(v -> {
            runAsync(() -> {
                if (!valid()) return;
                save();
                post(() -> navController.navigateUp());
            });
        });
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
        btnToday.select();

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

        CalendarDialog calendarDialog = new CalendarDialog(date -> {
            btnSelectDate.select();
            btnToday.unselect();
            btnYesterday.unselect();

            dateCreate = date;
            btnSelectDate.setDate(dateCreate);
        });
        btnSelectDate.setName("Select");
        btnSelectDate.setDate(null);
        btnSelectDate.setOnClickListener(v -> calendarDialog.show());

        btnDateGroup.setAdapter(new GridListAdapter<>(
            Arrays.asList(btnToday, btnYesterday, btnSelectDate), (data, parent) -> data.getRoot()));
    }

    private void fillPager() {
        runAsync(() -> {
            List<Account> allAccounts = dataService.getAllAccounts();
            allAccounts.add(0, new EmptyElement());
            allAccounts.add(1, new PeopleSelectElement());
            List<Account> allAccounts2 = new ArrayList<>(allAccounts);
            post(() -> {
                int indexFrom = allAccounts.size() > 2 ? 2 : 1;
                int indexTo = 0;

                from.setData(allAccounts).setDefaultPos(indexFrom);
                to.setData(allAccounts2).setDefaultPos(indexTo);

                prepareViewPage(pagerFrom, from);
                prepareViewPage(pagerTo, to);

                from.updateOutData();
                to.updateOutData();
            });
        });
    }

    private static class EmptyElement extends Account {}
    private static class PeopleSelectElement extends Account {}

    private static class PagerDataHolder extends ViewPager2.OnPageChangeCallback {
        private List<Account> accountsList;
        private Account account;
        private People people;
        private int pos;
        private final TransactionSetupPeopleSelectFragment peopleSelectFragment;

        private PagerDataHolder() {
            peopleSelectFragment = new TransactionSetupPeopleSelectFragment();
            peopleSelectFragment.setCallback(() -> {
                this.people = peopleSelectFragment.getSelectedPeople();
                this.account = null;
            });
        }

        public PagerDataHolder setDefaultPos(int pos) {
            this.pos = pos;
            return this;
        }
        public PagerDataHolder setData(List<Account> accountsList) {
            this.accountsList = accountsList;
            return this;
        }
        public void setPeople(People people) {
            this.people = people;
            peopleSelectFragment.setSelectedPeople(people);
        }
        public void setAccount(Account account) {
            this.account = account;
        }

        public int getPos() {
            int index = pos;
            if (account != null) {
                index = accountsList.indexOf(account);
            } else if (people != null) {
                index = 1;
            }
            return index;
        }

        public void updateOutData() {
            onPageSelected(getPos());
        }

        @Override
        public void onPageSelected(int position) {
            Account account = accountsList.get(position);
            if (account instanceof EmptyElement) {
                this.people = null;
                this.account = null;
            } else if (account instanceof PeopleSelectElement) {
                this.people = peopleSelectFragment.getSelectedPeople();
                this.account = null;
            } else {
                this.account = account;
                this.people = null;
            }
        }
    }

    private static class Adapter extends FragmentStateAdapter {
        private final PagerDataHolder dataHolder;
        public Adapter(Fragment fragment, PagerDataHolder dataHolder) {
            super(fragment);
            this.dataHolder = dataHolder;
        }

        public Fragment createFragment(int position) {
            Account account = dataHolder.accountsList.get(position);
            if (account instanceof EmptyElement) {
                return new Fragment(R.layout.fragment_transaction_setup_empty_element);
            } else if (account instanceof PeopleSelectElement) {
                return dataHolder.peopleSelectFragment;
            } else {
                return new TransactionSetupAccountFragment(account);
            }
        }
        public int getItemCount() {
            return dataHolder.accountsList.size();
        }
    }

    private void prepareViewPage(ViewPager2 pager, PagerDataHolder dataHolder) {
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(new Adapter(this, dataHolder));
        pager.setCurrentItem(dataHolder.getPos());
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
        pager.registerOnPageChangeCallback(dataHolder);
    }

    @Override
    protected void argumentProcessing(Bundle args) {
        int trnId = args.getInt(ENTITY_ID, -1);
        boolean focusNeed = args.getBoolean(FOCUS_NEED, trnId == -1);
        if (focusNeed) MainActivity.getInstance().showKeyboard(etSum);

        String sum = args.getString(NOTIFY_TO_TRN_SUM, "");
        String dscr = args.getString(NOTIFY_TO_TRN_DSCR, "");
        etDscr.setText(dscr);
        etSum.setText(sum);

        if (trnId != -1) {
            runAsync(() -> {
                Optional<Transaction> optional = Optional.ofNullable(transactionDao.findById(trnId));
                optional.ifPresent(trn -> post(() -> {
                    update = true;
                    btnSave.setText("Update");
                    transaction = trn;
                    etSum.setText(trn.getSum());
                    etDscr.setText(trn.getDescription());
                    dateCreate = trn.getDateCreate();

                    from.setAccount(trn.getFromAccount());
                    from.setPeople(trn.getFromPeople());
                    to.setAccount(trn.getToAccount());
                    to.setPeople(trn.getToPeople());
                    pagerFrom.setCurrentItem(from.getPos());
                    pagerTo.setCurrentItem(to.getPos());
                }));
            });
        }
    }

    private boolean valid() {
        String sum = etSum.getText().toString();
        if (dateCreate == null) dateCreate = new Date();
        return !isBlank(sum)
            /*&& !Objects.equals(from.account, to.account)
            && !Objects.equals(from.people, to.people)*/;
    }

    private BigDecimal getSum() {
        String sum = etSum.getText().toString();
        if (!isBlank(sum)) return new BigDecimal(sum);
        else return new BigDecimal(0);
    }

    private void save() {
        if (!update) transaction = new Transaction();
        transaction.setSum(getSum().setScale(2).toString());
        transaction.setDateCreate(dateCreate);
        transaction.setDescription(etDscr.getText().toString());

        transaction.setFromPeople(from.people);
        transaction.setToPeople(to.people);

        transaction.setFromAccount(from.account);
        transaction.setToAccount(to.account);

        if (!update) {
            transactionDao.insert(transaction);
        } else {
            transactionDao.update(transaction);
        }
    }
}