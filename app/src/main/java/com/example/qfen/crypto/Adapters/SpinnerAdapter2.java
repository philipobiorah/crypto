package com.example.qfen.crypto.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qfen.crypto.Models.SpinnerModel2;
import com.example.qfen.crypto.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;



public class SpinnerAdapter2 extends ArrayAdapter<SpinnerModel2> {

    private Context mContext;
    private ArrayList<SpinnerModel2> mArray = new ArrayList<>();

    public SpinnerAdapter2(Context ctx, int txtViewResourceId, ArrayList<SpinnerModel2> array) {
        super(ctx, txtViewResourceId, array);
        mContext = ctx;
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
        SpinnerModel2 item = mArray.get(position);
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner2,container,false);
        CircleImageView image = (CircleImageView)rootview.findViewById(R.id.spinner_image);
        image.setImageResource(item.getmImage());

        TextView code = (TextView)rootview.findViewById(R.id.spinner_code);
        code.setText(item.getmCode());

        TextView country = (TextView)rootview.findViewById(R.id.spinner_country);
        country.setText(item.getmCountry());

        return rootview;
    }

    public SpinnerModel2 getItem(int position){
        return mArray.get(position);
    }

}
