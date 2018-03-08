package net.ej3.libs.aboutappdevlib;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.ej3.libs.aboutappdevlib.databinding.AboutDevFragmentBinding;
import net.ej3.libs.aboutappdevlib.model.App;
import net.ej3.libs.aboutappdevlib.model.Dev;
import net.ej3.libs.aboutappdevlib.util.Util;
import net.ej3.libs.aboutappdevlib.view.AppCardView;
import net.ej3.libs.aboutappdevlib.view.DevCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings({"unused","SameParameterValue"})
public class AboutDevFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    @ColorInt protected static final int DEFAULT_BACKGROUND_COLOR      = 0xffeceff1;
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_PRIMARY    = 0xdd000000; //0xdd ~ 87%
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_SECONDARY  = 0x88000000; //0x88 ~ 54%
    @ColorInt protected static final int DEFAULT_SECTION_TITLE_COLOR   = 0xff546e7a;
    @ColorInt protected static final int DEFAULT_SECTION_DIVIDER_COLOR = 0x22546e7a; //0x22 ~ 13%
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    @ColorInt private int backgroundColor;
    @ColorInt private int primaryTextColor;
    @ColorInt private int secondaryTextColor;
    @ColorInt private int sectionTitleColor;
    @ColorInt private int sectionDividerColor;

    @DrawableRes private int logoRes;
    @Nullable private String author;
    @Nullable private String info;
    @Nullable private String devsTitle;
    @Nullable private String appsTitle;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutDevFragmentBinding binding;
    private List<View> actions;
    private List<Dev> devs;
    private List<App> apps;
    //endregion


    //--------------------------------------------------------------------------
    //region Builder
    //
    public static final class Builder {
        @ColorInt int mBackgroundColor     = DEFAULT_BACKGROUND_COLOR;
        @ColorInt int mPrimaryTextColor    = DEFAULT_TEXT_COLOR_PRIMARY;
        @ColorInt int mSecondaryTextColor  = DEFAULT_TEXT_COLOR_SECONDARY;
        @ColorInt int mSectionTitleColor   = DEFAULT_SECTION_TITLE_COLOR;
        @ColorInt int mSectionDividerColor = DEFAULT_SECTION_DIVIDER_COLOR;

        @DrawableRes int mLogoRes = -1;
        @Nullable String mAuthor;
        @Nullable String mInfo;
        @Nullable String mDevsTitle;
        @Nullable String mAppsTitle;
        List<View> mActions = new ArrayList<>();
        List<Dev> mDevs = new ArrayList<>();
        List<App> mApps = new ArrayList<>();

        public Builder() {
            //Empty
        }

        public Builder withBackgroundColor(@ColorInt int backgroundColor) {
            mBackgroundColor = backgroundColor;
            return this;
        }

        public Builder withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor) {
            mPrimaryTextColor = primaryColor;
            mSecondaryTextColor = secondaryColor;
            mSectionTitleColor = sectionColor;
            mSectionDividerColor = (0x22000000 | (sectionColor & 0xffffff));
            return this;
        }

        public Builder withLogo(@DrawableRes int logoRes) {
            mLogoRes = logoRes;
            return this;
        }

        public Builder withAuthor(@Nullable String author) {
            mAuthor = author;
            return this;
        }

        public Builder withInfo(@Nullable String info) {
            mInfo = info;
            return this;
        }

        public Builder withActions(View... actions) {
            mActions.addAll(Arrays.asList(actions));
            return this;
        }

        public Builder withDevs(@Nullable String title,Dev... devs) {
            mDevsTitle = title;
            mDevs.addAll(Arrays.asList(devs));
            return this;
        }

        public Builder withApps(@Nullable String title,App... apps) {
            mAppsTitle = title;
            mApps.addAll(Arrays.asList(apps));
            return this;
        }

        public AboutDevFragment build() {
            AboutDevFragment aboutDevFragment = new AboutDevFragment();
            aboutDevFragment.backgroundColor = mBackgroundColor;
            aboutDevFragment.primaryTextColor = mPrimaryTextColor;
            aboutDevFragment.secondaryTextColor = mSecondaryTextColor;
            aboutDevFragment.sectionTitleColor = mSectionTitleColor;
            aboutDevFragment.sectionDividerColor = mSectionDividerColor;

            aboutDevFragment.logoRes = mLogoRes;
            aboutDevFragment.author = mAuthor;
            aboutDevFragment.info = mInfo;
            aboutDevFragment.devsTitle = mDevsTitle;
            aboutDevFragment.appsTitle = mAppsTitle;
            aboutDevFragment.actions = mActions;
            aboutDevFragment.devs = mDevs;
            aboutDevFragment.apps = mApps;

            return aboutDevFragment;
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.about_dev_fragment,container,false);
        setData();
        addActions();
        addDevs(inflater.getContext());
        addApps(inflater.getContext());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.layActions.removeAllViews();
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private void setData() {
        binding.setBackgroundColor(backgroundColor);
        binding.setPrimaryTextColor(primaryTextColor);
        binding.setSecondaryTextColor(secondaryTextColor);
        binding.setSectionTitleColor(sectionTitleColor);
        binding.setSectionDividerColor(sectionDividerColor);

        binding.setLogo(logoRes);
        binding.setAuthor(Util.toHtml(author));
        binding.setInfo(Util.toHtml(info));
        binding.setDevsVisible(devs.size() > 0);
        binding.setAppsVisible(apps.size() > 0);
        binding.setDevsTitle(Util.toHtml(devsTitle));
        binding.setAppsTitle(Util.toHtml(appsTitle));
    }

    private void addActions() {
        binding.layActions.setVisibility(actions.size() == 0 ? View.GONE : View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        for(View v : actions) binding.layActions.addView(v,layoutParams);
    }

    private void addDevs(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layDevs.removeAllViews();
        for(Dev d : devs) {
            DevCardView devCardView = new DevCardView(ctx);
            devCardView.setApp(d);
            binding.layDevs.addView(devCardView,layoutParams);
        }
    }

    private void addApps(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layApps.removeAllViews();
        for(App d : apps) {
            AppCardView appCardView = new AppCardView(ctx);
            appCardView.setApp(d);
            binding.layApps.addView(appCardView,layoutParams);
        }
    }
    //endregion

}