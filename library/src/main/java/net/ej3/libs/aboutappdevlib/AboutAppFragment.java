package net.ej3.libs.aboutappdevlib;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ej3.libs.aboutappdevlib.databinding.AboutAppFragmentBinding;

/**
 * @author E.J. Jiménez
 * @version 20180305
 */
public class AboutAppFragment extends Fragment {

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
    @ColorInt private int sectionTitleColor;
    @ColorInt private int sectionDividerColor;

    @DrawableRes private int iconRes;
    @Nullable private String name;
    @Nullable private String version;
    @Nullable private String copyright;
    @Nullable private String thanksTitle;
    @Nullable private String thanksText;
    @Nullable private String changelogTitle;
    @Nullable private String changelogText;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutAppFragmentBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Builder
    //
    public static final class Builder {
        @ColorInt int mBackgroundColor = 0xffeceff1;
        @ColorInt int mPrimaryTextColor = 0xdd000000;   //0xdd ~ 87%
        @ColorInt int mSecondaryTextColor = 0x88000000; //0x88 ~ 54%
        @ColorInt int mSectionTitleColor = 0xff546e7a;
        @ColorInt int mSectionDividerColor = 0x22546e7a;//0x22 ~ 13%

        @DrawableRes int mIconRes = -1;
        @Nullable String mName;
        @Nullable String mVersion;
        @Nullable String mCopyright;
        @Nullable String mThanksTitle;
        @Nullable String mThanksText;
        @Nullable String mChangelogTitle;
        @Nullable String mChangelogText;

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

        public Builder withIcon(@DrawableRes int iconRes) {
            mIconRes = iconRes;
            return this;
        }

        public Builder withName(@Nullable String name) {
            mName = name;
            return this;
        }

        public Builder withVersion(@Nullable String version) {
            mVersion = version;
            return this;
        }
        public Builder withCopyright(@Nullable String copyright) {
            mCopyright = copyright;
            return this;
        }

        public Builder withThanks(@Nullable String title,@Nullable String text) {
            mThanksTitle = title;
            mThanksText = text;
            return this;
        }

        public Builder withChangelog(@Nullable String title,@Nullable String text) {
            mChangelogTitle = title;
            mChangelogText = text;
            return this;
        }

        public AboutAppFragment build() {
            AboutAppFragment aboutAppFragment = new AboutAppFragment();
            aboutAppFragment.backgroundColor = mBackgroundColor;
            aboutAppFragment.primaryTextColor = mPrimaryTextColor;
            aboutAppFragment.secondaryTextColor = mSecondaryTextColor;
            aboutAppFragment.sectionTitleColor = mSectionTitleColor;
            aboutAppFragment.sectionDividerColor = mSectionDividerColor;

            aboutAppFragment.iconRes = mIconRes;
            aboutAppFragment.name = mName;
            aboutAppFragment.version = mVersion;
            aboutAppFragment.copyright = mCopyright;
            aboutAppFragment.thanksTitle = mThanksTitle;
            aboutAppFragment.thanksText = mThanksText;
            aboutAppFragment.changelogTitle = mChangelogTitle;
            aboutAppFragment.changelogText = mChangelogText;

            return aboutAppFragment;
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.about_app_fragment,container,false);
        setData();
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

        binding.setIcon(iconRes);
        binding.setName(Html.fromHtml(name));
        binding.setVersion(Html.fromHtml(version));
        binding.setCopyright(Html.fromHtml(copyright));
        binding.setThanksTitle(Html.fromHtml(thanksTitle));
        binding.setThanksText(Html.fromHtml(thanksText));
        binding.setChangelogTitle(Html.fromHtml(changelogTitle));
        binding.setChangelogText(Html.fromHtml(changelogText));
    }
    //endregion

}