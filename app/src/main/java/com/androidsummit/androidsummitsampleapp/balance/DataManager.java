package com.androidsummit.androidsummitsampleapp.balance;

import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;

import java.util.List;

/**
 * Created by Jose Garcia on 27/08/2016.
 */
public class DataManager {

    private static DataManager instance;

    private int accountBalance;

    private List<Purchase> purchaseList;
    private List<Deposit> depositList;


    private DataManager() {

    }

    public static DataManager getInstance() {

        if(instance != null) {

            instance = new DataManager();
        }

        return instance;
    }

    public int getAccountBalance() {

        return accountBalance;
    }

    public List<Purchase> getPurchaseList() {

        return purchaseList;
    }

    public List<Deposit> getDepositList() {

        return depositList;
    }

    public void setAccountBalance(int accountBalance) {

        this.accountBalance = accountBalance;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {

        this.purchaseList = purchaseList;
    }

    public void setDepositList(List<Deposit> depositList) {

        this.depositList = depositList;
    }
}
