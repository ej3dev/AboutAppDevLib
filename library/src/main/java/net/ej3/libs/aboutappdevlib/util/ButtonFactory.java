package net.ej3.libs.aboutappdevlib.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author E.J. Jiménez
 * @version 20180307
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class ButtonFactory {

    //--------------------------------------------------------------------------
    //region Constants
    //
    private static final String CALL_PHONE_URI          = "tel:%s";

    private static final String GITHUB_WEB_URL          = "https://github.com/%s";

    private static final String BITBUCKET_WEB_URL       = "https://bitbucket.org/%s";

    private static final String INSTAGRAM_WEB_URL       = "http://instagram.com/%s";
    private static final String INSTAGRAM_APP_URI       = "http://instagram.com/_u/%s";
    private static final String INSTAGRAM_APP_ID        = "com.instagram.android";

    private static final String FACEBOOK_WEB_URL        = "https://www.facebook.com/%s";
    private static final String FACEBOOK_APP_URI        = "fb://page/%s";
    private static final String FACEBOOK_APP_ID         = "com.facebook.katana";

    private static final String TWITTER_WEB_URL         = "https://twitter.com/%s";
    private static final String TWITTER_APP_URI         = "twitter://user?user_id=%s";
    private static final String TWITTER_APP_ID          = "com.twitter.android";

    private static final String GOOGLE_PLUS_WEB_URL     = "https://plus.google.com/%s";
    private static final String GOOGLE_PLUS_APP_URI     = "https://plus.google.com/%s";
    private static final String GOOGLE_PLUS_APP_ID      = "com.google.android.apps.plus";

    private static final String LINKEDIN_WEB_URL        = "https://www.linkedin.com/in/%s";
    private static final String LINKEDIN_APP_URI        = "linkedin://profile/%s";
    private static final String LINKEDIN_APP_ID         = "com.linkedin.android";

    private static final String SKYPE_APP_URI           = "skype:%s";
    private static final String SKYPE_APP_ID            = "com.skype.raider";

    private static final String YOUTUBE_USER_WEB_URL    = "https://www.youtube.com/user/%s";
    private static final String YOUTUBE_CHANNEL_WEB_URL = "https://www.youtube.com/channel/%s";

    private static final String PLAY_STORE_DEV_WEB_URL  = "https://play.google.com/store/apps/dev?id=%s";
    private static final String PLAY_STORE_APP_WEB_URL  = "https://play.google.com/store/apps/details?id=%s";
    private static final String PLAY_STORE_APP_URI      = "market://details?id=%s";
    private static final String PLAY_STORE_APP_LIST_URI = "market://search?q=pub:%s";
    private static final String PLAY_STORE_APP_LIST_URL = "https://play.google.com/store/search?q=%s";
    //endregion


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
                open(ctx,newIntent(url));
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
                open(ctx,intent);
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
                open(ctx,intent);
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
                open(ctx,intent);
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
                intent.setData(Uri.parse(String.format(CALL_PHONE_URI,phone)));
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
                open(ctx,newIntent(GITHUB_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openBitbucket(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"Bitbucket",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(ctx,newIntent(BITBUCKET_WEB_URL,user));
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
                    tryPackage(ctx,INSTAGRAM_APP_ID);
                    intent = newIntent(INSTAGRAM_APP_URI,user);
                } catch(Exception e) {
                    intent = newIntent(INSTAGRAM_WEB_URL,user);
                }
                open(ctx,intent);
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
                    tryPackage(ctx,FACEBOOK_APP_ID);
                    intent = newIntent(FACEBOOK_APP_URI,user);
                } catch(Exception e) {
                    intent = newIntent(FACEBOOK_WEB_URL,user);
                }
                open(ctx,intent);
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
                    tryPackage(ctx,TWITTER_APP_ID);
                    intent = newIntent(TWITTER_APP_URI,user);
                } catch(Exception e) {
                    intent = newIntent(TWITTER_WEB_URL,user);
                }
                open(ctx,intent);
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
                    tryPackage(ctx,GOOGLE_PLUS_APP_ID);
                    intent = newIntent(GOOGLE_PLUS_APP_URI,user);
                } catch(Exception e) {
                    intent = newIntent(GOOGLE_PLUS_WEB_URL,user);
                }
                open(ctx,intent);
            }
        });
    }

    @NonNull
    public static Intent openYoutubeChannel(String user) {
        return newIntent(YOUTUBE_CHANNEL_WEB_URL,user);
    }

    @NonNull
    public static Intent openYoutubeUser(String user) {
        return newIntent(YOUTUBE_USER_WEB_URL,user);
    }

    @NonNull
    public static Button openLinkedIn(@NonNull final Context ctx,@NonNull final String user) {
        return customButton(ctx,0,"LinkedIn",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    tryPackage(ctx,LINKEDIN_APP_ID);
                    intent = newIntent(LINKEDIN_APP_URI,user);
                } catch(Exception e) {
                    intent = newIntent(LINKEDIN_WEB_URL,user);
                }
                open(ctx,intent);
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
                    tryPackage(ctx,SKYPE_APP_ID);
                    open(ctx,newIntent(SKYPE_APP_URI,phone));
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
                open(ctx,newIntent(PLAY_STORE_DEV_WEB_URL,user));
            }
        });
    }

    @NonNull
    public static Button openPlayStoreAppPage(@NonNull final Context ctx,@NonNull final String app) {
        return customButton(ctx,0,"Play Store",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = newIntent(PLAY_STORE_APP_URI,app);
                if( resolveActivity(ctx,intent) ) {
                    open(ctx,intent);
                } else {
                    open(ctx,newIntent(PLAY_STORE_APP_WEB_URL,app));
                }
            }
        });
    }

    @NonNull
    public static Button openPlayStoreAppsList(@NonNull final Context ctx,@NonNull final String app) {
        return customButton(ctx,0,"Play Store",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = newIntent(PLAY_STORE_APP_LIST_URI,app);
                if( resolveActivity(ctx,intent) ) {
                    open(ctx,intent);
                } else {
                    open(ctx,newIntent(PLAY_STORE_APP_LIST_URL,app));
                }
            }
        });
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private static void tryPackage(Context ctx,String packageName) throws PackageManager.NameNotFoundException {
        ctx.getPackageManager().getPackageInfo(packageName,0);
    }

    private static boolean resolveActivity(Context ctx,Intent intent) {
        return (intent.resolveActivity(ctx.getPackageManager()) != null);
    }

    @NonNull
    private static Intent newIntent(Uri uri) {
        return new Intent(Intent.ACTION_VIEW,uri);
    }

    @NonNull
    private static Intent newIntent(String url) {
        return newIntent(Uri.parse(url));
    }

    @NonNull
    private static Intent newIntent(String url,String user) {
        return newIntent(Uri.parse(String.format(url,user)));
    }

    private static void open(Context ctx,Intent intent) {
        try {
            ctx.startActivity(intent);
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

    private static void open(Context ctx,Uri uri) {
        open(ctx,newIntent(uri));
    }
    //endregion

}
