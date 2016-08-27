package com.androidsummit.androidsummitsampleapp.balance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidsummit.androidsummitsampleapp.R;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris Anderson on 27/08/16.
 */
public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;

    private static final List<Purchase> EMPTY_LIST = new ArrayList<>();

    private List<Purchase> items = EMPTY_LIST;

    public TransactionListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void update(List<Purchase> items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = EMPTY_LIST;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Purchase purchase = items.get(position);
        holder.bind(purchase);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView transactionDescription;
        TextView transactionAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            transactionDescription = (TextView) itemView.findViewById(R.id.transaction_description);
            transactionAmount = (TextView) itemView.findViewById(R.id.transaction_amount);
        }

        public void bind(Purchase purchase) {
            transactionDescription.setText(purchase.getDescription());
            transactionAmount.setText(String.format("-$%.2f", purchase.getAmount()));
        }
    }
}
