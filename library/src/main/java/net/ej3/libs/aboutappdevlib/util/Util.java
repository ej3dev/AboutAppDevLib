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

/**
 * @author E.J. Jiménez
 * @version 20180308
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
        return ( url != null && Patterns.WEB_URL.matcher(url).matches() );
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Format
    //
    public static Spanned toHtml(String text) {
        return Html.fromHtml(""+text);
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

}
