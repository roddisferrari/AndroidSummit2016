package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @BindView(R.id.main_transaction_list)
    RecyclerView transactionsList;

    @BindView(R.id.set_goal_card)
    ViewGroup setGoalCardContainer;
    @BindView(R.id.progress_bar_header_container)
    ViewGroup progressBarHeaderContainer;


    TransactionListAdapter transactionListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


    private void setupHeader(){

        SavingGoalStore savingGoalStore = new SavingGoalStore(getApplicationContext());

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
    }

    private NessieResultsListener accountListener =  new NessieResultsListener() {

        @Override
        public void onSuccess(Object result) {

            Account accountResponse = (Account) result;
            dManager.setAccountBalance(accountResponse.getBalance().intValue());
            accountBalanceTextView.setText("Your account balance is $" + accountResponse.getBalance().toString());



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
