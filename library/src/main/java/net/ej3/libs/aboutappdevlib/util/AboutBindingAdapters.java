package net.ej3.libs.aboutappdevlib.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author E.J. Jiménez
 * @version 20180401
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
    public static void setVisibility(@NonNull View view,@Nullable CharSequence text) {
        int visibility = ((text == null || text.toString().trim().isEmpty()) ? View.GONE : View.VISIBLE);
        view.setVisibility(visibility);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(@NonNull View view,@DrawableRes int resource) {
        int visibility = (resource == 0 ? View.GONE : View.VISIBLE);
        view.setVisibility(visibility);
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(@NonNull View view,@Nullable Drawable drawable) {
        int visibility = (drawable == null ? View.GONE : View.VISIBLE);
        view.setVisibility(visibility);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region ImageView
    //
    @BindingAdapter("lib:src")
    public static void setImageDrawable(ImageView imageView,Drawable drawable){
        if( drawable != null ) imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("lib:src")
    public static void setImageResource(ImageView imageView,@DrawableRes int resource){
        if( resource != 0 ) imageView.setImageResource(resource);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region TextView
    //
    @BindingAdapter("lib:htmlText")
    public static void setHtmlText(TextView textView,@Nullable String text) {
        if( text == null || TextUtils.isEmpty(text) ) return;
        textView.setText(Html.fromHtml(text));
    }

    @BindingAdapter("lib:htmlText")
    public static void setHtmlText(TextView textView,@StringRes int resource) {
        if( resource == 0 ) return;
        final CharSequence text = textView.getContext().getText(resource);
        if( text == null || TextUtils.isEmpty(text) ) return;
        textView.setText(text);
    }
    //endregion

}
