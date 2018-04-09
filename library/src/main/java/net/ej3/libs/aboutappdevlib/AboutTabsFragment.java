package net.ej3.libs.aboutappdevlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import net.ej3.libs.aboutappdevlib.databinding.AboutTabsFragmentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180409
 */
@SuppressWarnings("unused")
public class AboutTabsFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    private static final String ARGUMENT_ID = "argument_id";
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    @SuppressLint("UseSparseArrays")
    private static final HashMap<Integer,Config> store = new HashMap<>();
    private Config config;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutTabsFragmentBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Builder
    //
    public static final class Builder {
        Context ctx;
        Config mConfig;

        public Builder(@NonNull final Context ctx) {
            this.ctx = ctx;
            mConfig = new Config();
        }

        public Builder withId(int id) {
            mConfig.id = id;
            return this;
        }

        public Builder withTabsMode(int mode) {
            mConfig.tabsMode = mode;
            return this;
        }

        public Builder withTabsBackgroundColor(@ColorInt int tabsBackgroundColor) {
            mConfig.tabsBackgroundColor = tabsBackgroundColor;
            return this;
        }

        public Builder withTabsBackgroundColorRes(@ColorRes int tabsBackgroundColorRes) {
            mConfig.tabsBackgroundColor = ContextCompat.getColor(ctx,tabsBackgroundColorRes);
            return this;
        }

        public Builder withTabsNormalColor(@ColorInt int tabsNormalColor) {
            mConfig.tabsNormalColor = tabsNormalColor;
            return this;
        }

        public Builder withTabsNormalColorRes(@ColorRes int tabsNormalColorRes) {
            mConfig.tabsNormalColor = ContextCompat.getColor(ctx,tabsNormalColorRes);
            return this;
        }

        public Builder withTabsSelectedColor(@ColorInt int tabsSelectedColor) {
            mConfig.tabsSelectedColor = tabsSelectedColor;
            return this;
        }

        public Builder withTabsSelectedColorRes(@ColorRes int tabsSelectedColorRes) {
            mConfig.tabsSelectedColor = ContextCompat.getColor(ctx,tabsSelectedColorRes);
            return this;
        }

        public Builder withTabsIndicatorColor(@ColorInt int tabsIndicatorColor) {
            mConfig.tabsIndicatorColor = tabsIndicatorColor;
            return this;
        }

        public Builder withTabsIndicatorColorRes(@ColorRes int tabsIndicatorColorRes) {
            mConfig.tabsIndicatorColor = ContextCompat.getColor(ctx,tabsIndicatorColorRes);
            return this;
        }

        public Builder addPage(@NonNull String tabTitle,@NonNull Fragment fragment) {
            mConfig.tabHeader.add(tabTitle);
            mConfig.pages.add(fragment);
            return this;
        }

        public Builder addPage(@StringRes int tabTitleRes,@NonNull Fragment fragment) {
            mConfig.tabHeader.add(ctx.getString(tabTitleRes));
            mConfig.pages.add(fragment);
            return this;
        }

        public Builder addPageWithIcon(@DrawableRes int tabIcon,@NonNull Fragment fragment) {
            mConfig.tabHeader.add(tabIcon);
            mConfig.pages.add(fragment);
            return this;
        }

        public AboutTabsFragment build() {
            AboutTabsFragment aboutTabsFragment = new AboutTabsFragment();
            if( mConfig.id == null ) mConfig.id = aboutTabsFragment.hashCode();
            store.put(mConfig.id,mConfig);
            Bundle b = new Bundle();
            b.putInt(ARGUMENT_ID,mConfig.id);
            aboutTabsFragment.setArguments(b);
            return aboutTabsFragment;
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.about_tabs_fragment,container,false);
        config = getConfig();
        if( config != null ) {
            buildTabs();
            binding.pager.setAdapter(new AboutPagerAdapter(getChildFragmentManager(),config.pages));
            binding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
        }
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
    @Nullable
    private Config getConfig() {
        final Bundle b = getArguments();
        if( b == null ) {
            Logger.e("Error: No config data for AboutTabsFragment");
        } else {
            final int id = b.getInt(ARGUMENT_ID,Integer.MIN_VALUE);
            final Config c = store.get(id);
            if( c == null ) {
                Logger.e("Error: No config data for AboutTabsFragment with id:",id);
            } else {
                return c;
            }
        }
        return null;
    }

    private void buildTabs() {
        //Style
        binding.tabs.setTabMode(config.tabsMode);
        binding.tabs.setBackgroundColor(config.tabsBackgroundColor);
        binding.tabs.setSelectedTabIndicatorColor(config.tabsIndicatorColor);
        binding.tabs.setTabTextColors(config.tabsNormalColor,config.tabsSelectedColor);

        //Titles-icons
        for(Object o : config.tabHeader) {
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


    //--------------------------------------------------------------------------
    //region Config data
    //
    private static final class Config {
        Integer id;
        int tabsMode = TabLayout.MODE_FIXED;
        @ColorInt int tabsBackgroundColor = 0xff607d8b;
        @ColorInt int tabsNormalColor = 0x99ffffff;
        @ColorInt int tabsSelectedColor = 0xffffffff;
        @ColorInt int tabsIndicatorColor = 0xffffffff;
        List<Object> tabHeader = new ArrayList<>();
        List<Fragment> pages = new ArrayList<>();
    }
    //endregion

}
