package com.example.qfen.crypto.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.qfen.crypto.Activities.ConversionScreen;
import com.example.qfen.crypto.Adapters.CryptoAdapter;
import com.example.qfen.crypto.Helper.DataHelper;
import com.example.qfen.crypto.Models.CryptoModel;
import com.example.qfen.crypto.R;

import java.util.ArrayList;

public class MyCardsFragment extends android.support.v4.app.Fragment {

    private View rootView;
    private static ArrayList<CryptoModel> myList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RelativeLayout emptyCards;
    private boolean noCardsVisible = false;
    CryptoAdapter adapter;
    Database database;
    private  FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.my_cards_frag, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        database = DataHelper.getDatabase(getActivity().getApplicationContext(), DataHelper.CRYPTO_DATABASE);
        emptyCards = (RelativeLayout) rootView.findViewById(R.id.empty_cards);
        getAllConversions();
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConversionScreen.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getAllConversions() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (database == null)
            return;

        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS); //ALL_DOCS by id, BY_SEQUENCE by last modified

        try {
            QueryEnumerator result = query.run();
            myList = new ArrayList<>();

            for (; result.hasNext(); ) {
                QueryRow row = result.next();
                CryptoModel customer = CryptoModel.fromDictionary(row.getDocument().getProperties());
                myList.add(customer);
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Get customers info failed", Toast.LENGTH_SHORT).show();
        }

        adapter = new CryptoAdapter(getActivity(),myList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllConversions();
    }
}


