package net.ej3.libs.aboutappdevlib.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import net.ej3.libs.aboutappdevlib.K;
import net.ej3.libs.aboutappdevlib.databinding.AppCardViewBinding;
import net.ej3.libs.aboutappdevlib.model.App;
import net.ej3.libs.aboutappdevlib.util.Util;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings("unused")
public class AppCardView extends CardView {

    //--------------------------------------------------------------------------
    //region Constants
    //
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_PRIMARY   = 0xdd000000; //0xdd ~ 87%
    @ColorInt protected static final int DEFAULT_TEXT_COLOR_SECONDARY = 0x88000000; //0x88 ~ 54%
    //endregion


    //--------------------------------------------------------------------------
    //region Properties
    //
    protected AppCardViewBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public AppCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public AppCardView(@NonNull Context context,@Nullable AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    public AppCardView(@NonNull Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(context);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Config
    //
    private void init(final Context context) {
        binding = AppCardViewBinding.inflate(LayoutInflater.from(context),this,true);
        setTextColors(DEFAULT_TEXT_COLOR_PRIMARY,DEFAULT_TEXT_COLOR_SECONDARY);
    }

    public void setApp(final App app) {
        binding.setApp(app);
        binding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( app.getUrlOrPackageName() == null ) return;
                if( Util.isUrl(app.getUrlOrPackageName()) ) {
                    Util.open(v.getContext(),app.getUrlOrPackageName());
                } else {
                    Intent intent = Util.newIntent(K.PLAY_STORE_APP_URI,app.getUrlOrPackageName());
                    if( Util.resolveActivity(v.getContext(),intent) ) {
                        Util.open(v.getContext(),intent);
                    } else {
                        Util.open(v.getContext(),Util.newIntent(K.PLAY_STORE_APP_WEB_URL,app.getUrlOrPackageName()));
                    }
                }
            }
        });
    }

    public void setTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor) {
        binding.setPrimaryTextColor(primaryColor);
        binding.setSecondaryTextColor(secondaryColor);
    }
    //endregion

}
