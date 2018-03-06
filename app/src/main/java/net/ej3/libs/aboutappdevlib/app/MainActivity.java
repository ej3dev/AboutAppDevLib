package net.ej3.libs.aboutappdevlib.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import net.ej3.libs.aboutappdevlib.AboutAppFragment;
import net.ej3.libs.aboutappdevlib.AboutDevFragment;
import net.ej3.libs.aboutappdevlib.AboutFragment;
import net.ej3.libs.aboutappdevlib.AboutLibFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.addLogAdapter(new AndroidLogAdapter(
            PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)               // (Optional) Whether to show thread info or not. Default true
                .methodCount(3)                     // (Optional) How many method line to show. Default 2
                .methodOffset(5)                    // (Optional) Hides internal method calls up to offset. Default 5
                .tag(getString(R.string.app_name))  // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        ));


        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_NONE)
            .replace(R.id.content,buildFragment())
            .commitAllowingStateLoss();
    }

    private Fragment buildFragment() {
        return new AboutFragment.Builder()
            .withTabsMode(TabLayout.MODE_SCROLLABLE)
            .withTabsBackgroundColor(ContextCompat.getColor(this,R.color.md_blue_grey_600))
            .addPage("APP",buildAppFragment())
            .addPage("DEV",new AboutDevFragment.Builder().build())
            .addPage("LIB",new AboutLibFragment.Builder().build())
            .addPage(R.mipmap.ic_launcher,new FooFragment())
            .addPage(R.mipmap.ic_launcher_round,new FooFragment())
            .addPage("FOUR",new FooFragment())
            .addPage("FIVE",new FooFragment())
            .build();
    }

    @SuppressLint("DefaultLocale")
    private Fragment buildAppFragment() {
        return new AboutAppFragment.Builder()
            .withIcon(R.drawable.ic_launcher)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withThanks(getString(R.string.about_app_thanks_title),getString(R.string.about_app_thanks_text))
            .withChangelog(getString(R.string.about_app_changelog_title),getString(R.string.about_app_changelog_text))
            .build();
    }
}
