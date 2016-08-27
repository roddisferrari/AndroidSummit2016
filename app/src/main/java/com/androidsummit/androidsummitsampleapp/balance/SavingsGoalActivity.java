package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.androidsummit.androidsummitsampleapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavingsGoalActivity extends AppCompatActivity {


    static Intent createLaunchIntent(Context context) {
        Intent intent = new Intent(context, SavingsGoalActivity.class);
        return intent;
    }

    private DataManager dataManager;

    @BindView(R.id.savings_goal_amount)
    EditText goalAmount;

    @BindView(R.id.savings_goal_description)
    EditText goalDescription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avaialble_balance)
    TextView accountAvailableBalance;

    private TextWatcher textChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onEditTextChanged();
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    private SavingGoalStore savingGoalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_goal);

        savingGoalStore = new SavingGoalStore(this);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.ic_close);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goalAmount.addTextChangedListener(textChangeListener);
        goalDescription.addTextChangedListener(textChangeListener);

        dataManager = DataManager.getInstance();

        accountAvailableBalance.setText("Available Balance: $" + dataManager.getAccountBalance());
    }

    private void onEditTextChanged() {

        String amountAsString = goalAmount.getText().toString();
        String description = goalDescription.getText().toString();

        boolean hasDescription = !TextUtils.isEmpty(description);
        boolean hasAmount = !TextUtils.isEmpty(amountAsString);

        long amount = 0;
        if (hasAmount) {
            try {
                amount = Long.valueOf(amountAsString);
            } catch (NumberFormatException ex) {
                // Ignore
            }
        }


        savingGoalStore.update(description, amount);

        toolbar.setNavigationIcon(hasDescription && hasAmount ? R.drawable.ic_done : R.drawable.ic_close);

    }

}
