package net.ej3.libs.aboutappdevlib.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.ej3.libs.aboutappdevlib.K;
import net.ej3.libs.aboutappdevlib.R;

/**
 * @author E.J. Jiménez
 * @version 20180312
 */
@SuppressWarnings({"unused","WeakerAccess","SameParameterValue"})
public class ButtonFactory {

    //--------------------------------------------------------------------------
    //region Factory pattern for custom button
    //
    @NonNull
    public static Button customButton(@NonNull final Context ctx,@DrawableRes final int icon,@NonNull final String label,final View.OnClickListener onClick) {
        Button output = new Button(ctx);
        output.setText(label);
        if( icon > 0 ) {
            output.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
            output.setCompoundDrawablePadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,ctx.getResources().getDisplayMetrics()));
        }
        if( onClick == null ) {
            output.setClickable(false);
        } else {
            output.setOnClickListener(onClick);
        }
        return output;
    }

    @NonNull
    public static Button customButton(@NonNull final Context ctx,@DrawableRes final int iconRes,@StringRes final int labelRes,final View.OnClickListener onClick) {
        return customButton(ctx,iconRes,ctx.getString(labelRes),onClick);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for system action buttons
    //
    @NonNull
    public static Button openUrl(@NonNull final Context ctx,@NonNull final String label,@NonNull final String url) {
        return customButton(ctx,R.drawable.ic_web_black_24dp,label,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(url));
            }
        });
    }

    @NonNull
    public static Button openUrl(@NonNull final Context ctx,@StringRes final int labelRes,@StringRes final int urlRes) {
        return openUrl(ctx,ctx.getString(labelRes),ctx.getString(urlRes));
    }

    @NonNull
    public static Button openAddContact(@NonNull final Context ctx,@NonNull final String name,@NonNull final String phone) {
        return customButton(ctx,R.drawable.ic_contacts_black_24dp,R.string.btn_contact,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME,name);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE,phone);
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button openAddContact(@NonNull final Context ctx,@StringRes final int nameRes,@StringRes final int phoneRes) {
        return openAddContact(ctx,ctx.getString(nameRes),ctx.getString(phoneRes));
    }

    @NonNull
    public static Button sendEmail(@NonNull final Context ctx,@NonNull final String email,@NonNull final String subject,@NonNull final String message) {
        return customButton(ctx,R.drawable.ic_email_black_24dp,R.string.btn_email,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button sendEmail(@NonNull final Context ctx,@StringRes final int emailRes,@StringRes final int subjectRes,@StringRes final int messageRes) {
        return sendEmail(ctx,ctx.getString(emailRes),ctx.getString(subjectRes),ctx.getString(messageRes));
    }

    @NonNull
    public static Button shareThisApp(@NonNull final Context ctx,@NonNull final String subject,@NonNull final String message) {
        return customButton(ctx,R.drawable.ic_share_black_24dp,R.string.btn_share,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button shareThisApp(@NonNull final Context ctx,@StringRes final int subjectRes,@StringRes final int messageRes) {
        return shareThisApp(ctx,ctx.getString(subjectRes),ctx.getString(messageRes));
    }

    @NonNull
    public static Button callPhone(@NonNull final Context ctx,@NonNull final String phone) {
        return customButton(ctx,R.drawable.ic_call_black_24dp,R.string.btn_call,new View.OnClickListener() {
            @RequiresPermission(Manifest.permission.CALL_PHONE)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(String.format(K.CALL_PHONE_URI,phone)));
                if( Util.resolveActivity(ctx,intent) ) {
                    Util.open(ctx,intent);
                }
            }
        });
    }

    @NonNull
    public static Button callPhone(@NonNull final Context ctx,@StringRes final int phoneRes) {
        return callPhone(ctx,ctx.getString(phoneRes));
    }

    @NonNull
    public static Button openMap(@NonNull final Context ctx,@Nullable final String place,final double latitude,final double longitude,final int zoom) {
        return customButton(ctx,R.drawable.ic_map_black_24dp,R.string.btn_address,new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(String.format(K.GEO_APP_URI,latitude,longitude,place == null ? latitude+", "+longitude : place,zoom)));
                if( Util.resolveActivity(ctx,intent) ) {
                    Util.open(ctx,intent);
                } else {
                    Util.open(ctx,Util.newIntent(String.format(K.GEO_WEB_URL,latitude,longitude,zoom)));
                }
            }
        });
    }

    @NonNull
    public static Button openMap(@NonNull final Context ctx,@StringRes final int placeRes,final double latitude,final double longitude,final int zoom) {
        return openMap(ctx,ctx.getString(placeRes),latitude,longitude,zoom);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for repositories
    //
    @NonNull
    public static Button openGithub(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_github_black,R.string.btn_github,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.GITHUB_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openGithub(@NonNull final Context ctx,@StringRes final int userRes) {
        return openGithub(ctx,ctx.getString(userRes));
    }

    @NonNull
    public static Button openBitbucket(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_bitbucket_black,R.string.btn_bitbucket,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.BITBUCKET_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openBitbucket(@NonNull final Context ctx,@StringRes final int userRes) {
        return openBitbucket(ctx,ctx.getString(userRes));
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for apps
    //
    @NonNull
    public static Button openInstagram(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_instagram_black,R.string.btn_instagram,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    Util.tryPackage(ctx,K.INSTAGRAM_APP_ID);
                    intent = Util.newIntent(K.INSTAGRAM_APP_URI,user);
                } catch(Exception e) {
                    intent = Util.newIntent(K.INSTAGRAM_WEB_URL,user);
                }
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button openInstagram(@NonNull final Context ctx,@StringRes final int userRes) {
        return openInstagram(ctx,ctx.getString(userRes));
    }

    @NonNull
    public static Button openFacebook(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_facebook_black,R.string.btn_facebook,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    Util.tryPackage(ctx,K.FACEBOOK_APP_ID);
                    intent = Util.newIntent(K.FACEBOOK_APP_URI,user);
                } catch(Exception e) {
                    intent = Util.newIntent(K.FACEBOOK_WEB_URL,user);
                }
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button openFacebook(@NonNull final Context ctx,@StringRes final int userRes) {
        return openFacebook(ctx,ctx.getString(userRes));
    }

    @NonNull
    public static Button openTwitter(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_twitter_black,R.string.btn_twitter,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    Util.tryPackage(ctx,K.TWITTER_APP_ID);
                    intent = Util.newIntent(K.TWITTER_APP_URI,user);
                } catch(Exception e) {
                    intent = Util.newIntent(K.TWITTER_WEB_URL,user);
                }
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button openTwitter(@NonNull final Context ctx,@StringRes final int userRes) {
        return openTwitter(ctx,ctx.getString(userRes));
    }

    @NonNull
    public static Button openGooglePlus(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_google_plus_black,R.string.btn_google_plus,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    Util.tryPackage(ctx,K.GOOGLE_PLUS_APP_ID);
                    intent = Util.newIntent(K.GOOGLE_PLUS_APP_URI,user);
                } catch(Exception e) {
                    intent = Util.newIntent(K.GOOGLE_PLUS_WEB_URL,user);
                }
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button openGooglePlus(@NonNull final Context ctx,@StringRes final int userRes) {
        return openGooglePlus(ctx,ctx.getString(userRes));
    }

    @NonNull
    public static Button openYoutubeChannel(@NonNull final Context ctx,@NonNull final String channel) {
        return customButton(ctx,R.drawable.ic_youtube_black,R.string.btn_youtube,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.YOUTUBE_CHANNEL_WEB_URL,channel));
            }
        });
    }

    @NonNull
    public static Button openYoutubeChannel(@NonNull final Context ctx,@StringRes final int channelRes) {
        return openYoutubeChannel(ctx,ctx.getString(channelRes));
    }

    @NonNull
    public static Button openYoutubeUser(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_youtube_black,R.string.btn_youtube,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.YOUTUBE_USER_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openYoutubeUser(@NonNull final Context ctx,@StringRes final int userRes) {
        return openYoutubeUser(ctx,ctx.getString(userRes));
    }

    @NonNull
    public static Button openLinkedIn(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_linkedin_black,R.string.btn_linkedin,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    Util.tryPackage(ctx,K.LINKEDIN_APP_ID);
                    intent = Util.newIntent(K.LINKEDIN_APP_URI,user);
                } catch(Exception e) {
                    intent = Util.newIntent(K.LINKEDIN_WEB_URL,user);
                }
                Util.open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Button openLinkedIn(@NonNull final Context ctx,@StringRes final int userRes) {
        return openLinkedIn(ctx,ctx.getString(userRes));

    }

    @NonNull
    public static Button openSkype(@NonNull final Context ctx,@NonNull final String phone) {
        return customButton(ctx,R.drawable.ic_skype_black,R.string.btn_skype,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    Util.tryPackage(ctx,K.SKYPE_APP_ID);
                    Util.open(ctx,Util.newIntent(K.SKYPE_APP_URI,phone));
                } catch(Exception e) {
                    Toast.makeText(ctx,"Skype app missing",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    public static Button openSkype(@NonNull final Context ctx,@StringRes final int phoneRes) {
        return openSkype(ctx,ctx.getString(phoneRes));
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for Play Store
    //
    @NonNull
    public static Button openPlayStoreDev(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,R.drawable.ic_google_play_store_black,R.string.btn_play_store,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.PLAY_STORE_DEV_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openPlayStoreDev(@NonNull final Context ctx,@StringRes final int userRes) {
        return openPlayStoreDev(ctx,ctx.getString(userRes));

    }

    @NonNull
    public static Button openPlayStoreAppPage(@NonNull final Context ctx,@NonNull final String app) {
        return customButton(ctx,R.drawable.ic_google_play_store_black,R.string.btn_play_store,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Util.newIntent(K.PLAY_STORE_APP_URI,app);
                if( Util.resolveActivity(ctx,intent) ) {
                    Util.open(ctx,intent);
                } else {
                    Util.open(ctx,Util.newIntent(K.PLAY_STORE_APP_WEB_URL,app));
                }
            }
        });
    }

    @NonNull
    public static Button openPlayStoreAppPage(@NonNull final Context ctx,@StringRes final int appRes) {
        return openPlayStoreAppPage(ctx,ctx.getString(appRes));
    }

    @NonNull
    public static Button openPlayStoreAppsList(@NonNull final Context ctx,@NonNull final String app) {
        return customButton(ctx,R.drawable.ic_google_play_store_black,R.string.btn_play_store,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Util.newIntent(K.PLAY_STORE_APP_LIST_URI,app);
                if( Util.resolveActivity(ctx,intent) ) {
                    Util.open(ctx,intent);
                } else {
                    Util.open(ctx,Util.newIntent(K.PLAY_STORE_APP_LIST_URL,app));
                }
            }
        });
    }

    @NonNull
    public static Button openPlayStoreAppsList(@NonNull final Context ctx,@StringRes final int appRes) {
        return openPlayStoreAppsList(ctx,ctx.getString(appRes));
    }
    //endregion

}