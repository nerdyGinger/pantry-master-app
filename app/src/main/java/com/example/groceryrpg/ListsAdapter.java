package com.example.groceryrpg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 12/18/2017.
 * Adapter for Lists activity
 */

public class ListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerViewClickListener mListener;
    private List<MyItem> mDataSet = new ArrayList<MyItem>();
    private TextView itemName, quantity;

    public class ListsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener myListener;
        ListsViewHolder (View v, RecyclerViewClickListener listener) {
            super(v);
            itemName = v.findViewById(R.id.listItemName);
            quantity = v.findViewById(R.id.listQuantity);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) { mListener.onClick(view, getAdapterPosition()); }
    }

    ListsAdapter(RecyclerViewClickListener listener) { mListener = listener; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.lists_layout, parent, false);
        return new ListsViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListsViewHolder listsHolder = (ListsViewHolder) holder;
        MyItem i = mDataSet.get(position);
        itemName.setText(i.getItemName());
        quantity.setText(i.getQuantity());
    }

    public void updateData(List<MyItem> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public int getItemCount() { return mDataSet.size(); }

    @Override
    public int getItemViewType(int position) { return position; }

    @Override
    public long getItemId(int position) { return position; }
}
