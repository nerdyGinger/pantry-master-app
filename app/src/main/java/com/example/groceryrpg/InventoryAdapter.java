package com.example.groceryrpg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 11/24/2017.
 * Adapter for inventory list RecyclerView (it works! TuT)
 */


public class InventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerViewClickListener mListener;
    private List<MyItem> mDataSet = new ArrayList<>();
    private TextView itemName, quantity, nmItemName, nmQuantity;
    private ProgressBar lifeBar;

    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener myListener;
        RowViewHolder (View v, RecyclerViewClickListener listener) {
            super(v);
            itemName = v.findViewById(R.id.nmitem_name);
            quantity = v.findViewById(R.id.nmnumber);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public class LifeBarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener myListener;
        LifeBarViewHolder (View v, RecyclerViewClickListener listener) {
            super(v);
            nmItemName = v.findViewById(R.id.item_name);
            nmQuantity = v.findViewById(R.id.number);
            lifeBar = v.findViewById(R.id.lifeBar);
            mListener = listener;
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    InventoryAdapter(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        switch (viewType) {
            case 1:
                View v = LayoutInflater.from(context).inflate(R.layout.nonmulti_layout, parent, false);
                return new RowViewHolder(v, mListener);
            case 2:
                View w = LayoutInflater.from(context).inflate(R.layout.inventory_layout, parent, false);
                return new LifeBarViewHolder(w, mListener);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.nonmulti_layout, parent, false);
        return new RowViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                RowViewHolder rowHolder = (RowViewHolder) holder;
                MyItem i = mDataSet.get(position);
                itemName.setText(i.getItemName());
                quantity.setText(i.getQuantity());
                break;
            case 2:
                LifeBarViewHolder lifeViewHolder = (LifeBarViewHolder) holder;
                MyItem k = mDataSet.get(position);
                nmItemName.setText(k.getItemName());
                nmQuantity.setText(k.getQuantity());
                lifeBar.setMax(k.getUnitsPer());
                lifeBar.setProgress(k.getUnits());
                break;
        }
    }

    public void updateData(List<MyItem> dataset) {
        mDataSet.clear();
        mDataSet.addAll(dataset);
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        MyItem i = mDataSet.get(position);
        if(i.getUnitsPer() == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}