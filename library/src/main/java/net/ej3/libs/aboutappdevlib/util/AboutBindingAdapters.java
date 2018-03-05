package net.ej3.libs.aboutappdevlib.util;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author E.J. Jiménez
 * @version 20180305
 */
@SuppressWarnings("unused")
public class AboutBindingAdapters {

    //--------------------------------------------------------------------------
    //region View
    //
    @BindingAdapter("android:visibility")
    public static void setVisibility(@NonNull View view,@Nullable Boolean visible) {
        int visibility = ((visible == null || !visible) ? View.GONE : View.VISIBLE);
        view.setVisibility(visibility);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(@NonNull View view,@Nullable String text) {
        int visibility = ((text == null || text.trim().isEmpty()) ? View.GONE : View.VISIBLE);
        view.setVisibility(visibility);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(@NonNull View view,@DrawableRes int resource) {
        int visibility = (resource <= 0 ? View.GONE : View.VISIBLE);
        view.setVisibility(visibility);
    }
    //endregion

}
