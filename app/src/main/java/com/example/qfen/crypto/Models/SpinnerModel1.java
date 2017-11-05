package com.example.qfen.crypto.Models;

/**
 * Created by Qfen on 26-Oct-17.
 */

public class SpinnerModel1 {

    private int mImage;
    private String mFromCode;
    private String mFromCrypto;

    public SpinnerModel1(int image, String fromCode, String fromCrypto){
        mImage = image;
        mFromCode = fromCode;
        mFromCrypto = fromCrypto;
    }

    public int getmImage(){
        return mImage;
    }
    public String getmFromCode(){
        return mFromCode;
    }
    public String getmFromCrypto(){
        return mFromCrypto;
    }
}
