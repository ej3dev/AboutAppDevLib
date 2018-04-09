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
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import net.ej3.libs.aboutappdevlib.databinding.AboutAppFragmentBinding;
import net.ej3.libs.aboutappdevlib.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180314
 */
@SuppressWarnings("unused")
public class AboutAppFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    private static final String ARGUMENT_ID = "argument_id";
    @ColorInt protected static final int DEFAULT_BACKGROUND_COLOR      = 0xffeceff1;
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_PRIMARY    = 0xdd000000; //0xdd ~ 87%
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_SECONDARY  = 0x88000000; //0x88 ~ 54%
    @ColorInt protected static final int DEFAULT_SECTION_TITLE_COLOR   = 0xff546e7a;
    @ColorInt protected static final int DEFAULT_SECTION_DIVIDER_COLOR = 0x22546e7a; //0x22 ~ 13%
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    private static final SparseArray<Config> store = new SparseArray<>();
    private Config config;
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
        Context ctx;
        Config mConfig;

        public Builder(@NonNull final Context ctx) {
            this.ctx = ctx;
            mConfig = new Config();
        }

        public Builder withId(int id) {
            mConfig.id = (id == Integer.MIN_VALUE ? id+1 : id);
            return this;
        }

        public Builder withBackgroundColor(@ColorInt int backgroundColor) {
            mConfig.background = backgroundColor;
            return this;
        }

        public Builder withBackgroundColorRes(@ColorRes int backgroundColorRes) {
            mConfig.background = ContextCompat.getColor(ctx,backgroundColorRes);
            return this;
        }

        public Builder withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor) {
            mConfig.primaryTextColor = primaryColor;
            mConfig.secondaryTextColor = secondaryColor;
            mConfig.sectionTitleColor = sectionColor;
            mConfig.sectionDividerColor = (0x22000000 | (sectionColor & 0xffffff));
            return this;
        }

        public Builder withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor) {
            mConfig.primaryTextColor = ContextCompat.getColor(ctx,primaryColor);
            mConfig.secondaryTextColor = ContextCompat.getColor(ctx,secondaryColor);
            mConfig.sectionTitleColor = ContextCompat.getColor(ctx,sectionColor);
            mConfig.sectionDividerColor = (0x22000000 | (mConfig.sectionTitleColor & 0xffffff));
            return this;
        }

        public Builder withIcon(@Nullable Drawable icon) {
            mConfig.icon = icon;
            return this;
        }

        public Builder withIcon(@DrawableRes int iconRes) {
            mConfig.icon = ctx.getDrawable(iconRes);
            return this;
        }

        public Builder withName(@Nullable String name) {
            mConfig.name = name;
            return this;
        }

        public Builder withName(@StringRes int nameRes) {
            mConfig.name = ctx.getString(nameRes);
            return this;
        }

        public Builder withVersion(@Nullable String version) {
            mConfig.version = version;
            return this;
        }

        public Builder withVersion(@StringRes int versionRes) {
            mConfig.version = ctx.getString(versionRes);
            return this;
        }

        public Builder withCopyright(@Nullable String copyright) {
            mConfig.copyright = copyright;
            return this;
        }

        public Builder withCopyright(@StringRes int copyrightRes) {
            mConfig.copyright = ctx.getString(copyrightRes);
            return this;
        }

        public Builder withThanks(@Nullable String title,@Nullable String text) {
            mConfig.thanksTitle = title;
            mConfig.thanksText = text;
            return this;
        }

        public Builder withThanks(@StringRes int titleRes,@StringRes int textRes) {
            mConfig.thanksTitle = ctx.getString(titleRes);
            mConfig.thanksText = ctx.getString(textRes);
            return this;
        }

        public Builder withChangelog(@Nullable String title,@Nullable String text) {
            mConfig.changelogTitle = title;
            mConfig.changelogText = text;
            return this;
        }

        public Builder withChangelog(@StringRes int titleRes,@StringRes int textRes) {
            mConfig.changelogTitle = ctx.getString(titleRes);
            mConfig.changelogText = ctx.getString(textRes);
            return this;
        }

        public Builder withActions(View... actions) {
            mConfig.actions.addAll(Arrays.asList(actions));
            return this;
        }

        public AboutAppFragment build() {
            AboutAppFragment aboutAppFragment = new AboutAppFragment();
            if( mConfig.id == Integer.MIN_VALUE ) mConfig.id = aboutAppFragment.hashCode();
            store.put(mConfig.id,mConfig);
            final Bundle b = new Bundle();
            b.putInt(ARGUMENT_ID,mConfig.id);
            aboutAppFragment.setArguments(b);
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
        config = getConfig();
        if( config != null ) {
            setData();
            addActions();
        }
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

    private void setData() {
        binding.setBackground(config.background);
        binding.setPrimaryTextColor(config.primaryTextColor);
        binding.setSecondaryTextColor(config.secondaryTextColor);
        binding.setSectionTitleColor(config.sectionTitleColor);
        binding.setSectionDividerColor(config.sectionDividerColor);

        binding.setIcon(config.icon);
        binding.setName(Util.toHtml(config.name));
        binding.setVersion(Util.toHtml(config.version));
        binding.setCopyright(Util.toHtml(config.copyright));
        binding.setThanksTitle(Util.toHtml(config.thanksTitle));
        binding.setThanksText(Util.toHtml(config.thanksText));
        binding.setChangelogTitle(Util.toHtml(config.changelogTitle));
        binding.setChangelogText(Util.toHtml(config.changelogText));
    }

    private void addActions() {
        binding.layActions.setVisibility(config.actions.size() == 0 ? View.GONE : View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getResources().getDisplayMetrics());
        for(View v : config.actions) binding.layActions.addView(v,layoutParams);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Config data
    //
    private static final class Config {
        int id = Integer.MIN_VALUE;
        @ColorInt int background          = DEFAULT_BACKGROUND_COLOR;
        @ColorInt int primaryTextColor    = DEFAULT_TEXT_COLOR_PRIMARY;
        @ColorInt int secondaryTextColor  = DEFAULT_TEXT_COLOR_SECONDARY;
        @ColorInt int sectionTitleColor   = DEFAULT_SECTION_TITLE_COLOR;
        @ColorInt int sectionDividerColor = DEFAULT_SECTION_DIVIDER_COLOR;
        @Nullable Drawable icon;
        @Nullable String name;
        @Nullable String version;
        @Nullable String copyright;
        @Nullable String thanksTitle;
        @Nullable String thanksText;
        @Nullable String changelogTitle;
        @Nullable String changelogText;
        List<View> actions = new ArrayList<>();
    }
    //endregion

}