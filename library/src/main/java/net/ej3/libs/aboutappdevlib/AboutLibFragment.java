package net.ej3.libs.aboutappdevlib;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
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

import net.ej3.libs.aboutappdevlib.databinding.AboutLibFragmentBinding;
import net.ej3.libs.aboutappdevlib.model.Lib;
import net.ej3.libs.aboutappdevlib.util.Util;
import net.ej3.libs.aboutappdevlib.view.LibCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180410
 */
@SuppressWarnings({"unused","SameParameterValue"})
public class AboutLibFragment extends Fragment {

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
    private AboutLibFragmentBinding binding;
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

        public Builder withInfo(@Nullable String info) {
            mConfig.info = info;
            return this;
        }

        public Builder withInfo(@StringRes int infoRes) {
            mConfig.info = ctx.getString(infoRes);
            return this;
        }

        public Builder withLibs(@Nullable String title,Lib... libs) {
            mConfig.libsTitle = title;
            mConfig.libs.addAll(Arrays.asList(libs));
            return this;
        }

        public Builder withLibs(@StringRes int titleRes,Lib... libs) {
            mConfig.libsTitle = ctx.getString(titleRes);
            mConfig.libs.addAll(Arrays.asList(libs));
            return this;
        }

        public AboutLibFragment build() {
            AboutLibFragment aboutLibFragment = new AboutLibFragment();
            if( mConfig.id == Integer.MIN_VALUE ) mConfig.id = aboutLibFragment.hashCode();
            store.put(mConfig.id,mConfig);
            final Bundle b = new Bundle();
            b.putInt(ARGUMENT_ID,mConfig.id);
            aboutLibFragment.setArguments(b);
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
        config = getConfig();
        if( config != null ) {
            setData();
            addLibs(inflater.getContext());
        }
        return binding.getRoot();
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

        binding.setInfo(Util.toHtml(config.info));
        binding.setLibsVisible(config.libs.size() > 0);
        binding.setLibsTitle(Util.toHtml(config.libsTitle));
    }

    private void addLibs(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layLibs.removeAllViews();
        for(Lib l : config.libs) {
            LibCardView libCardView = new LibCardView(ctx);
            libCardView.setLib(l);
            binding.layLibs.addView(libCardView,layoutParams);
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
        @Nullable String info;
        @Nullable String libsTitle;
        List<Lib> libs = new ArrayList<>();
    }
    //endregion

}