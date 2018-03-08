package net.ej3.libs.aboutappdevlib.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class App {

    //--------------------------------------------------------------------------
    //region Properties
    //
    protected String urlOrPackageName;
    protected String name;
    protected String description;
    @DrawableRes protected int icon;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public App(@Nullable String urlOrPackageName,@NonNull String name,@Nullable String description,@DrawableRes int icon) {
        this.urlOrPackageName = urlOrPackageName;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Getters
    //
    @Nullable
    public String getUrlOrPackageName() {
        return urlOrPackageName;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }
    //endregion

}