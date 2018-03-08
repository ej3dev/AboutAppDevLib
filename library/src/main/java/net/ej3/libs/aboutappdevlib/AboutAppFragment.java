package net.ej3.libs.aboutappdevlib;

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

import net.ej3.libs.aboutappdevlib.databinding.AboutAppFragmentBinding;
import net.ej3.libs.aboutappdevlib.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings("unused")
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
    private List<View> actions;
    //endregion


    //--------------------------------------------------------------------------
    //region Builder
    //
    public static final class Builder {
        @ColorInt int mBackgroundColor     = 0xffeceff1;
        @ColorInt int mPrimaryTextColor    = 0xdd000000; //0xdd ~ 87%
        @ColorInt int mSecondaryTextColor  = 0x88000000; //0x88 ~ 54%
        @ColorInt int mSectionTitleColor   = 0xff546e7a;
        @ColorInt int mSectionDividerColor = 0x22546e7a; //0x22 ~ 13%

        @DrawableRes int mIconRes = -1;
        @Nullable String mName;
        @Nullable String mVersion;
        @Nullable String mCopyright;
        @Nullable String mThanksTitle;
        @Nullable String mThanksText;
        @Nullable String mChangelogTitle;
        @Nullable String mChangelogText;
        List<View> mActions = new ArrayList<>();

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

        public Builder withActions(View... actions) {
            mActions.addAll(Arrays.asList(actions));
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
            aboutAppFragment.actions = mActions;

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
        addActions();
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
        binding.setName(Util.toHtml(name));
        binding.setVersion(Util.toHtml(version));
        binding.setCopyright(Util.toHtml(copyright));
        binding.setThanksTitle(Util.toHtml(thanksTitle));
        binding.setThanksText(Util.toHtml(thanksText));
        binding.setChangelogTitle(Util.toHtml(changelogTitle));
        binding.setChangelogText(Util.toHtml(changelogText));
    }

    private void addActions() {
        binding.layActions.setVisibility(actions.size() == 0 ? View.GONE : View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        binding.layActions.removeAllViews();
        for(View v : actions) binding.layActions.addView(v,layoutParams);
    }
    //endregion

}