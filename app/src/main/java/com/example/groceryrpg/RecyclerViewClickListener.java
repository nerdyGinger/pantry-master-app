package com.example.groceryrpg;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Morgan on 11/29/2017.
 * interface for click listener... please work!
 */

public interface RecyclerViewClickListener {
    void onClick (View view, int position);
    boolean onLongClick (View view, int position);
}
