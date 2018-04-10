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
 * @version 20180409
 */
@SuppressWarnings({"unused","SameParameterValue"})
public class AboutDevFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    private static final String ARGUMENT_ID = "argument_id";
    @ColorInt protected static final int DEFAULT_BACKGROUND_COLOR      = 0xffeceff1;
    @ColorInt protected static final int DEFAULT_TEXT_PRIMARY_COLOR    = 0xdd000000; //0xdd ~ 87%
    @ColorInt protected static final int DEFAULT_TEXT_SECONDARY_COLOR  = 0x88000000; //0x88 ~ 54%
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
    private AboutDevFragmentBinding binding;
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

        public Builder withLogo(@Nullable Drawable logo) {
            mConfig.logo = logo;
            return this;
        }

        public Builder withLogo(@DrawableRes int logoRes) {
            mConfig.logo = ctx.getDrawable(logoRes);
            return this;
        }

        public Builder withAuthor(@Nullable String author) {
            mConfig.author = author;
            return this;
        }

        public Builder withAuthor(@StringRes int authorRes) {
            mConfig.author = ctx.getString(authorRes);
            return this;
        }

        public Builder withInfo(@Nullable String info) {
            mConfig.info = info;
            return this;
        }

        public Builder withInfo(@StringRes int infoRes) {
            mConfig.info = ctx.getString(infoRes);
            return this;
        }

        public Builder withActions(View... actions) {
            mConfig.actions.addAll(Arrays.asList(actions));
            return this;
        }

        public Builder withDevs(@Nullable String title,Dev... devs) {
            mConfig.devsTitle = title;
            mConfig.devs.addAll(Arrays.asList(devs));
            return this;
        }

        public Builder withDevs(@StringRes int titleRes,Dev... devs) {
            mConfig.devsTitle = ctx.getString(titleRes);
            mConfig.devs.addAll(Arrays.asList(devs));
            return this;
        }

        public Builder withApps(@Nullable String title,App... apps) {
            mConfig.appsTitle = title;
            mConfig.apps.addAll(Arrays.asList(apps));
            return this;
        }

        public Builder withApps(@StringRes int titleRes,App... apps) {
            mConfig.appsTitle = ctx.getString(titleRes);
            mConfig.apps.addAll(Arrays.asList(apps));
            return this;
        }

        public AboutDevFragment build() {
            AboutDevFragment aboutDevFragment = new AboutDevFragment();
            if( mConfig.id == Integer.MIN_VALUE ) mConfig.id = aboutDevFragment.hashCode();
            store.put(mConfig.id,mConfig);
            final Bundle b = new Bundle();
            b.putInt(ARGUMENT_ID,mConfig.id);
            aboutDevFragment.setArguments(b);
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
        config = getConfig();
        if( config != null ) {
            setData();
            addActions();
            addDevs(inflater.getContext());
            addApps(inflater.getContext());
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

        binding.setLogo(config.logo);
        binding.setAuthor(Util.toHtml(config.author));
        binding.setInfo(Util.toHtml(config.info));
        binding.setDevsVisible(config.devs.size() > 0);
        binding.setAppsVisible(config.apps.size() > 0);
        binding.setDevsTitle(Util.toHtml(config.devsTitle));
        binding.setAppsTitle(Util.toHtml(config.appsTitle));
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

    private void addDevs(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layDevs.removeAllViews();
        for(Dev d : config.devs) {
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
        for(App d : config.apps) {
            AppCardView appCardView = new AppCardView(ctx);
            appCardView.setApp(d);
            binding.layApps.addView(appCardView,layoutParams);
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Config data
    //
    private static final class Config {
        int id = Integer.MIN_VALUE;
        @ColorInt int background = DEFAULT_BACKGROUND_COLOR;
        @ColorInt int primaryTextColor = DEFAULT_TEXT_PRIMARY_COLOR;
        @ColorInt int secondaryTextColor = DEFAULT_TEXT_SECONDARY_COLOR;
        @ColorInt int sectionTitleColor = DEFAULT_SECTION_TITLE_COLOR;
        @ColorInt int sectionDividerColor = DEFAULT_SECTION_DIVIDER_COLOR;
        @Nullable Drawable logo;
        @Nullable String author;
        @Nullable String info;
        @Nullable String devsTitle;
        @Nullable String appsTitle;
        List<View> actions = new ArrayList<>();
        List<Dev> devs = new ArrayList<>();
        List<App> apps = new ArrayList<>();
    }
    //endregion

}