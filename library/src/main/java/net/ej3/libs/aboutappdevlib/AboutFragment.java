package net.ej3.libs.aboutappdevlib;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ej3.libs.aboutappdevlib.databinding.AboutFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180313
 */
@SuppressWarnings("unused")
public class AboutFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    private int tabsMode;
    @ColorInt private int tabsBackgroundColor;
    @ColorInt private int tabsNormalColor;
    @ColorInt private int tabsSelectedColor;
    @ColorInt private int tabsIndicatorColor;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutFragmentBinding binding;
    private List<Object> tabHeader;
    private List<Fragment> pages;
    //endregion


    //--------------------------------------------------------------------------
    //region Builder
    //
    public static final class Builder {
        Context ctx;
        int mTabsMode = TabLayout.MODE_FIXED;
        @ColorInt int mTabsBackgroundColor = 0xff607d8b;
        @ColorInt int mTabsNormalColor = 0x99ffffff;
        @ColorInt int mTabsSelectedColor = 0xffffffff;
        @ColorInt int mTabsIndicatorColor = 0xffffffff;
        List<Object> mTabHeader = new ArrayList<>();
        List<Fragment> mPages = new ArrayList<>();

        public Builder(@NonNull final Context ctx) {
            this.ctx = ctx;
        }

        public Builder withTabsMode(int mode) {
            mTabsMode = mode;
            return this;
        }

        public Builder withTabsBackgroundColor(@ColorInt int tabsBackgroundColor) {
            mTabsBackgroundColor = tabsBackgroundColor;
            return this;
        }

        public Builder withTabsBackgroundColorRes(@ColorRes int tabsBackgroundColorRes) {
            mTabsBackgroundColor = ContextCompat.getColor(ctx,tabsBackgroundColorRes);
            return this;
        }

        public Builder withTabsNormalColor(@ColorInt int tabsNormalColor) {
            mTabsNormalColor = tabsNormalColor;
            return this;
        }

        public Builder withTabsNormalColorRes(@ColorRes int tabsNormalColorRes) {
            mTabsNormalColor = ContextCompat.getColor(ctx,tabsNormalColorRes);
            return this;
        }

        public Builder withTabsSelectedColor(@ColorInt int tabsSelectedColor) {
            mTabsSelectedColor = tabsSelectedColor;
            return this;
        }

        public Builder withTabsSelectedColorRes(@ColorRes int tabsSelectedColorRes) {
            mTabsSelectedColor = ContextCompat.getColor(ctx,tabsSelectedColorRes);
            return this;
        }

        public Builder withTabsIndicatorColor(@ColorInt int tabsIndicatorColor) {
            mTabsIndicatorColor = tabsIndicatorColor;
            return this;
        }

        public Builder withTabsIndicatorColorRes(@ColorRes int tabsIndicatorColorRes) {
            mTabsIndicatorColor = ContextCompat.getColor(ctx,tabsIndicatorColorRes);
            return this;
        }

        public Builder addPage(@NonNull String tabTitle,@NonNull Fragment fragment) {
            mTabHeader.add(tabTitle);
            mPages.add(fragment);
            return this;
        }

        public Builder addPage(@DrawableRes int tabIcon,@NonNull Fragment fragment) {
            mTabHeader.add(tabIcon);
            mPages.add(fragment);
            return this;
        }

        public AboutFragment build() {
            AboutFragment aboutFragment = new AboutFragment();
            aboutFragment.tabsMode = mTabsMode;
            aboutFragment.tabsBackgroundColor = mTabsBackgroundColor;
            aboutFragment.tabsNormalColor = mTabsNormalColor;
            aboutFragment.tabsIndicatorColor = mTabsIndicatorColor;
            aboutFragment.tabsSelectedColor = mTabsSelectedColor;
            aboutFragment.tabHeader = mTabHeader;
            aboutFragment.pages = mPages;
            return aboutFragment;
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.about_fragment,container,false);
        buildTabs();
        binding.pager.setAdapter(new AboutPagerAdapter(getChildFragmentManager(),pages));
        binding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        return binding.getRoot();
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Toolbar Options Menu
    //
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private void buildTabs() {
        //Style
        binding.tabs.setTabMode(tabsMode);
        binding.tabs.setBackgroundColor(tabsBackgroundColor);
        binding.tabs.setSelectedTabIndicatorColor(tabsIndicatorColor);
        binding.tabs.setTabTextColors(tabsNormalColor,tabsSelectedColor);

        //Titles-icons
        for(Object o : tabHeader) {
            if( o instanceof String ) {
                binding.tabs.addTab(binding.tabs.newTab().setText((String)o));
            } else if( o instanceof Integer ) {
                binding.tabs.addTab(binding.tabs.newTab().setIcon((int)o));
            }
        }

        //Actions
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.pager.setCurrentItem(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
    //endregion

}

