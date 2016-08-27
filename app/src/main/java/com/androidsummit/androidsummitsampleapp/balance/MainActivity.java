package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.androidsummit.androidsummitsampleapp.BuildConfig;
import com.androidsummit.androidsummitsampleapp.R;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_transaction_list)
    RecyclerView transactionsList;
    private NessieClient mClient;
    private DataManager dManager;

    @BindView(R.id.main_balance)
    TextView accountBalanceTextView;

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

        transactionListAdapter = new TransactionListAdapter();

        transactionsList.setLayoutManager(new LinearLayoutManager(this));
        transactionsList.setAdapter(transactionListAdapter);
    }

    @OnClick(R.id.main_purchase_goal_container)
    void onPurchaseGoalClicked() {
        Intent purchaseGoalActivity = SavingsGoalActivity.createLaunchIntent(this);
        startActivity(purchaseGoalActivity);
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

}
