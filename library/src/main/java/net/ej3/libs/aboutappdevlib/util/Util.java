package net.ej3.libs.aboutappdevlib.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;

/**
 * @author E.J. Jiménez
 * @version 20180314
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Util {

    //--------------------------------------------------------------------------
    //region Checks
    //
    public static void tryPackage(final Context ctx,final String packageName) throws PackageManager.NameNotFoundException {
        ctx.getPackageManager().getPackageInfo(packageName,0);
    }

    public static boolean resolveActivity(final Context ctx,final Intent intent) {
        return (intent.resolveActivity(ctx.getPackageManager()) != null);
    }

    public static boolean isUrl(@Nullable final String url) {
        return ( url != null && url.toLowerCase().startsWith("http") && Patterns.WEB_URL.matcher(url).matches() );
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Format
    //
    public static Spanned toHtml(String text) {
        return Html.fromHtml(text == null ? "" : text);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Intents
    //
    @NonNull
    public static Intent newIntent(final Uri uri) {
        return new Intent(Intent.ACTION_VIEW,uri);
    }

    @NonNull
    public static Intent newIntent(final String url) {
        return newIntent(Uri.parse(url));
    }

    @NonNull
    public static Intent newIntent(final String url,final String user) {
        return newIntent(Uri.parse(String.format(url,user)));
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Open app
    //
    public static void open(final Context ctx,final Intent intent) {
        try {
            ctx.startActivity(intent);
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

    public static void open(final Context ctx,final Uri uri) {
        open(ctx,newIntent(uri));
    }

    public static void open(final Context ctx,final String url) {
        open(ctx,newIntent(url));
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Expandable views
    //
    public static void expand(final ViewGroup vg,int duration) {
        slide(vg,duration,true);
    }

    public static void collapse(final ViewGroup vg,int duration) {
        slide(vg,duration,false);
    }

    private static void slide(final ViewGroup vg,int duration,final boolean expand) {
        try {
            Method m = vg.getClass().getDeclaredMethod("onMeasure",int.class,int.class);
            m.setAccessible(true);
            m.invoke(
                vg,
                View.MeasureSpec.makeMeasureSpec(((View)vg.getParent()).getMeasuredWidth(),View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
            );
        } catch (Exception e) {
            Logger.e("slideAnimation\n%s",e.getMessage());
        }

        final int initialHeight = vg.getMeasuredHeight();
        if( expand ) {
            vg.getLayoutParams().height = 0;
        } else {
            vg.getLayoutParams().height = initialHeight;
        }
        vg.setVisibility(View.VISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,Transformation t) {
                int newHeight = 0;
                if( expand ) {
                    newHeight = (int)(initialHeight * interpolatedTime);
                } else {
                    newHeight = (int)(initialHeight * (1 - interpolatedTime));
                }
                vg.getLayoutParams().height = newHeight;
                vg.requestLayout();
                if( interpolatedTime == 1 && !expand ) vg.setVisibility(View.GONE);
            }
            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(duration);
        vg.startAnimation(a);
    }
    //endregion

}
