package net.ej3.libs.aboutappdevlib.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import net.ej3.libs.aboutappdevlib.AboutAppFragment;
import net.ej3.libs.aboutappdevlib.AboutDevFragment;
import net.ej3.libs.aboutappdevlib.AboutFragment;
import net.ej3.libs.aboutappdevlib.AboutLibFragment;
import net.ej3.libs.aboutappdevlib.util.AppBuilder;
import net.ej3.libs.aboutappdevlib.util.ButtonFactory;
import net.ej3.libs.aboutappdevlib.util.LibBuilder;

/**
 * @author E.J. Jiménez
 * @version 20180308
 */
public class DemoActivity extends AppCompatActivity {

    //--------------------------------------------------------------------------
    //region Constants
    //
    public static final int DRAWER_EXAMPLE_1 = 0;
    public static final int DRAWER_EXAMPLE_2 = 1;
    public static final int DRAWER_EXAMPLE_3 = 2;
    public static final int DRAWER_ABOUT     = 3;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private Toolbar toolbar;
    //endregion


    //--------------------------------------------------------------------------
    //region Activity lifecycle
    //
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_activity);
        buildDrawer(savedInstanceState);
        show(buildAboutFragment());
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Utils
    //
    private String currentFragmentName() {
        FragmentManager fm = getSupportFragmentManager();
        int backStackCnt = fm.getBackStackEntryCount();
        if( backStackCnt > 0 ) return fm.getBackStackEntryAt(backStackCnt-1).getName();
        return "";
    }

    private void show(final Fragment fragment) {
        final String nextFragmentName = fragment.getClass().getName();
        final String currentFragmentName = currentFragmentName();
        if( currentFragmentName.equalsIgnoreCase(nextFragmentName) ) return;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if( currentFragmentName.equalsIgnoreCase(nextFragmentName) ) return;
                FragmentManager fm = getSupportFragmentManager();
                if( !fm.popBackStackImmediate(nextFragmentName,0) ) {
                    fm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .replace(R.id.content,fragment).commitAllowingStateLoss();
                }
            }
        });
    }

    private void buildDrawer(@Nullable Bundle savedInstanceState) {
        if( toolbar == null ) {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        new DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withAccountHeader(buildDrawerHeader(savedInstanceState),true)
            .withSavedInstance(savedInstanceState)
            .withHasStableIds(true)
            .withSelectedItem(-1)
            .addDrawerItems(
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_1).withName("Home").withIcon(GoogleMaterial.Icon.gmd_home).withSelectable(false),
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_2).withName("Example 1").withIcon(GoogleMaterial.Icon.gmd_filter_1).withSelectable(false),
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_3).withName("Example 2").withIcon(GoogleMaterial.Icon.gmd_filter_2).withSelectable(false),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withIdentifier(DRAWER_ABOUT).withName("About").withIcon(GoogleMaterial.Icon.gmd_info).withSelectable(false)
            )
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view,int position,IDrawerItem drawerItem) {
                    if( drawerItem == null ) return false;
                    switch( (int)drawerItem.getIdentifier() ) {
                        case DRAWER_EXAMPLE_1: show(buildExample1Fragment()); break;
                        case DRAWER_EXAMPLE_2: show(buildExample1Fragment()); break;
                        case DRAWER_EXAMPLE_3: show(buildExample1Fragment()); break;
                        case DRAWER_ABOUT: show(buildAboutFragment()); break;
                    }
                    return false;
                }
            }).withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                @Override
                public boolean onNavigationClickListener(View clickedView) {
                    onBackPressed();
                    return true;
                }
            }).build();
    }

    private AccountHeader buildDrawerHeader(@Nullable Bundle savedInstanceState) {
        return new AccountHeaderBuilder()
            .withActivity(this)
            .withCompactStyle(false)
            .withHeaderBackground(R.drawable.bg_drawer_header)
            .withProfileImagesClickable(false)
            .withSelectionListEnabled(false)
            .withSavedInstance(savedInstanceState)
            .addProfiles(new ProfileDrawerItem().withIdentifier(0).withName(R.string.drawer_profile_name).withEmail(R.string.drawer_profile_email).withIcon(R.mipmap.ic_launcher))
            .build();
    }

    private Fragment buildExample1Fragment() {
        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withIcon(R.drawable.ic_launcher)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withThanks(getString(R.string.about_app_thanks_title),getString(R.string.about_app_thanks_text))
            .withChangelog(getString(R.string.about_app_changelog_title),getString(R.string.about_app_changelog_text))
            .withActions(
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev"),
                ButtonFactory.openGithub(this,"ej3dev")
            )
            .build();

        return new AboutFragment.Builder()
                .withTabsMode(TabLayout.MODE_SCROLLABLE)
                .withTabsBackgroundColor(ContextCompat.getColor(this,R.color.md_blue_grey_600))
                .addPage("APP",appFragment)
                .addPage("DEV",new AboutDevFragment.Builder(this).build())
                .addPage("LIB",new AboutLibFragment.Builder(this).build())
                .addPage("FOUR",new FooFragment())
                .addPage("FIVE",new FooFragment())
                .build();
    }

    private Fragment buildAboutFragment() {
        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withIcon(R.drawable.ic_launcher)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withThanks(getString(R.string.about_app_thanks_title),getString(R.string.about_app_thanks_text))
            .withChangelog(getString(R.string.about_app_changelog_title),getString(R.string.about_app_changelog_text))
            .withActions(
                ButtonFactory.openGithub(this,getString(R.string.about_dev_github)),
                ButtonFactory.openBitbucket(this,getString(R.string.about_dev_github))
            )
            .build();

        Fragment devFragment = new AboutDevFragment.Builder(this)
            .withLogo(R.drawable.icon_dev)
            .withAuthor(R.string.about_dev_author)
            .withInfo(R.string.about_dev_info)
            .withActions(
                ButtonFactory.openPlayStoreDev(this,getString(R.string.about_dev_store))
            )
            .withApps(getString(R.string.about_dev_other_apps),
                new AppBuilder(this,R.string.about_dev_rea_name).withUrlOrPackageName(R.string.about_dev_rea_package).withDescription(R.string.about_dev_rea_description).withIcon(R.drawable.icon_app_rea).build(),
                new AppBuilder(this,R.string.about_dev_nac_name).withUrlOrPackageName(R.string.about_dev_nac_package).withDescription(R.string.about_dev_nac_description).withIcon(R.drawable.icon_app_nac).build(),
                new AppBuilder(this,R.string.about_dev_aps_name).withUrlOrPackageName(R.string.about_dev_aps_package).withDescription(R.string.about_dev_aps_description).withIcon(R.drawable.icon_app_aps).build(),
                new AppBuilder(this,R.string.about_dev_sqr_name).withUrlOrPackageName(R.string.about_dev_sqr_package).withDescription(R.string.about_dev_sqr_description).withIcon(R.drawable.icon_app_sqr).build()
            )
            .build();

        Fragment libFragment = new AboutLibFragment.Builder(this)
            .withInfo(getString(R.string.about_lib_info))
            .withLibs(getString(R.string.about_lib_libraries),
                new LibBuilder(this,R.string.about_lib_aboutappdevlib_name).withAuthor(R.string.about_lib_aboutappdevlib_author).withDescription(R.string.about_lib_aboutappdevlib_description).withUrl(R.string.about_lib_aboutappdevlib_url).withIcon(R.drawable.icon_lib_aboutappdevlib).build(),
                new LibBuilder(this,R.string.about_lib_material_drawer_name).withAuthor(R.string.about_lib_material_drawer_author).withDescription(R.string.about_lib_material_drawer_description).withUrl(R.string.about_lib_material_drawer_url).build(),
                new LibBuilder(this,R.string.about_lib_logger_name).withAuthor(R.string.about_lib_logger_author).withDescription(R.string.about_lib_logger_description).withUrl(R.string.about_lib_logger_url).build(),
                new LibBuilder(this,R.string.about_lib_leak_canary_name).withAuthor(R.string.about_lib_leak_canary_author).withDescription(R.string.about_lib_leak_canary_description).withUrl(R.string.about_lib_leak_canary_url).withIcon(R.drawable.leak_canary_icon).build()
            )
            .build();

        return new AboutFragment.Builder()
            .withTabsMode(TabLayout.MODE_FIXED)
            .withTabsBackgroundColor(ContextCompat.getColor(this,R.color.md_blue_grey_600))
            .addPage(getString(R.string.about_app_tab),appFragment)
            .addPage(getString(R.string.about_dev_tab),devFragment)
            .addPage(getString(R.string.about_lib_tab),libFragment)
            .build();
    }
    //enedregion

}
