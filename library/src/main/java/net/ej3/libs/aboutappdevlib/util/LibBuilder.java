package net.ej3.libs.aboutappdevlib.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import net.ej3.libs.aboutappdevlib.model.Lib;

/**
 * @author E.J. Jiménez
 * @version 20180310
 */
@SuppressWarnings("unused")
public class LibBuilder {

    //--------------------------------------------------------------------------
    //region Properties
    //
    private Context ctx;
    private String mName;
    private String mAuthor;
    private String mDescription;
    private Drawable mIcon;
    private String mUrl;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public LibBuilder(@NonNull final Context ctx,@NonNull final String name) {
        this.ctx = ctx;
        mName = name;
    }

    public LibBuilder(@NonNull final Context ctx,@StringRes final int nameRes) {
        this.ctx = ctx;
        mName = ctx.getString(nameRes);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Builder pattern
    //
    public LibBuilder withAuthor(@Nullable final String author) {
        mAuthor = author;
        return this;
    }

    public LibBuilder withAuthor(@StringRes final int authorRes) {
        mAuthor = ctx.getString(authorRes);
        return this;
    }

    public LibBuilder withDescription(@Nullable final String description) {
        mDescription = description;
        return this;
    }

    public LibBuilder withDescription(@StringRes final int descriptionRes) {
        mDescription = ctx.getString(descriptionRes);
        return this;
    }

    public LibBuilder withIcon(@Nullable final Drawable icon) {
        mIcon = icon;
        return this;
    }

    public LibBuilder withIcon(@DrawableRes final int iconRes) {
        mIcon = ctx.getDrawable(iconRes);
        return this;
    }

    public LibBuilder withUrl(@Nullable final String url) {
        mUrl = url;
        return this;
    }

    public LibBuilder withUrl(@StringRes final int urlRes) {
        mUrl = ctx.getString(urlRes);
        return this;
    }

    @NonNull
    public Lib build() {
        return new Lib(mName,mAuthor,mDescription,mIcon,mUrl);
    }
    //endregion

}
