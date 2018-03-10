package net.ej3.libs.aboutappdevlib.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author E.J. Jiménez
 * @version 20180310
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Dev {

    //--------------------------------------------------------------------------
    //region Properties
    //
    protected String name;
    protected String job;
    protected String bio;
    protected Drawable photo;
    protected String url;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public Dev(@NonNull String name,@Nullable String job,@Nullable String bio,@Nullable Drawable photo,@Nullable String url) {
        this.name = name;
        this.job = job;
        this.bio = bio;
        this.photo = photo;
        this.url = url;
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Getters
    //
    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getJob() {
        return job;
    }

    @Nullable
    public String getBio() {
        return bio;
    }

    @Nullable
    public Drawable getPhoto() {
        return photo;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
    //endregion

}