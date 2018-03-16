package net.ej3.libs.aboutappdevlib.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import net.ej3.libs.aboutappdevlib.model.Faq;

/**
 * @author E.J. Jiménez
 * @version 20180316
 */
@SuppressWarnings({"unused","SameParameterValue"})
public class FaqBuilder {

    //--------------------------------------------------------------------------
    //region Properties
    //
    private Context ctx;
    private String mQuestion;
    private String mAnswer;
    private boolean mOpen;
    //endregion


    //--------------------------------------------------------------------------
    //region Constructors
    //
    public FaqBuilder(@NonNull final Context ctx,@NonNull final String question) {
        this.ctx = ctx;
        mQuestion = question;
    }

    public FaqBuilder(@NonNull final Context ctx,@StringRes final int questionRes) {
        this.ctx = ctx;
        mQuestion = ctx.getString(questionRes);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Builder pattern
    //
    public FaqBuilder withAnswer(@NonNull final String answer) {
        mAnswer = answer;
        return this;
    }

    public FaqBuilder withAnswer(@StringRes final int answerRes) {
        mAnswer = ctx.getString(answerRes);
        return this;
    }

    public FaqBuilder isOpen(final boolean open) {
        mOpen = open;
        return this;
    }

    @NonNull
    public Faq build() {
        return new Faq(mQuestion,mAnswer == null ? "Forty two" : mAnswer,mOpen);
    }
    //endregion

}
