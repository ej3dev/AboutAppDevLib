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

import net.ej3.libs.aboutappdevlib.databinding.AboutDevFragmentBinding;
import net.ej3.libs.aboutappdevlib.model.App;
import net.ej3.libs.aboutappdevlib.model.Dev;
import net.ej3.libs.aboutappdevlib.model.Faq;
import net.ej3.libs.aboutappdevlib.util.Util;
import net.ej3.libs.aboutappdevlib.view.AppCardView;
import net.ej3.libs.aboutappdevlib.view.DevCardView;
import net.ej3.libs.aboutappdevlib.view.FaqCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180314
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
    @ColorInt private int background;
    @ColorInt private int primaryTextColor;
    @ColorInt private int secondaryTextColor;
    @ColorInt private int sectionTitleColor;
    @ColorInt private int sectionDividerColor;

    @Nullable private Drawable logo;
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
        Context ctx;
        @ColorInt int mBackground          = DEFAULT_BACKGROUND_COLOR;
        @ColorInt int mPrimaryTextColor    = DEFAULT_TEXT_COLOR_PRIMARY;
        @ColorInt int mSecondaryTextColor  = DEFAULT_TEXT_COLOR_SECONDARY;
        @ColorInt int mSectionTitleColor   = DEFAULT_SECTION_TITLE_COLOR;
        @ColorInt int mSectionDividerColor = DEFAULT_SECTION_DIVIDER_COLOR;

        @Nullable Drawable mLogo;
        @Nullable String mAuthor;
        @Nullable String mInfo;
        @Nullable String mDevsTitle;
        @Nullable String mAppsTitle;
        List<View> mActions = new ArrayList<>();
        List<Dev> mDevs = new ArrayList<>();
        List<App> mApps = new ArrayList<>();

        public Builder(@NonNull final Context ctx) {
            this.ctx = ctx;
        }

        public Builder withBackgroundColor(@ColorInt int backgroundColor) {
            mBackground = backgroundColor;
            return this;
        }

        public Builder withBackgroundColorRes(@ColorRes int backgroundColorRes) {
            mBackground = ContextCompat.getColor(ctx,backgroundColorRes);
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

        public Builder withLogo(@Nullable Drawable logo) {
            mLogo = logo;
            return this;
        }

        public Builder withLogo(@DrawableRes int logoRes) {
            mLogo = ctx.getDrawable(logoRes);
            return this;
        }

        public Builder withAuthor(@Nullable String author) {
            mAuthor = author;
            return this;
        }

        public Builder withAuthor(@StringRes int authorRes) {
            mAuthor = ctx.getString(authorRes);
            return this;
        }

        public Builder withInfo(@Nullable String info) {
            mInfo = info;
            return this;
        }

        public Builder withInfo(@StringRes int infoRes) {
            mInfo = ctx.getString(infoRes);
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

        public Builder withDevs(@StringRes int titleRes,Dev... devs) {
            mDevsTitle = ctx.getString(titleRes);
            mDevs.addAll(Arrays.asList(devs));
            return this;
        }

        public Builder withApps(@Nullable String title,App... apps) {
            mAppsTitle = title;
            mApps.addAll(Arrays.asList(apps));
            return this;
        }

        public Builder withApps(@StringRes int titleRes,App... apps) {
            mAppsTitle = ctx.getString(titleRes);
            mApps.addAll(Arrays.asList(apps));
            return this;
        }

        public AboutDevFragment build() {
            AboutDevFragment aboutDevFragment = new AboutDevFragment();
            aboutDevFragment.background = mBackground;
            aboutDevFragment.primaryTextColor = mPrimaryTextColor;
            aboutDevFragment.secondaryTextColor = mSecondaryTextColor;
            aboutDevFragment.sectionTitleColor = mSectionTitleColor;
            aboutDevFragment.sectionDividerColor = mSectionDividerColor;

            aboutDevFragment.logo = mLogo;
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
        binding.setBackground(background);
        binding.setPrimaryTextColor(primaryTextColor);
        binding.setSecondaryTextColor(secondaryTextColor);
        binding.setSectionTitleColor(sectionTitleColor);
        binding.setSectionDividerColor(sectionDividerColor);

        binding.setLogo(logo);
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

        FaqCardView faqCardView = new FaqCardView(ctx);
        faqCardView.setFaq(new Faq("What is the Number of the Beast?","666",true));
        binding.layApps.addView(faqCardView,layoutParams);

        FaqCardView faqCardView2 = new FaqCardView(ctx);
        faqCardView2.setFaq(new Faq("Cómo empieza el clásico libro de caballerias Don Quijote de la Mancha?","En un lugar de la Mancha de cuyo nombre no quiero acordarme no hace mucho tiempo...",false));
        binding.layApps.addView(faqCardView2,layoutParams);


        for(App d : apps) {
            AppCardView appCardView = new AppCardView(ctx);
            appCardView.setApp(d);
            binding.layApps.addView(appCardView,layoutParams);
        }
    }
    //endregion

}