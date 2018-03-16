package net.ej3.libs.aboutappdevlib.model;

import android.support.annotation.NonNull;

/**
 * @author E.J. Jiménez
 * @version 20180315
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Faq {

    //--------------------------------------------------------------------------
    //region Properties
    //
    protected String question;
    protected String answer;
    protected boolean open;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructor
    //
    public Faq(@NonNull String question,@NonNull String answer,boolean isOpen) {
        this.question = question;
        this.answer = answer;
        this.open = isOpen;
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

    public boolean isOpen() {
        return open;
    }
    //endregion

}