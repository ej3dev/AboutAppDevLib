package net.ej3.libs.aboutappdevlib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180305
 */
public class AboutPagerAdapter extends FragmentPagerAdapter {

    //--------------------------------------------------------------------------
    //region Constants
    //
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    private List<Fragment> pages;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public AboutPagerAdapter(FragmentManager fm,List<Fragment> pages) {
        super(fm);
        this.pages = pages;
    }
    //endregion


    //--------------------------------------------------------------------------
    //region FragmentPagerAdapter methods
    //
    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }
    //endregion

}
