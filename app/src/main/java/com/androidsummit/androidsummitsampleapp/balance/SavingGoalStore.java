package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Chris Anderson on 27/08/16.
 */
public class SavingGoalStore {

    private static final String KEY_NAME = "Name";
    private static final String KEY_AMOUNT = "Amount";
    private static final String PREFERENCE_FILE = "SavingsGoal";

    private final SharedPreferences sharedPreferences;
    private String name;
    private long amount;

    public SavingGoalStore(Context context) {
         this.sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
    }

    public void update(String name, long amount) {
        this.name = name;
        this.amount = amount;

        sharedPreferences.edit()
                .putString(KEY_NAME, name)
                .putLong(KEY_NAME, amount)
                .apply();
    }

    public String getName() {
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public long getAmount() {
        return sharedPreferences.getLong(KEY_AMOUNT, 0);
    }
}
