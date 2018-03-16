package net.ej3.libs.aboutappdevlib.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import net.ej3.libs.aboutappdevlib.databinding.FaqCardViewBinding;
import net.ej3.libs.aboutappdevlib.model.Faq;
import net.ej3.libs.aboutappdevlib.util.Util;

/**
 * @author E.J. Jiménez
 * @version 20180316
 */
@SuppressWarnings("unused")
public class FaqCardView extends CardView {

    //--------------------------------------------------------------------------
    //region Constants
    //
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_PRIMARY   = 0xdd000000; //0xdd ~ 87%
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_SECONDARY = 0x88000000; //0x88 ~ 54%
    protected static final int DEFAULT_ANIMATION_DURATION = 250;
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    protected FaqCardViewBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public FaqCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FaqCardView(@NonNull Context context,@Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    public FaqCardView(@NonNull Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(context);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Config
    //
    private void init(final Context context) {
        binding = FaqCardViewBinding.inflate(LayoutInflater.from(context),this,true);
        binding.imgToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( binding.layExpandable.getVisibility() == View.GONE ) {
                    Util.expand(binding.layExpandable,DEFAULT_ANIMATION_DURATION);
                    binding.imgToggle.startAnimation(rotate(0.0f,-180.0f,DEFAULT_ANIMATION_DURATION));
                } else {
                    Util.collapse(binding.layExpandable,DEFAULT_ANIMATION_DURATION);
                    binding.imgToggle.startAnimation(rotate(-180.0f,0.0f,DEFAULT_ANIMATION_DURATION));
                }
            }
        });
        setTextColors(DEFAULT_TEXT_COLOR_PRIMARY,DEFAULT_TEXT_COLOR_SECONDARY);
    }

    public void setFaq(final Faq faq) {
        binding.setFaq(faq);
        if( faq.isOpen() ) binding.imgToggle.startAnimation(rotate(0.0f,-180.0f,0));
    }

    public void setTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor) {
        binding.setPrimaryTextColor(primaryColor);
        binding.setSecondaryTextColor(secondaryColor);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private Animation rotate(final float fromAngle,final float toAngle,final long duration) {
        Animation animation = new RotateAnimation(fromAngle,toAngle,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setFillAfter(true);
        animation.setDuration(duration);
        return animation;
    }
    //endregion

}
