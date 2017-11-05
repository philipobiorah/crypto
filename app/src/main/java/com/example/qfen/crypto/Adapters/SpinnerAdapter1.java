package com.example.qfen.crypto.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qfen.crypto.Models.SpinnerModel1;
import com.example.qfen.crypto.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Qfen on 26-Oct-17.
 */

public class SpinnerAdapter1 extends ArrayAdapter<SpinnerModel1> {

    private Context mContext;
    private ArrayList<SpinnerModel1> mArray = new ArrayList<>();

    public SpinnerAdapter1(Context ctx, int txtViewResourceId, ArrayList<SpinnerModel1> array) {
        super(ctx,txtViewResourceId,array);
        this.mContext = ctx;
        this.mArray = array;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    private View getCustomView(int position, View view, ViewGroup container){
        SpinnerModel1 item = mArray.get(position);
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner1,container,false);
        CircleImageView image = (CircleImageView)rootview.findViewById(R.id.spinner_image);
        image.setImageResource(item.getmImage());

        TextView code = (TextView)rootview.findViewById(R.id.spinner_code);
        code.setText(item.getmFromCode());

        TextView crypto = (TextView)rootview.findViewById(R.id.spinner_crypto);
        crypto.setText(item.getmFromCrypto());

        return rootview;
    }

    public SpinnerModel1 getItem(int position){
        return mArray.get(position);
    }
}
