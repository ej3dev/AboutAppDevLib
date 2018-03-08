package net.ej3.libs.aboutappdevlib.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.ej3.libs.aboutappdevlib.K;

/**
 * @author E.J. Jiménez
 * @version 20180308
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
        if( icon > 0 ) output.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
        if( onClick == null ) {
            output.setClickable(false);
        } else {
            output.setOnClickListener(onClick);
        }
        return output;
    }

    @NonNull
    public static Button customButton(@NonNull final Context ctx,@DrawableRes final int icon,@StringRes final int label,final View.OnClickListener onClick) {
        return customButton(ctx,icon,ctx.getString(label),onClick);
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for system action buttons
    //
    @NonNull
    public static Button openUrl(@NonNull final Context ctx,@NonNull final String label,@NonNull final String url) {
        return customButton(ctx,0,label,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(url));
            }
        });
    }

    @NonNull
    public static Button openAddContact(@NonNull final Context ctx,@NonNull final String name,@NonNull final String phone) {
        return customButton(ctx,0,"Add contact",new View.OnClickListener() {
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
    public static Button sendEmail(@NonNull final Context ctx,@NonNull final String email,@NonNull final String subject,@NonNull final String message) {
        return customButton(ctx,0,"Email",new View.OnClickListener() {
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
    public static Button shareThisApp(@NonNull final Context ctx,@NonNull final String subject,@NonNull final String message) {
        return customButton(ctx,0,"Share",new View.OnClickListener() {
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
    public static Button callPhone(@NonNull final Context ctx,@NonNull final String phone) {
        return customButton(ctx,0,"Phone",new View.OnClickListener() {
            @RequiresPermission(Manifest.permission.CALL_PHONE)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(String.format(K.CALL_PHONE_URI,phone)));
                ctx.startActivity(intent);
            }
        });
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for repositories
    //
    @NonNull
    public static Button openGithub(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Github",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.GITHUB_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openBitbucket(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Bitbucket",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.BITBUCKET_WEB_URL,user));
            }
        });
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for apps
    //
    @NonNull
    public static Button openInstagram(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Instagram",new View.OnClickListener() {
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
    public static Button openFacebook(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Facebook",new View.OnClickListener() {
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
    public static Button openTwitter(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Twitter",new View.OnClickListener() {
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
    public static Button openGooglePlus(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Google Plus",new View.OnClickListener() {
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
    public static Intent openYoutubeChannel(String user) {
        return Util.newIntent(K.YOUTUBE_CHANNEL_WEB_URL,user);
    }

    @NonNull
    public static Intent openYoutubeUser(String user) {
        return Util.newIntent(K.YOUTUBE_USER_WEB_URL,user);
    }

    @NonNull
    public static Button openLinkedIn(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"LinkedIn",new View.OnClickListener() {
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
    public static Button openSkype(@NonNull final Context ctx,@NonNull final String phone) {
        return customButton(ctx,0,"Skype",new View.OnClickListener() {
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
    //endregion


    //--------------------------------------------------------------------------
    //region Factory pattern for Play Store
    //
    @NonNull
    public static Button openPlayStoreDev(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Play Store",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.open(ctx,Util.newIntent(K.PLAY_STORE_DEV_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openPlayStoreAppPage(@NonNull final Context ctx,@NonNull final String app) {
        return customButton(ctx,0,"Play Store",new View.OnClickListener() {
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
    public static Button openPlayStoreAppsList(@NonNull final Context ctx,@NonNull final String app) {
        return customButton(ctx,0,"Play Store",new View.OnClickListener() {
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
    //endregion

}
