package com.example.groceryrpg;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Morgan on 12/18/2017.
 * Adapter for Lists activity
 */

public class ListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerViewClickListener mListener;
    public List<MyItem> mDataSet = new ArrayList<>();
    public List<MyItem> checkList = new ArrayList<>();
    private TextView itemName, quantity;
    private CheckBox check;

    public class ListsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private RecyclerViewClickListener myListener;

        ListsViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            itemName = v.findViewById(R.id.listItemName);
            quantity = v.findViewById(R.id.listQuantity);
            check = v.findViewById(R.id.listCheck);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onLongClick(view, getAdapterPosition());
            return true;
        }

    }

    ListsAdapter(RecyclerViewClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.lists_layout, parent, false);
        return new ListsViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ListsViewHolder listsHolder = (ListsViewHolder) holder;
        if(position<mDataSet.size()) {
            MyItem i = mDataSet.get(position);
            itemName.setText(i.getItemName());
            quantity.setText(i.getQuantity());
            check.setChecked(false);
        } else {
            MyItem j = checkList.get(position-mDataSet.size());
            itemName.setText(j.getItemName());
            quantity.setText(j.getQuantity());
            quantity.setPaintFlags(quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            check.setChecked(true);
        }
    }

    public void updateData(List<MyItem> dataSetOne, List<MyItem> dataSetTwo) {
        mDataSet.clear();
        mDataSet.addAll(dataSetOne);
        checkList.clear();
        checkList.addAll(dataSetTwo);
        notifyDataSetChanged();
    }

    public void onItemMove(int fromPos, int toPos) {
        if (fromPos < toPos) {
            for (int i = fromPos; i < toPos; i++) {
                Collections.swap(mDataSet, i, i + 1);
            }
        } else {
            for (int i = fromPos; i > toPos; i--) {
                Collections.swap(mDataSet, i, i - 1);
            }
        }
        notifyItemMoved(fromPos, toPos);
    }

    public int getItemCount() { return mDataSet.size()+checkList.size(); }

    @Override
    public int getItemViewType(int position) { return position; }

    @Override
    public long getItemId(int position) { return position; }
}
