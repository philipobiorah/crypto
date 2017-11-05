package com.example.qfen.crypto.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.internal.database.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class CryptoModel extends BaseModel implements Parcelable{

    private int mFromImage;
    private String id;
    private String mFromCrypto;
    private String mFromCode;
    private int mToImage;
    private String mToCountry;
    private String mFromCountry;
    private String mToCode;

    public CryptoModel() {
    }

    public static CryptoModel fromDictionary(Object dictionary){
        return fromDictionary(dictionary,CryptoModel.class);
    }

    protected CryptoModel(Parcel in) {
        id= in.readString();
        mToImage = in.readInt();
        mFromImage = in.readInt();
        mFromCrypto = in.readString();
        mFromCode = in.readString();
        mToCountry = in.readString();
        mFromCountry = in.readString();
        mToCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFromCrypto);
        dest.writeString(mFromCode);
        dest.writeString(mToCountry);
        dest.writeString(mToCode);
        dest.writeString(id);
        dest.writeString(mFromCountry);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CryptoModel> CREATOR = new Creator<CryptoModel>() {
        @Override
        public CryptoModel createFromParcel(Parcel in) {
            return new CryptoModel(in);
        }

        @Override
        public CryptoModel[] newArray(int size) {
            return new CryptoModel[size];
        }
    };


    public int getmFromImage() {
        return mFromImage;
    }

    public void setmFromImage(int mFromImage) {
        this.mFromImage = mFromImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmFromCrypto() {
        return mFromCrypto;
    }

    public void setmFromCrypto(String mFromCrypto) {
        this.mFromCrypto = mFromCrypto;
    }

    public String getmFromCode() {
        return mFromCode;
    }

    public void setmFromCode(String mFromCode) {
        this.mFromCode = mFromCode;
    }

    public int getmToImage() {
        return mToImage;
    }

    public void setmToImage(int mToImage) {
        this.mToImage = mToImage;
    }

    public String getmToCountry() {
        return mToCountry;
    }

    public void setmToCountry(String mToCountry) {
        this.mToCountry = mToCountry;
    }

    public String getmToCode() {
        return mToCode;
    }

    public void setmToCode(String mToCode) {
        this.mToCode = mToCode;
    }

    public String getmFromCountry() {
        return mFromCountry;
    }

    public void setmFromCountry(String mFromCountry) {
        this.mFromCountry = mFromCountry;
    }

    public void saveToDatabase(final AppCompatActivity activity, final Database database){

        if (database == null)
        {
            Toast.makeText(activity, "Cannot to save to store. Database unavailable.", Toast.LENGTH_SHORT).show();
            return;
        }

        Document CryptoDocument;
        Map<String, Object> properties;

        if (TextUtils.isEmpty(this.getId())){
            //new style
            CryptoDocument  = database.createDocument();
            this.setId(CryptoDocument.getId());
            properties = this.toDictionary();
        }
        else{
            CryptoDocument = database.getDocument(this.getId());
            properties = new HashMap<>();
            properties.putAll(CryptoDocument.getProperties());
            properties.putAll(this.toDictionary());
        }

        try {
            CryptoDocument.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to save to store. Fatal error occurred.", Toast.LENGTH_SHORT).show();
        }
    }
}
