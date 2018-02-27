package com.example.groceryrpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class InventoryFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<MyItem> itemsList = new ArrayList<>();
    private List<String> cats = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public InventoryFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InventoryFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryFrag newInstance(String param1, String param2) {
        InventoryFrag fragment = new InventoryFrag();
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

    private void addItem() {
        Intent intent = new Intent(this.getContext(), AddItem.class);
        intent.putExtra("page", "Inventory");
        startActivity(intent);
    }

    public void editInventory(int position) {
        Intent intent = new Intent(this.getContext(), EditItem.class);
        MyItem chosen = itemsList.get(position);
        String name = chosen.getItemName();
        String quantity = chosen.getQuantity();
        String units = chosen.getUnits();
        String cat = cats.get(position);
        Integer unitsCurr = chosen.getCurrentUnits();
        Integer unitsPer = chosen.getUnitsPer();

        intent.putExtra("name", name);
        intent.putExtra("number", quantity);
        intent.putExtra("units", units);
        intent.putExtra("category", cat);
        intent.putExtra("currUnits", unitsCurr);
        intent.putExtra("unitsPer", unitsPer);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        //TabLayout tabs = view.findViewById(R.id.tabsLayout);
        Button addButton = view.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        SharedPreferences masterPref = this.getActivity().getSharedPreferences("juggleface", Context.MODE_PRIVATE);
        Set<String> listCats = masterPref.getAll().keySet();
        for(String i : listCats) {
            String cat = masterPref.getString(i, "");
            SharedPreferences tempPref = this.getActivity().getSharedPreferences(cat, Context.MODE_PRIVATE);
            Set<String> tempItemNames = tempPref.getAll().keySet();
            for(String j : tempItemNames) {
                Gson gson = new Gson();
                String json = tempPref.getString(j, "");
                itemsList.add(gson.fromJson(json, MyItem.class));
                cats.add(cat);
            }
        }

        RecyclerView rv = view.findViewById(R.id.recycler);
        rv.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                editInventory(position);
            }

            @Override
            public boolean onLongClick(View view, int position) {
                return true;
            }
        };
        InventoryAdapter mAdapter = new InventoryAdapter(listener);
        mAdapter.updateData(itemsList);
        rv.setAdapter(mAdapter);

        return view;
    }

    public void onFragmentInteraction(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
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
        void onFragmentInteraction(View view);
    }
}
