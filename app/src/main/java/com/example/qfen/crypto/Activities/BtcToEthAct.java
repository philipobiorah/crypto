package com.example.qfen.crypto.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.example.qfen.crypto.Fragments.BtcToEthFrag;
import com.example.qfen.crypto.R;

/**
 * Created by Qfen on 21-Oct-17.
 */

public class BtcToEthAct extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btc_eth_act);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setImageResource(R.drawable.refresh);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new BtcToEthFrag())
                .commit();
    }
}
