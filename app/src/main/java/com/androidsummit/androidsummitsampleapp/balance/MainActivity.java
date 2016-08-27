package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.androidsummit.androidsummitsampleapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_transaction_list)
    RecyclerView transactionsList;

    TransactionListAdapter transactionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        transactionListAdapter = new TransactionListAdapter();

        transactionsList.setLayoutManager(new LinearLayoutManager(this));
        transactionsList.setAdapter(transactionListAdapter);
    }

    @OnClick(R.id.main_purchase_goal_container)
    void onPurchaseGoalClicked() {
        Intent purchaseGoalActivity = SavingsGoalActivity.createLaunchIntent(this);
        startActivity(purchaseGoalActivity);
    }

}
