package net.ej3.libs.aboutappdevlib.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author E.J. Jiménez
 * @version 20180310
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Lib {

    //--------------------------------------------------------------------------
    //region Properties
    //
    protected String name;
    protected String author;
    protected String description;
    protected Drawable icon;
    protected String url;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public Lib(@NonNull String name,@Nullable String author,@Nullable String description,@Nullable Drawable icon,@Nullable String url) {
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

    @Nullable
    public Drawable getIcon() {
        return icon;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
    //endregion

}