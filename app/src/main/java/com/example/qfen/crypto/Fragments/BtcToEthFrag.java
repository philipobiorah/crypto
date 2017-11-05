package com.example.qfen.crypto.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qfen.crypto.NetworkUtil.NetworkCall;
import com.example.qfen.crypto.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BtcToEthFrag extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String,Double>> {

    private static String url = "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=ETH";
    private final static String CURRENCY = "ETH";
    private HashMap<String,Double> hashMap = new HashMap<>();
    private final int ID = 1;
    private RelativeLayout noData;
    private RelativeLayout btcEthCard;
    private View rootView;
    private TextView ethView;
    private TextView time;
    private TextView date;
    FloatingActionButton fab ;
    private ProgressBar progressBar;
    private  boolean btcCardIsVisible = false;
    private boolean noInternetVisible = false;
    HashMap<String,String> calendar;

    private ItemTouchHelper mItemTouchHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.btc_eth_frag, container, false);
        fab =  (FloatingActionButton)rootView.findViewById(R.id.fab_1);
        btcEthCard = (RelativeLayout) rootView.findViewById(R.id.btc_eth_card);
        noData = (RelativeLayout)rootView.findViewById(R.id.no_internet);
        date = (TextView)rootView.findViewById(R.id.real_date);
        time = (TextView)rootView.findViewById(R.id.real_time);
        updateUI();
        fab.setImageResource(R.drawable.refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });
        return rootView;
    }

    private void updateUI(){
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        boolean netOn = NetworkCall.connectivity(getActivity());
       // boolean net = NetworkCall.isNetworkAvailable(getActivity());
        Log.e(BtcToEthFrag.class.getSimpleName(),"AVAILABLE NETWORK: "+netOn);

        if(btcCardIsVisible){
            if(netOn){
                getLoaderManager().restartLoader(ID,null,this);
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),R.string.updating_btc,Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),R.string.no_internet,Toast.LENGTH_LONG).show();
            }

        }
        else {
            if(netOn == true){
                if(noInternetVisible == true){
                    noData.setVisibility(View.GONE);
                    noInternetVisible = false;
                }
                progressBar.setVisibility(View.VISIBLE);
                getLoaderManager().initLoader(ID,null,this);
                Toast.makeText(getActivity(),R.string.on_start,Toast.LENGTH_LONG).show();
            }
            else {
                progressBar.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                noInternetVisible = true;
            }
        }
    }

    @Override
    public android.support.v4.content.Loader<HashMap<String, Double>> onCreateLoader(int id, Bundle args) {
        return new DataLoader(getActivity());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<HashMap<String, Double>> loader, HashMap<String,Double> data) {
        ethView = (TextView)rootView.findViewById(R.id.to_crp);
        hashMap = data;
        double val = hashMap.get(CURRENCY);
        String value = String.valueOf(val);
        calendar = getDate();
        String tme = calendar.get("Time");
        String dte = calendar.get("Date");

        if(btcCardIsVisible == true){
            progressBar.setVisibility(View.GONE);
            ethView.setText(value);
            date.setText(dte);
            time.setText(tme);
            Toast.makeText(getActivity(),R.string.updated_btc,Toast.LENGTH_LONG);
        }
        else {
            progressBar.setVisibility(View.GONE);
            if(noInternetVisible == true){
                noData.setVisibility(View.GONE);
                noInternetVisible = false;
            }
            btcEthCard.setVisibility(View.VISIBLE);
            ethView.setText(value);
            date.setText(dte);
            time.setText(tme);
            btcCardIsVisible = true;

        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<HashMap<String, Double>> loader) {
        hashMap = null;
    }






    private static class DataLoader extends AsyncTaskLoader<HashMap<String,Double>> {

        public DataLoader(Context context){
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public HashMap<String, Double> loadInBackground() {
            HashMap<String,Double> hashMap = NetworkCall.getResponse(url,CURRENCY);
            Log.e(BtcToEthFrag.class.getSimpleName(),"LoadInBackground() method: Hashmap: "+hashMap);
            return hashMap;
        }
    }

    private HashMap<String,String> getDate(){

        HashMap<String,String> myTime = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        String realDate = date.format(calendar.getTime());
        String realTime = time.format(calendar.getTime());
        myTime.put("Date",realDate);
        myTime.put("Time",realTime);

        return myTime;
    }
}
