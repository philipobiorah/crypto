package com.example.qfen.crypto.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qfen.crypto.NetworkUtil.NetworkCall;
import com.example.qfen.crypto.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Qfen on 31-Oct-17.
 */

public class DisplayXchange extends AppCompatActivity  implements android.app.LoaderManager.LoaderCallbacks<HashMap<String,Double>>{

    private TextView mValue;
    private TextView mSign;
    private TextView mTocode;
    private CircleImageView mImage;
    private static String fromCode;
    private static String toCode;
    private HashMap<String,Double> hashMap;
    private  static String url;
    private boolean noInternetVisible = false;
    private boolean displayCardVisible = false;
    private static final int ID = 1;
    private RelativeLayout noInternet;
    private RelativeLayout displayCard;
    private HashMap<String,String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_xchange);
        noInternet = (RelativeLayout)findViewById(R.id.nointernet);
        displayCard = (RelativeLayout)findViewById(R.id.rela_card);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fromCode = bundle.getString("fromCode");
        toCode = bundle.getString("toCode");
        updateUI();
    }

    @Override
    public android.content.Loader<HashMap<String, Double>> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader<HashMap<String, Double>> loader, HashMap<String, Double> data) {
        hashMap = data;
        map = getCountrySymbol();
        double value = hashMap.get(toCode);
        mValue = (TextView)findViewById(R.id.val);
        mTocode = (TextView)findViewById(R.id.code);
        mSign = (TextView)findViewById(R.id.sign);
        mImage = (CircleImageView)findViewById(R.id.crypto);
        String valueTxt = String.valueOf(value);
        String symbol = map.get(toCode);


        if(noInternetVisible == true){
            noInternet.setVisibility(View.GONE);
            displayCard.setVisibility(View.VISIBLE);
            noInternetVisible = false;
            mValue.setText(valueTxt);
            mTocode.setText(toCode);
            mSign.setText(symbol);
            String img = fromCode.toLowerCase();
            if(fromCode == "BTC") mImage.setImageResource(R.drawable.btc);
            if(fromCode == "ETH") mImage.setImageResource(R.drawable.eth);
        }
        else {
            displayCard.setVisibility(View.VISIBLE);
            displayCardVisible = true;
            mValue.setText(valueTxt);
            mTocode.setText(toCode);
            mSign.setText(symbol);
            if(fromCode == "BTC") mImage.setImageResource(R.drawable.btc);
            if(fromCode == "ETH") mImage.setImageResource(R.drawable.eth);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<HashMap<String, Double>> loader) {
        hashMap = null;
    }

    private void updateUI(){

        boolean conn = NetworkCall.connectivity(this);
        if(conn == true){
            getLoaderManager().initLoader(ID,null,this);
        }
        else {
            if (displayCardVisible == true){
                displayCard.setVisibility(View.GONE);
                displayCardVisible = false;
            }
            noInternet.setVisibility(View.VISIBLE);
            noInternetVisible = true;
        }
    }



    private static class DataLoader extends android.content.AsyncTaskLoader<HashMap<String,Double>> {

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
            url = "https://min-api.cryptocompare.com/data/price?fsym="+fromCode+"&tsyms="+toCode;
            HashMap<String,Double> hashMap = NetworkCall.getResponse(url,toCode);
            Log.e(ConversionScreen.class.getSimpleName(),"LoadInBackground() method: Hashmap: "+hashMap);
            return hashMap;
        }
    }

    private HashMap<String,String> getCountrySymbol(){
        HashMap<String,String> map = new HashMap<>();
        String[] code = {"ARS","AUD","CAD","CNY","DKR","EGP","EUR","GHS","HKD",
        "ILS","INR","IDR","JPY","NGN","RUB","ZAR","CHF","SAR","GBP","USD"};
        String[] symbol = {"$","$","$","¥","k","£","€","¢","$","₪","₹","Rp","¥","₦","p","R","CHF","ريال","£","$"};

        for (int i=0; i<code.length; i++){
            for (int j=0; j<symbol.length; j++){
                if (j == i){
                    map.put(code[i],symbol[j]);
                }
            }
        }
        return map;
    }

}
