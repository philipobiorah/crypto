package com.example.qfen.crypto.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.example.qfen.crypto.Activities.DisplayXchange;
import com.example.qfen.crypto.Helper.DataHelper;
import com.example.qfen.crypto.Models.CryptoModel;
import com.example.qfen.crypto.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoHolder>{

    private Context mContext;
    ArrayList<CryptoModel>  array= new ArrayList<>();
    private String mFromCode;
    private String mToCode;
    private int id ;


    public class CryptoHolder extends RecyclerView.ViewHolder{

        private CircleImageView mFromImage, mToImage;
        private TextView mFromText, mToText, mFromCode, mToCode;
        private ImageButton delete;
        private CardView mCardView;

        public CryptoHolder(final View itemView){
            super(itemView);
            mFromImage = (CircleImageView)itemView.findViewById(R.id.first_flag);
            mToImage = (CircleImageView)itemView.findViewById(R.id.second_flag);
            mFromText = (TextView)itemView.findViewById(R.id.first_country);
            mToText = (TextView)itemView.findViewById(R.id.second_country);
            mFromCode = (TextView)itemView.findViewById(R.id.first_code);
            mToCode = (TextView)itemView.findViewById(R.id.second_code);
            delete = (ImageButton)itemView.findViewById(R.id.delete1);
            mCardView = (CardView)itemView.findViewById(R.id.cardView);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if (delete.getTag() == null){
                     final Toast notice = Toast.makeText(mContext,"Tap again to permanently delete",Toast.LENGTH_SHORT);
                    notice.show();
                     delete.setImageTintList(ColorStateList.valueOf(Color.RED));
                     delete.setTag("imgActionDelete");

                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             delete.setImageTintList(ColorStateList.valueOf(Color.BLACK));
                             notice.cancel();
                             delete.setTag(null);
                         }
                     },2000);
                 }else {
                     Database database = DataHelper.getDatabase(mContext,DataHelper.CRYPTO_DATABASE);
                     if (database !=null){
                         Document document = database.getDocument(array.get(getAdapterPosition()).getId());
                         try {
                             document.delete();
                             CryptoAdapter.this.array.remove(getAdapterPosition());
                             CryptoAdapter.this.notifyDataSetChanged();
                             Toast.makeText(mContext,"Deleted",Toast.LENGTH_SHORT).show();
                         } catch (CouchbaseLiteException e) {
                             e.printStackTrace();
                             Toast.makeText(mContext,"Failed to remove ",Toast.LENGTH_SHORT).show();
                         }
                     }
                 }
                }
            });
        }
    }
    public CryptoAdapter(Context context, ArrayList<CryptoModel> myList){
        mContext = context;
        array = myList;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    @Override
    public CryptoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        return new CryptoHolder(view);
    }

    @Override
    public void onBindViewHolder(CryptoHolder holder, int position) {
        final CryptoModel item = array.get(position);
        holder.mFromImage.setImageResource(item.getmFromImage());
        holder.mToImage.setImageResource(item.getmToImage());
        holder.mToImage.setTag(item.getmToImage());
        holder.mFromImage.setTag(item.getmFromImage());
        holder.mFromText.setText(item.getmFromCountry());
        holder.mToText.setText(item.getmToCountry());
        holder.mFromCode.setText(item.getmFromCode());
        holder.mToCode.setText(item.getmToCode());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFromCode = item.getmFromCode();
                mToCode = item.getmToCode();
                Bundle bundle = new Bundle();
                bundle.putString("fromCode",mFromCode);
                bundle.putString("toCode",mToCode);
                Intent intent = new Intent(mContext,DisplayXchange.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    public CryptoModel getItem(int position){

        return array.get(position);
    }

}
