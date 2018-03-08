package net.ej3.libs.aboutappdevlib.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Faq {

    //--------------------------------------------------------------------------
    //region Properties
    //
    protected String question;
    protected String answer;
    protected String url;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public Faq(@NonNull String question,@NonNull String answer,@Nullable String url) {
        this.question = question;
        this.answer = answer;
        this.url = url;
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Getters
    //
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUrl() {
        return url;
    }
    //endregion

}