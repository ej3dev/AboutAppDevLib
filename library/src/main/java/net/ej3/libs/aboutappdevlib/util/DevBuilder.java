package net.ej3.libs.aboutappdevlib.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import net.ej3.libs.aboutappdevlib.model.Dev;

/**
 * @author E.J. Jiménez
 * @version 20180310
 */
@SuppressWarnings("unused")
public class DevBuilder {

    //--------------------------------------------------------------------------
    //region Properties
    //
    private Context ctx;
    private String mName;
    private String mJob;
    private String mBio;
    private Drawable mPhoto;
    private String mUrl;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public DevBuilder(@NonNull final Context ctx,@NonNull final String name) {
        this.ctx = ctx;
        mName = name;
    }

    public DevBuilder(@NonNull final Context ctx,@StringRes final int nameRes) {
        this.ctx = ctx;
        mName = ctx.getString(nameRes);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Builder pattern
    //
    public DevBuilder withJob(@Nullable final String job) {
        mJob = job;
        return this;
    }

    public DevBuilder withJob(@StringRes final int jobRes) {
        mJob = ctx.getString(jobRes);
        return this;
    }

    public DevBuilder withBio(@Nullable final String bio) {
        mBio = bio;
        return this;
    }

    public DevBuilder withBio(@StringRes final int bioRes) {
        mBio = ctx.getString(bioRes);
        return this;
    }

    public DevBuilder withPhoto(@Nullable final Drawable photo) {
        mPhoto = photo;
        return this;
    }

    public DevBuilder withPhoto(@DrawableRes final int photoRes) {
        mPhoto = ctx.getDrawable(photoRes);
        return this;
    }

    public DevBuilder withUrl(@Nullable final String url) {
        mUrl = url;
        return this;
    }

    public DevBuilder withUrl(@StringRes final int urlRes) {
        mUrl = ctx.getString(urlRes);
        return this;
    }

    @NonNull
    public Dev build() {
        return new Dev(mName,mJob,mBio,mPhoto,mUrl);
    }
    //endregion

}
