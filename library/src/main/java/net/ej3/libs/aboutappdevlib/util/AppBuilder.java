package net.ej3.libs.aboutappdevlib.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import net.ej3.libs.aboutappdevlib.model.App;

/**
 * @author E.J. Jiménez
 * @version 20180310
 */
@SuppressWarnings("unused")
public class AppBuilder {

    //--------------------------------------------------------------------------
    //region Properties
    //
    private Context ctx;
    private String mUrlOrPackageName;
    private String mName;
    private String mDescription;
    private Drawable mIcon;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public AppBuilder(@NonNull final Context ctx,@NonNull final String name) {
        this.ctx = ctx;
        mName = name;
    }

    public AppBuilder(@NonNull final Context ctx,@StringRes final int nameRes) {
        this.ctx = ctx;
        mName = ctx.getString(nameRes);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Builder pattern
    //
    public AppBuilder withUrlOrPackageName(@Nullable final String urlOrPackageName) {
        mUrlOrPackageName = urlOrPackageName;
        return this;
    }

    public AppBuilder withUrlOrPackageName(@StringRes final int urlOrPackageNameRes) {
        mUrlOrPackageName = ctx.getString(urlOrPackageNameRes);
        return this;
    }

    public AppBuilder withDescription(@Nullable final String description) {
        mDescription = description;
        return this;
    }

    public AppBuilder withDescription(@StringRes final int descriptionRes) {
        mDescription = ctx.getString(descriptionRes);
        return this;
    }

    public AppBuilder withIcon(@Nullable final Drawable icon) {
        mIcon = icon;
        return this;
    }

    public AppBuilder withIcon(@DrawableRes final int iconRes) {
        mIcon = ctx.getDrawable(iconRes);
        return this;
    }

    @NonNull
    public App build() {
        return new App(mUrlOrPackageName,mName,mDescription,mIcon);
    }
    //endregion

}
