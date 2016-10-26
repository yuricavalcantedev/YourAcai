package com.yuri.youracai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.yuri.youracai.Activitys.FragmentVendaAdicionarItem;
import com.yuri.youracai.Activitys.FragmentVendaListaItens;

/**
 * Created by Yuri on 19/10/2016.
 */

public class PagerTabs extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerTabs(FragmentManager fm, int tabCount) {

        super(fm);

        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FragmentVendaListaItens fragmentVendaItem = new FragmentVendaListaItens();
                return fragmentVendaItem;
            case 1:
                FragmentVendaAdicionarItem fragmentVendaAdicional = new FragmentVendaAdicionarItem();
                return fragmentVendaAdicional;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }


}