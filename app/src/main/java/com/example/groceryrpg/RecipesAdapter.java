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
 * Created by nerdyGinger on 12/15/2017.
 * Adapter for recipes RecyclerView XD
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerViewClickListener mListener;
    private List<String> mDataSet = new ArrayList<>();
    private TextView recipeName;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener myListener;
        MyViewHolder (View v, RecyclerViewClickListener listener) {
            super(v);
            recipeName = v.findViewById(R.id.recipeName);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) { mListener.onClick(view, getAdapterPosition()); }
    }

    RecipesAdapter (RecyclerViewClickListener listener) { mListener = listener; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recipes_layout, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        String name = mDataSet.get(position);
        recipeName.setText(name);
    }

    public void updateData(List<String> dataSet) {
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
