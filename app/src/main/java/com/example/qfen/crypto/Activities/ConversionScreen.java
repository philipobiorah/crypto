package com.example.qfen.crypto.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.couchbase.lite.Database;
import com.example.qfen.crypto.Adapters.SpinnerAdapter1;
import com.example.qfen.crypto.Adapters.SpinnerAdapter2;
import com.example.qfen.crypto.Fragments.MyCardsFragment;
import com.example.qfen.crypto.Helper.DataHelper;
import com.example.qfen.crypto.Models.CryptoModel;
import com.example.qfen.crypto.Models.SpinnerModel1;
import com.example.qfen.crypto.Models.SpinnerModel2;
import com.example.qfen.crypto.NetworkUtil.NetworkCall;
import com.example.qfen.crypto.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ConversionScreen extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<HashMap<String,Double>> {

    private static  String crypto;
    private static String cryptoCode;
    private static String currency;
    private static int cryptoImg;
    ArrayList<SpinnerModel2> model;
    ArrayList<SpinnerModel1> model2;
    private static int currencyImg;
    private String to_country;
    private Button mConvert;
    private static final int ID = 1;
    private static HashMap<String,Double> hashMap;
    private TextView valText;
    private ProgressBar pbar;
    private boolean pbarVisible = false;
    @Override
    public android.content.Loader<HashMap<String, Double>> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader<HashMap<String, Double>> loader, HashMap<String, Double> data) {
        hashMap = data;
        Log.e(ConversionScreen.class.getSimpleName(),"onLoadFinished() method: Hashmap: "+hashMap);
        Double value = hashMap.get(currency);
        valText = (TextView) findViewById(R.id.value);
        if(pbarVisible == true){
            pbar.setVisibility(View.GONE);
            pbarVisible = false;
        }
        valText.setText(String.valueOf(value));
    }

    @Override
    public void onLoaderReset(android.content.Loader<HashMap<String, Double>> loader) {
        hashMap = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion_screen);

        ArrayList<SpinnerModel1> items1 = getItems1();
        final Spinner from_spinner = (Spinner)findViewById(R.id.from_spinner);
        from_spinner.setPrompt("Choose coin");
        from_spinner.setAdapter(new SpinnerAdapter1(this,R.layout.custom_spinner1, items1));

        // Todo implement a custom spinner adapter so as to handle more than one onItem selected at a give time

        from_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel1 selection = (SpinnerModel1) from_spinner.getSelectedItem();
                cryptoImg =selection.getmImage();
                cryptoCode = selection.getmFromCode();
                crypto = selection.getmFromCrypto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<SpinnerModel2> items2 = getItems2();
        final Spinner to_spinner = (Spinner)findViewById(R.id.to_spinner);
        to_spinner.setAdapter(new SpinnerAdapter2(this,R.layout.custom_spinner2,items2));

        // Todo implement a custom spinner adapter so as to handle more than one onItem selected at a give time
        to_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel2 selection2 = (SpinnerModel2)parent.getItemAtPosition(position);
                currencyImg = selection2.getmImage();
                currency = selection2.getmCode();
                to_country= selection2.getmCountry();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mConvert = (Button)findViewById(R.id.convert_btn);
        mConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertData();
                exit();
                return true;

            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateUI(){
        pbar = (ProgressBar)findViewById(R.id.converting);
        pbar.setVisibility(View.VISIBLE);
        pbarVisible = true;
        getLoaderManager().initLoader(ID,null,this);
    }

    private ArrayList<SpinnerModel2> getItems2(){
         model = new ArrayList<>();

        model.add(new SpinnerModel2(R.drawable.ars, "ARS", "Argentina Peso"));
        model.add(new SpinnerModel2(R.drawable.aud, "AUD", "Australia Dollar"));
        model.add(new SpinnerModel2(R.drawable.cad, "CAD", "Canada Dollar"));
        model.add(new SpinnerModel2(R.drawable.cny, "CNY", "China Yuan"));
        model.add(new SpinnerModel2(R.drawable.dkr, "DKR", "Denmark Krone"));
        model.add(new SpinnerModel2(R.drawable.egp, "EGP", "Egypt Pound"));
        model.add(new SpinnerModel2(R.drawable.eur, "EUR", "European EURO"));
        model.add(new SpinnerModel2(R.drawable.ghs, "GHS", "Ghana Cedi"));
        model.add(new SpinnerModel2(R.drawable.hkd, "HKD", "Honk Kong Dollar"));
        model.add(new SpinnerModel2(R.drawable.ils, "ILS", "Israel Shekel"));
        model.add(new SpinnerModel2(R.drawable.inr, "INR", "India Rupee"));
        model.add(new SpinnerModel2(R.drawable.idr, "IDR", "Indonesian Rupia"));
        model.add(new SpinnerModel2(R.drawable.jpy, "JPY", "Japan Yen"));
        model.add(new SpinnerModel2(R.drawable.ngn, "NGN", "Nigeria Naira"));
        model.add(new SpinnerModel2(R.drawable.rub, "RUB", "Russia Ruble"));
        model.add(new SpinnerModel2(R.drawable.zar, "ZAR", "South Africa Rand"));
        model.add(new SpinnerModel2(R.drawable.chf, "CHF", "Swiss Franc"));
        model.add(new SpinnerModel2(R.drawable.sar, "SAR", "Saudi Arabia Riyal"));
        model.add(new SpinnerModel2(R.drawable.gbp, "GBP", "Great Britain Pounda"));
        model.add(new SpinnerModel2(R.drawable.usd, "USD", "United States Dollar"));

        return model;
    }

    private ArrayList<SpinnerModel1> getItems1(){
        model2 = new ArrayList<>();

        model2.add(new SpinnerModel1(R.drawable.btc, "BTC","Bitcoin"));
        model2.add(new SpinnerModel1(R.drawable.eth, "ETH","Etherum"));

        return model2;
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
            String url = "https://min-api.cryptocompare.com/data/price?fsym="+cryptoCode+"&tsyms="+currency;
            HashMap<String,Double> hashMap = NetworkCall.getResponse(url,currency);
            Log.e(ConversionScreen.class.getSimpleName(),"LoadInBackground() method: Hashmap: "+hashMap);
            return hashMap;
        }
    }

    private void insertData(){
        CryptoModel cryptoModel = new CryptoModel();
        cryptoModel.setmFromCode(cryptoCode);
        cryptoModel.setmFromCrypto(crypto);
        cryptoModel.setmFromImage(cryptoImg);
        cryptoModel.setmToCode(currency);
        cryptoModel.setmToCountry(to_country);
        cryptoModel.setmToImage(currencyImg);

        Database database = DataHelper.getDatabase(getApplicationContext(), DataHelper.CRYPTO_DATABASE);
        cryptoModel.saveToDatabase(ConversionScreen.this,database);
    }
    private void exit(){
        Intent intent = new Intent(this,MyCardsFragment.class);
        NavUtils.navigateUpTo(this,intent);

    }



}
