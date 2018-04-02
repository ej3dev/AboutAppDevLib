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
 * @version 20180316
 */
@SuppressWarnings("unused")
public class AboutFaqFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Constants
    //
    @ColorInt
    protected static final int DEFAULT_BACKGROUND_COLOR      = 0xffeceff1;
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

    @Nullable
    private String info;
    @Nullable private String faqsTitle;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private AboutFaqFragmentBinding binding;
    private List<Faq> faqs;
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

        @Nullable String mInfo;
        @Nullable String mFaqsTitle;
        List<Faq> mFaqs = new ArrayList<>();

        public Builder(@NonNull final Context ctx) {
            this.ctx = ctx;
        }

        public AboutFaqFragment.Builder withBackgroundColor(@ColorInt int backgroundColor) {
            mBackground = backgroundColor;
            return this;
        }

        public AboutFaqFragment.Builder withBackgroundColorRes(@ColorRes int backgroundColorRes) {
            mBackground = ContextCompat.getColor(ctx,backgroundColorRes);
            return this;
        }

        public AboutFaqFragment.Builder withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor) {
            mPrimaryTextColor = primaryColor;
            mSecondaryTextColor = secondaryColor;
            mSectionTitleColor = sectionColor;
            mSectionDividerColor = (0x22000000 | (sectionColor & 0xffffff));
            return this;
        }

        public AboutFaqFragment.Builder withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor) {
            mPrimaryTextColor = ContextCompat.getColor(ctx,primaryColor);
            mSecondaryTextColor = ContextCompat.getColor(ctx,secondaryColor);
            mSectionTitleColor = ContextCompat.getColor(ctx,sectionColor);
            mSectionDividerColor = (0x22000000 | (mSectionTitleColor & 0xffffff));
            return this;
        }

        public AboutFaqFragment.Builder withInfo(@Nullable String info) {
            mInfo = info;
            return this;
        }

        public AboutFaqFragment.Builder withInfo(@StringRes int infoRes) {
            mInfo = ctx.getString(infoRes);
            return this;
        }

        public AboutFaqFragment.Builder withFaqs(@Nullable String title,Faq... faqs) {
            mFaqsTitle = title;
            mFaqs.addAll(Arrays.asList(faqs));
            return this;
        }

        public AboutFaqFragment.Builder withFaqs(@StringRes int titleRes,Faq... faqs) {
            mFaqsTitle = ctx.getString(titleRes);
            mFaqs.addAll(Arrays.asList(faqs));
            return this;
        }

        public AboutFaqFragment build() {
            AboutFaqFragment aboutFaqFragment = new AboutFaqFragment();
            aboutFaqFragment.background = mBackground;
            aboutFaqFragment.primaryTextColor = mPrimaryTextColor;
            aboutFaqFragment.secondaryTextColor = mSecondaryTextColor;
            aboutFaqFragment.sectionTitleColor = mSectionTitleColor;
            aboutFaqFragment.sectionDividerColor = mSectionDividerColor;

            aboutFaqFragment.info = mInfo;
            aboutFaqFragment.faqsTitle = mFaqsTitle;
            aboutFaqFragment.faqs = mFaqs;

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
        binding.setBackground(background);
        binding.setPrimaryTextColor(primaryTextColor);
        binding.setSecondaryTextColor(secondaryTextColor);
        binding.setSectionTitleColor(sectionTitleColor);
        binding.setSectionDividerColor(sectionDividerColor);

        binding.setInfo(Util.toHtml(info));
        binding.setFaqsVisible(faqs.size() > 0);
        binding.setFaqsTitle(Util.toHtml(faqsTitle));
    }

    private void addFaqs(Context ctx) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        layoutParams.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        layoutParams.rightMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
        binding.layFaqs.removeAllViews();
        for(Faq f : faqs) {
            FaqCardView faqCardView = new FaqCardView(ctx);
            faqCardView.setFaq(f);
            binding.layFaqs.addView(faqCardView,layoutParams);
        }
    }
    //endregion

}
