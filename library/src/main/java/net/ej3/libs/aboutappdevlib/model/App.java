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
    protected String packageName;
    protected String name;
    protected String description;
    @DrawableRes protected int icon;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public App(@Nullable String packageName,@NonNull String name,@Nullable String description,@DrawableRes int icon) {
        this.packageName = packageName;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Getters
    //
    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }
    //endregion

}