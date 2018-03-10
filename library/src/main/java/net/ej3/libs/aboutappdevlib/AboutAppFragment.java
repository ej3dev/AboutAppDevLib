package net.ej3.libs.aboutappdevlib;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
 * @version 20180310
 */
@SuppressWarnings("unused")
public class AboutAppFragment extends Fragment {

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
    @ColorInt private int background;
    @ColorInt private int primaryTextColor;
    @ColorInt private int secondaryTextColor;
    @ColorInt private int sectionTitleColor;
    @ColorInt private int sectionDividerColor;

    @Nullable private Drawable iconRes;
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
        Context ctx;
        @ColorInt int mBackground          = DEFAULT_BACKGROUND_COLOR;
        @ColorInt int mPrimaryTextColor    = DEFAULT_TEXT_COLOR_PRIMARY;
        @ColorInt int mSecondaryTextColor  = DEFAULT_TEXT_COLOR_SECONDARY;
        @ColorInt int mSectionTitleColor   = DEFAULT_SECTION_TITLE_COLOR;
        @ColorInt int mSectionDividerColor = DEFAULT_SECTION_DIVIDER_COLOR;

        @Nullable Drawable mIcon;
        @Nullable String mName;
        @Nullable String mVersion;
        @Nullable String mCopyright;
        @Nullable String mThanksTitle;
        @Nullable String mThanksText;
        @Nullable String mChangelogTitle;
        @Nullable String mChangelogText;
        List<View> mActions = new ArrayList<>();

        public Builder(@NonNull final Context ctx) {
            this.ctx = ctx;
        }

        public Builder withBackgroundColor(@ColorInt int backgroundColor) {
            mBackground = backgroundColor;
            return this;
        }

        public Builder withBackgroundResource(@ColorRes @DrawableRes int backgroundRes) {
            mBackground = backgroundRes;
            return this;
        }

        public Builder withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor) {
            mPrimaryTextColor = primaryColor;
            mSecondaryTextColor = secondaryColor;
            mSectionTitleColor = sectionColor;
            mSectionDividerColor = (0x22000000 | (sectionColor & 0xffffff));
            return this;
        }

        public Builder withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor) {
            mPrimaryTextColor = ContextCompat.getColor(ctx,primaryColor);
            mSecondaryTextColor = ContextCompat.getColor(ctx,secondaryColor);
            mSectionTitleColor = ContextCompat.getColor(ctx,sectionColor);
            mSectionDividerColor = (0x22000000 | (mSectionTitleColor & 0xffffff));
            return this;
        }

        public Builder withIcon(@Nullable Drawable icon) {
            mIcon = icon;
            return this;
        }

        public Builder withIcon(@DrawableRes int iconRes) {
            mIcon = ctx.getDrawable(iconRes);
            return this;
        }

        public Builder withName(@Nullable String name) {
            mName = name;
            return this;
        }

        public Builder withName(@StringRes int nameRes) {
            mName = ctx.getString(nameRes);
            return this;
        }

        public Builder withVersion(@Nullable String version) {
            mVersion = version;
            return this;
        }

        public Builder withVersion(@StringRes int versionRes) {
            mVersion = ctx.getString(versionRes);
            return this;
        }

        public Builder withCopyright(@Nullable String copyright) {
            mCopyright = copyright;
            return this;
        }

        public Builder withCopyright(@StringRes int copyrightRes) {
            mCopyright = ctx.getString(copyrightRes);
            return this;
        }

        public Builder withThanks(@Nullable String title,@Nullable String text) {
            mThanksTitle = title;
            mThanksText = text;
            return this;
        }

        public Builder withThanks(@StringRes int titleRes,@StringRes int textRes) {
            mThanksTitle = ctx.getString(titleRes);
            mThanksText = ctx.getString(textRes);
            return this;
        }

        public Builder withChangelog(@Nullable String title,@Nullable String text) {
            mChangelogTitle = title;
            mChangelogText = text;
            return this;
        }

        public Builder withChangelog(@StringRes int titleRes,@StringRes int textRes) {
            mChangelogTitle = ctx.getString(titleRes);
            mChangelogText = ctx.getString(textRes);
            return this;
        }

        public Builder withActions(View... actions) {
            mActions.addAll(Arrays.asList(actions));
            return this;
        }

        public AboutAppFragment build() {
            AboutAppFragment aboutAppFragment = new AboutAppFragment();
            aboutAppFragment.background = mBackground;
            aboutAppFragment.primaryTextColor = mPrimaryTextColor;
            aboutAppFragment.secondaryTextColor = mSecondaryTextColor;
            aboutAppFragment.sectionTitleColor = mSectionTitleColor;
            aboutAppFragment.sectionDividerColor = mSectionDividerColor;

            aboutAppFragment.iconRes = mIcon;
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
        binding = DataBindingUtil.inflate(inflater,R.layout.about_app_fragment,null,false);
        setData();
        addActions();
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
        binding.setBackground(background);
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
        for(View v : actions) binding.layActions.addView(v,layoutParams);
    }
    //endregion

}