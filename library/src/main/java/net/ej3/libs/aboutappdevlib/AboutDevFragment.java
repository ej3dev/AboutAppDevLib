package net.ej3.libs.aboutappdevlib;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ej3.libs.aboutappdevlib.databinding.AboutDevFragmentBinding;

/**
 * @author E.J. Jiménez
 * @version 20180305
 */
public class AboutDevFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    @ColorInt private int backgroundColor;
    @ColorInt private int primaryTextColor;
    @ColorInt private int secondaryTextColor;
    @ColorInt private int sectionTextColor;
    @ColorInt private int sectionDividerColor;

    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutDevFragmentBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Builder
    //
    public static final class Builder {
        @ColorInt int mBackgroundColor = 0xffeceff1;
        @ColorInt int mPrimaryTextColor = 0xdd000000;   //0xdd ~ 87%
        @ColorInt int mSecondaryTextColor = 0x88000000; //0x88 ~ 54%
        @ColorInt int mSectionTextColor = 0xffeceff1;
        @ColorInt int mSectionDividerColor = 0x22eceff1;//0x22 ~ 13%

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
            mSectionTextColor = sectionColor;
            mSectionDividerColor = (0x22000000 | (sectionColor & 0xffffff));
            return this;
        }

        public AboutDevFragment build() {
            AboutDevFragment aboutDevFragment = new AboutDevFragment();
            aboutDevFragment.backgroundColor = mBackgroundColor;
            aboutDevFragment.primaryTextColor = mPrimaryTextColor;
            aboutDevFragment.secondaryTextColor = mSecondaryTextColor;
            aboutDevFragment.sectionTextColor = mSectionTextColor;
            aboutDevFragment.sectionDividerColor = mSectionDividerColor;
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
        return binding.getRoot();
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private void setData() {

    }
    //endregion

}