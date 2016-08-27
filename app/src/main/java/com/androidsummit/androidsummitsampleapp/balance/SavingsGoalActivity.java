package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.androidsummit.androidsummitsampleapp.R;

public class SavingsGoalActivity extends AppCompatActivity {


    static Intent createLaunchIntent(Context context) {
        Intent intent = new Intent(context, SavingsGoalActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_close);
    }

}
