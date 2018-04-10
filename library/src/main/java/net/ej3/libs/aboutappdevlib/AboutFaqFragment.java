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

import net.ej3.libs.aboutappdevlib.databinding.AboutFaqFragmentBinding;
import net.ej3.libs.aboutappdevlib.model.Faq;
import net.ej3.libs.aboutappdevlib.util.Util;
import net.ej3.libs.aboutappdevlib.view.FaqCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author E.J. Jiménez
 * @version 20180410
 */
@SuppressWarnings("unused")
public class AboutFaqFragment extends Fragment {

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
    private AboutFaqFragmentBinding binding;
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

        public AboutFaqFragment.Builder withBackgroundColor(@ColorInt int backgroundColor) {
            mConfig.background = backgroundColor;
            return this;
        }

        public AboutFaqFragment.Builder withBackgroundColorRes(@ColorRes int backgroundColorRes) {
            mConfig.background = ContextCompat.getColor(ctx,backgroundColorRes);
            return this;
        }

        public AboutFaqFragment.Builder withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor) {
            mConfig.primaryTextColor = primaryColor;
            mConfig.secondaryTextColor = secondaryColor;
            mConfig.sectionTitleColor = sectionColor;
            mConfig.sectionDividerColor = (0x22000000 | (sectionColor & 0xffffff));
            return this;
        }

        public AboutFaqFragment.Builder withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor) {
            mConfig.primaryTextColor = ContextCompat.getColor(ctx,primaryColor);
            mConfig.secondaryTextColor = ContextCompat.getColor(ctx,secondaryColor);
            mConfig.sectionTitleColor = ContextCompat.getColor(ctx,sectionColor);
            mConfig.sectionDividerColor = (0x22000000 | (mConfig.sectionTitleColor & 0xffffff));
            return this;
        }

        public AboutFaqFragment.Builder withInfo(@Nullable String info) {
            mConfig.info = info;
            return this;
        }

        public AboutFaqFragment.Builder withInfo(@StringRes int infoRes) {
            mConfig.info = ctx.getString(infoRes);
            return this;
        }

        public AboutFaqFragment.Builder withFaqs(@Nullable String title,Faq... faqs) {
            mConfig.faqsTitle = title;
            mConfig.faqs.addAll(Arrays.asList(faqs));
            return this;
        }

        public AboutFaqFragment.Builder withFaqs(@StringRes int titleRes,Faq... faqs) {
            mConfig.faqsTitle = ctx.getString(titleRes);
            mConfig.faqs.addAll(Arrays.asList(faqs));
            return this;
        }

        public AboutFaqFragment build() {
            AboutFaqFragment aboutFaqFragment = new AboutFaqFragment();
            if( mConfig.id == Integer.MIN_VALUE ) mConfig.id = aboutFaqFragment.hashCode();
            store.put(mConfig.id,mConfig);
            final Bundle b = new Bundle();
            b.putInt(ARGUMENT_ID,mConfig.id);
            aboutFaqFragment.setArguments(b);
            return aboutFaqFragment;
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.about_faq_fragment,container,false);
        setData();
        addFaqs(inflater.getContext());
        return binding.getRoot();
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private void setData() {
        binding.setBackground(config.background);
        binding.setPrimaryTextColor(config.primaryTextColor);
        binding.setSecondaryTextColor(config.secondaryTextColor);
        binding.setSectionTitleColor(config.sectionTitleColor);
        binding.setSectionDividerColor(config.sectionDividerColor);

        binding.setInfo(Util.toHtml(config.info));
        binding.setFaqsVisible(config.faqs.size() > 0);
        binding.setFaqsTitle(Util.toHtml(config.faqsTitle));
    }

    private void addFaqs(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layFaqs.removeAllViews();
        for(Faq f : config.faqs) {
            FaqCardView faqCardView = new FaqCardView(ctx);
            faqCardView.setFaq(f);
            binding.layFaqs.addView(faqCardView,layoutParams);
        }
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Config data
    //
    private static final class Config {
        int id = Integer.MIN_VALUE;
        @ColorInt int background;
        @ColorInt int primaryTextColor;
        @ColorInt int secondaryTextColor;
        @ColorInt int sectionTitleColor;
        @ColorInt int sectionDividerColor;
        @Nullable String info;
        @Nullable String faqsTitle;
        List<Faq> faqs = new ArrayList<>();
    }
    //endregion

}
