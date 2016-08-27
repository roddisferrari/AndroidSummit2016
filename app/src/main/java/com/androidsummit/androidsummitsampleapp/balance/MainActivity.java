package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.androidsummit.androidsummitsampleapp.BuildConfig;
import com.androidsummit.androidsummitsampleapp.R;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private NessieClient mClient;
    private DataManager dManager;

    @BindView(R.id.main_balance)
    TextView accountBalanceTextView;

    @BindView(R.id.progress_header_account_balance)
    TextView accountHeaderBalanceTextView;

    @BindView(R.id.main_transaction_list)
    RecyclerView transactionsList;

    @BindView(R.id.set_goal_card)
    ViewGroup setGoalCardContainer;

    @BindView(R.id.progress_bar_header_container)
    ViewGroup progressBarHeaderContainer;

    @BindView(R.id.progress_header_progress_bar)
    RoundCornerProgressBar progressBar;

    TransactionListAdapter transactionListAdapter;
    SavingGoalStore savingGoalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        savingGoalStore = new SavingGoalStore(getApplicationContext());

        dManager = DataManager.getInstance();

        ButterKnife.bind(this);

        String key = BuildConfig.NESSIE_API_KEY;
        mClient = NessieClient.getInstance(key);

        mClient.ACCOUNT.getAccount(Constants.ACCOUNT_ID, accountListener);

        mClient.PURCHASE.getPurchasesByAccount(Constants.ACCOUNT_ID, purchaseListener);
        transactionListAdapter = new TransactionListAdapter(this);

        transactionsList.setLayoutManager(new LinearLayoutManager(this));
        transactionsList.setAdapter(transactionListAdapter);

        setupHeader();

    }

    @OnClick(R.id.main_purchase_goal_container)
    void onPurchaseGoalClicked() {
        Intent purchaseGoalActivity = SavingsGoalActivity.createLaunchIntent(this);
        startActivity(purchaseGoalActivity);
    }


    @OnClick(R.id.progress_header_goal_edit)
    void onEditPurchaseGoalClicked() {
        Intent purchaseGoalActivity = SavingsGoalActivity.createLaunchIntent(this);
        startActivity(purchaseGoalActivity);
    }


    private void setupHeader(){

        if(savingGoalStore.getName() != null) {
            showProgressBarHeader();
        } else {
            showSetGoalHeader();
        }
    }

    private void showSetGoalHeader() {

        setGoalCardContainer.setVisibility(View.VISIBLE);
        progressBarHeaderContainer.setVisibility(View.GONE);
    }

    private void showProgressBarHeader() {

        setGoalCardContainer.setVisibility(View.GONE);
        progressBarHeaderContainer.setVisibility(View.VISIBLE);

        progressBar.setProgressColor(Color.parseColor("#56d2c2"));
        progressBar.setProgressBackgroundColor(Color.parseColor("#757575"));

    }

    private NessieResultsListener accountListener =  new NessieResultsListener() {

        @Override
        public void onSuccess(Object result) {

            Account accountResponse = (Account) result;

            final Integer balanceInteger = accountResponse.getBalance();
            final int balance = balanceInteger == null ? 0 : balanceInteger.intValue();

            dManager.setAccountBalance(balance);
            accountBalanceTextView.setText("Your account balance is $" + balance);

            long goalAmmount = savingGoalStore.getAmount();

            double difference  = goalAmmount - balance;

            progressBar.setMax(goalAmmount);
            progressBar.setProgress(balance);

            accountHeaderBalanceTextView.setText(String.format("$%.2f", difference));


        }

        @Override
        public void onFailure(NessieError error) {
            accountBalanceTextView.setText("Oops");
        }
    };


    private NessieResultsListener purchaseListener = new NessieResultsListener() {

        @Override
        public void onSuccess(Object result) {
            List<Purchase> purchases = (List<Purchase>) result;
            dManager.setPurchaseList(purchases);
            transactionListAdapter.update(purchases);
        }

        @Override
        public void onFailure(NessieError error) {
        }
    };


}
