package com.example.qfen.crypto.Models;

public class SpinnerModel2 {

    private int mImage;
    private String mCode;
    private String mCountry;

    public SpinnerModel2(int image, String code, String country){
        this.mImage = image;
        this.mCode = code;
        this.mCountry = country;
    }

    public int getmImage(){
        return mImage;
    }
    public String getmCode(){
        return mCode;
    }
    public String getmCountry(){
        return mCountry;
    }
}
