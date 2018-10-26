package com.example.groceryrpg;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;


public class ListsFrag extends Fragment {
    // This Lists Fragment doesn't look like much yet, but it will display shopping lists
    // for items that are running low in the inventory, or whatever the user wants to add
    // to them. Eventually, there should be scrolling tabs at the top for multiple lists,
    // for all of us type-A's that like to have our lists sorted by store or aisle.

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<MyItem> currentList = new ArrayList<>();
    private List<MyItem> toBeRaised = new ArrayList<>();
    private List<MyItem> toBeDropped = new ArrayList<>();
    private ListsAdapter adapter;
    private CheckBox check;

    public ListsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ListsFrag newInstance(String param1, String param2) {
        ListsFrag fragment = new ListsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        check = view.findViewById(R.id.listCheck);

        // Pull list info from storage ...
        List<MyItem> items = new ArrayList<>();
        List<MyItem> checkedItems = new ArrayList<>();
        items.add(new MyItem("Bread", "1", "Slices", 2, 20));
        items.add(new MyItem("Peanut Butter", "1", "Tbs", 4, 12));
        checkedItems.add(new MyItem("Milk", "1", "Cups", 1, 8));
        currentList.addAll(items);
        currentList.addAll(checkedItems);

        // Set up RecyclerView for list items
        final RecyclerView rv = view.findViewById(R.id.listsRecycler);
        rv.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyItem i = currentList.get(position);
                //crossOut(i);
                // ^oops, just realized that has a few fundamental problems... I'll come back to this later
            }
            @Override
            public boolean onLongClick(View view, int position) { return false; }
        };
        adapter = new ListsAdapter(listener);
        adapter.updateData(items, checkedItems);
        rv.setAdapter(adapter);

        return view;
    }

    private void crossOut(MyItem i) {
        // Handles click events by checking box and adding item to a hold list to either be
        // raised or lowered in the list when sort button is pushed.
        if(check.isChecked()) {
            if(toBeRaised.contains(i)) {
                toBeRaised.remove(i);
            }
            toBeDropped.add(i);
            check.setChecked(false);
        } else {
            if (toBeDropped.contains(i)) {
                toBeDropped.remove(i);
            }
            toBeRaised.add(i);
            check.setChecked(true);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
