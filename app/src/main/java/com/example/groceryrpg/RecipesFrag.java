package com.example.groceryrpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RecipesFrag extends Fragment {
    // This is the fragment for the recipes! It'll look prettier later (it _all_ will),
    // but this is where the recipes are displayed, and can be added to via the helpful
    // little button at the top. Clicking on a recipe will show it in more detail at some
    // time in the future, and editing and deleting will be enabled as well.

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecipesFrag() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecipesFrag newInstance(String param1, String param2) {
        RecipesFrag fragment = new RecipesFrag();
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
        // Inflate the layout for this fragment, initialize add recipe button
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        Button addButton = view.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe();
            }
        });

        // Get Recipes info from storage, currently SharedPreferences
        SharedPreferences recipesPref = this.getActivity().getSharedPreferences("AllOfMyRecipes,Yo", Context.MODE_PRIVATE);
        Set<String> keys = recipesPref.getAll().keySet();
        final List<MyRecipe> recipes = new ArrayList<>(); // TODO: update RecipesAdapter to better represent recipe data
        final List<String> recipeNames = new ArrayList<>(); // ...until then, we'll just use the names
        for(String i : keys) {
            Gson gson = new Gson();
            String json = recipesPref.getString(i, "");
            MyRecipe temp = gson.fromJson(json, MyRecipe.class);
            recipes.add(temp); recipeNames.add(temp.getRecipeName());
        }

        // ...and some dummy values, just to test;
        recipeNames.add("PB & J"); recipeNames.add("Beef Stew"); recipeNames.add("Cheesy Omelet");

        // Set up RecyclerView
        RecyclerView rv = view.findViewById(R.id.recipeRecycler);
        rv.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), recipeNames.get(position), Toast.LENGTH_SHORT).show();
                MyRecipe recipe = recipes.get(position);
                Intent intent = new Intent(getContext(), EditRecipe.class);
                intent.putExtra("recipeName", recipe.getRecipeName());
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View view, int position) {
                Toast.makeText(getContext(), recipeNames.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        RecipesAdapter adapter = new RecipesAdapter(listener);
        adapter.updateData(recipeNames);
        rv.setAdapter(adapter);

        return view;
    }

    private void addRecipe() {
        Intent intent = new Intent(this.getContext(), AddRecipe.class);
        startActivity(intent);
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
        void onFragmentInteraction(Uri uri);
    }
}
