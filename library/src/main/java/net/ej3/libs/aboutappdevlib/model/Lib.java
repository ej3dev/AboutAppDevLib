package net.ej3.libs.aboutappdevlib.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Lib {

    //--------------------------------------------------------------------------
    //region Properties
    //
    protected String name;
    protected String author;
    protected String description;
    @DrawableRes protected int icon;
    protected String url;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public Lib(@NonNull String name,@Nullable String author,@Nullable String description,@DrawableRes int icon,@Nullable String url) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.icon = icon;
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
    public String getAuthor() {
        return author;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
    //endregion

}