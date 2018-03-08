package net.ej3.libs.aboutappdevlib;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.ej3.libs.aboutappdevlib.databinding.AboutLibFragmentBinding;
import net.ej3.libs.aboutappdevlib.model.Lib;
import net.ej3.libs.aboutappdevlib.util.Util;
import net.ej3.libs.aboutappdevlib.view.LibCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings({"unused","SameParameterValue"})
public class AboutLibFragment extends Fragment {

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

    @Nullable private String info;
    @Nullable private String libsTitle;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutLibFragmentBinding binding;
    private List<Lib> libs;
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

        @Nullable String mInfo;
        @Nullable String mLibsTitle;
        List<Lib> mLibs = new ArrayList<>();

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

        public Builder withInfo(@Nullable String info) {
            mInfo = info;
            return this;
        }

        public Builder withLibs(@Nullable String title,Lib... libs) {
            mLibsTitle = title;
            mLibs.addAll(Arrays.asList(libs));
            return this;
        }

        public AboutLibFragment build() {
            AboutLibFragment aboutLibFragment = new AboutLibFragment();
            aboutLibFragment.backgroundColor = mBackgroundColor;
            aboutLibFragment.primaryTextColor = mPrimaryTextColor;
            aboutLibFragment.secondaryTextColor = mSecondaryTextColor;
            aboutLibFragment.sectionTitleColor = mSectionTitleColor;
            aboutLibFragment.sectionDividerColor = mSectionDividerColor;

            aboutLibFragment.info = mInfo;
            aboutLibFragment.libsTitle = mLibsTitle;
            aboutLibFragment.libs = mLibs;

            return aboutLibFragment;
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.about_lib_fragment,container,false);
        setData();
        addLibs(inflater.getContext());
        return binding.getRoot();
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

        binding.setInfo(Util.toHtml(info));
        binding.setLibsVisible(libs.size() > 0);
        binding.setLibsTitle(Util.toHtml(libsTitle));
    }

    private void addLibs(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layLibs.removeAllViews();
        for(Lib l : libs) {
            LibCardView libCardView = new LibCardView(ctx);
            libCardView.setApp(l);
            binding.layLibs.addView(libCardView,layoutParams);
        }
    }
    //endregion

}