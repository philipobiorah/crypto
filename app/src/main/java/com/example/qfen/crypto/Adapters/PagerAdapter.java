package com.example.qfen.crypto.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.qfen.crypto.Fragments.BtcToEthFrag;
import com.example.qfen.crypto.Fragments.MyCardsFragment;

/**
 * Created by Qfen on 21-Oct-17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new BtcToEthFrag();
        }
        else {
            return new MyCardsFragment();
        }
    }
}
