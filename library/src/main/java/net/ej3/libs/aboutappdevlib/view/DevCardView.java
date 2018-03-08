package net.ej3.libs.aboutappdevlib.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import net.ej3.libs.aboutappdevlib.databinding.DevCardViewBinding;
import net.ej3.libs.aboutappdevlib.model.Dev;
import net.ej3.libs.aboutappdevlib.util.Util;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings("unused")
public class DevCardView extends CardView {


    //--------------------------------------------------------------------------
    //region Constants
    //
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_PRIMARY   = 0xdd000000; //0xdd ~ 87%
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_SECONDARY = 0x88000000; //0x88 ~ 54%
    @ColorInt protected static final int DEFAULT_DIVIDER_COLOR        = 0x22546e7a; //0x22 ~ 13%
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    protected DevCardViewBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public DevCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DevCardView(@NonNull Context context,@Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    public DevCardView(@NonNull Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(context);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Config
    //
    private void init(final Context context) {
        binding = DevCardViewBinding.inflate(LayoutInflater.from(context),this,true);
        setTextColors(DEFAULT_TEXT_COLOR_PRIMARY,DEFAULT_TEXT_COLOR_SECONDARY);
        setDividerColor(DEFAULT_DIVIDER_COLOR);
    }

    public void setApp(final Dev dev) {
        binding.setDev(dev);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Util.isUrl(dev.getUrl()) ) {
                    Util.open(v.getContext(),dev.getUrl());
                }
            }
        });
    }

    public void setTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor) {
        binding.setPrimaryTextColor(primaryColor);
        binding.setSecondaryTextColor(secondaryColor);
    }

    public void setDividerColor(@ColorInt int dividerColor) {
        binding.setDividerColor(dividerColor);
    }
    //endregion

}
