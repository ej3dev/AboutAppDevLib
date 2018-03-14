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
import net.ej3.libs.aboutappdevlib.AboutLibFragment;
import net.ej3.libs.aboutappdevlib.AboutTabsFragment;
import net.ej3.libs.aboutappdevlib.util.AppBuilder;
import net.ej3.libs.aboutappdevlib.util.ButtonFactory;
import net.ej3.libs.aboutappdevlib.util.DevBuilder;
import net.ej3.libs.aboutappdevlib.util.LibBuilder;

/**
 * @author E.J. Jiménez
 * @version 20180313
 */
public class DemoActivity extends AppCompatActivity {

    //--------------------------------------------------------------------------
    //region Constants
    //
    public static final int DRAWER_HOME      = 0;
    public static final int DRAWER_EXAMPLE_1 = 1;
    public static final int DRAWER_EXAMPLE_2 = 2;
    public static final int DRAWER_EXAMPLE_3 = 3;
    public static final int DRAWER_EXAMPLE_4 = 4;
    public static final int DRAWER_EXAMPLE_5 = 5;
    public static final int DRAWER_ABOUT     = 6;
    //endregion


    //--------------------------------------------------------------------------
    //region Components
    //
    private Toolbar toolbar;
    private Drawer drawer;
    //endregion


    //--------------------------------------------------------------------------
    //region Activity lifecycle
    //
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo_activity);
        buildDrawer(savedInstanceState);
        show(buildHomeFragment());
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Navigation drawer
    //
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

    private void buildDrawer(@Nullable Bundle savedInstanceState) {
        if( toolbar == null ) {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        drawer = new DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withAccountHeader(buildDrawerHeader(savedInstanceState),true)
            .withSavedInstance(savedInstanceState)
            .withHasStableIds(true)
            .withSelectedItem(-1)
            .addDrawerItems(
                new PrimaryDrawerItem().withIdentifier(DRAWER_HOME).withName(R.string.drawer_home).withIcon(GoogleMaterial.Icon.gmd_home).withSelectable(false),
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_1).withName(R.string.drawer_example_1).withIcon(GoogleMaterial.Icon.gmd_filter_1).withSelectable(false),
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_2).withName(R.string.drawer_example_2).withIcon(GoogleMaterial.Icon.gmd_filter_2).withSelectable(false),
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_3).withName(R.string.drawer_example_3).withIcon(GoogleMaterial.Icon.gmd_filter_3).withSelectable(false),
                new PrimaryDrawerItem().withIdentifier(DRAWER_EXAMPLE_4).withName(R.string.drawer_example_4).withIcon(GoogleMaterial.Icon.gmd_filter_4).withSelectable(false),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withIdentifier(DRAWER_ABOUT).withName(R.string.drawer_about).withIcon(GoogleMaterial.Icon.gmd_info).withSelectable(false)
            )
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view,int position,IDrawerItem drawerItem) {
                    if( drawerItem == null ) return false;
                    switch( (int)drawerItem.getIdentifier() ) {
                        case DRAWER_HOME: show(buildHomeFragment()); break;
                        case DRAWER_EXAMPLE_1: show(buildFixedTabsFragment()); break;
                        case DRAWER_EXAMPLE_2: show(buildScrollableTabsFragment()); break;
                        case DRAWER_EXAMPLE_3: show(buildWithoutTabsFragment()); break;
                        case DRAWER_EXAMPLE_4: show(buildCustomColorsFragment()); break;
                        case DRAWER_ABOUT: show(buildAboutFragment()); break;
                    }
                    return false;
                }
            })
            .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                @Override
                public boolean onNavigationClickListener(View clickedView) {
                    onBackPressed();
                    return true;
                }
            })
            .build();
    }
    //endregion

    //--------------------------------------------------------------------------
    //region Fragments
    //
    Fragment buildHomeFragment() {
        drawer.getDrawerLayout().setStatusBarBackground(R.color.md_blue_grey_700);
        toolbar.setBackgroundResource(R.color.md_blue_grey_500);
        toolbar.setTitle(R.string.drawer_home);

        return new HomeFragment();
    }

    Fragment buildFixedTabsFragment() {
        drawer.getDrawerLayout().setStatusBarBackground(R.color.md_blue_grey_700);
        toolbar.setBackgroundResource(R.color.md_blue_grey_500);
        toolbar.setTitle(R.string.drawer_example_1);

        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withIcon(R.drawable.icon_app)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withThanks(getString(R.string.about_app_thanks_title),getString(R.string.about_app_thanks_text))
            .withActions(
                ButtonFactory.openPlayStoreAppPage(this,BuildConfig.APPLICATION_ID),
                ButtonFactory.shareThisApp(this,R.string.about_app_share_subject,R.string.about_app_share_message)
            )
            .build();

        Fragment devFragment = new AboutDevFragment.Builder(this)
            .withLogo(R.drawable.icon_dev)
            .withAuthor(R.string.about_dev_author)
            .withInfo(R.string.about_dev_info)
            .withActions(
                ButtonFactory.openPlayStoreDev(this,R.string.about_dev_store),
                ButtonFactory.openGithub(this,R.string.about_dev_github)
            )
            .withApps(getString(R.string.about_dev_other_apps),
                new AppBuilder(this,R.string.about_dev_rea_name).withUrlOrPackageName(R.string.about_dev_rea_package).withDescription(R.string.about_dev_rea_description).withIcon(R.drawable.icon_app_rea).build(),
                new AppBuilder(this,R.string.about_dev_nac_name).withUrlOrPackageName(R.string.about_dev_nac_package).withDescription(R.string.about_dev_nac_description).withIcon(R.drawable.icon_app_nac).build(),
                new AppBuilder(this,R.string.about_dev_aps_name).withUrlOrPackageName(R.string.about_dev_aps_package).withDescription(R.string.about_dev_aps_description).withIcon(R.drawable.icon_app_aps).build(),
                new AppBuilder(this,R.string.about_dev_sqr_name).withUrlOrPackageName(R.string.about_dev_sqr_package).withDescription(R.string.about_dev_sqr_description).withIcon(R.drawable.icon_app_sqr).build()
            )
            .build();

        return new AboutTabsFragment.Builder(this)
            .withTabsMode(TabLayout.MODE_FIXED)
            .withTabsBackgroundColor(ContextCompat.getColor(this,R.color.md_blue_grey_600))
            .addPage("Application",appFragment)
            .addPage("Developer",devFragment)
            .build();
    }

    Fragment buildScrollableTabsFragment() {
        drawer.getDrawerLayout().setStatusBarBackground(R.color.md_blue_grey_700);
        toolbar.setBackgroundResource(R.color.md_blue_grey_500);
        toolbar.setTitle(R.string.drawer_example_2);

        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withIcon(R.drawable.icon_app)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withChangelog(getString(R.string.about_app_changelog_title),getString(R.string.about_app_changelog_text))
            .build();

        Fragment teamFragment = new AboutDevFragment.Builder(this)
            .withInfo("This is a list of programmers who <b>have not worked</b> on this project. Even so, they have all my admiration and respect :-)")
            .withDevs("Programmers",
                new DevBuilder(this,"Paco Menéndez").withJob("Game programmer").withBio(" Spanish computer game programmer who wrote games for 8-bit computers. His most famous work is La abadía del crimen which is regarded as one of the best games made for 8 bit computers.").withUrl("https://en.wikipedia.org/wiki/Paco_Men%C3%A9ndez").build(),
                new DevBuilder(this,"Sid Meier").withJob("Game programmer & designer").withBio("Canadian-American programmer, designer, and producer of several strategy video games and simulation video games, including the Civilization series").withUrl("https://en.wikipedia.org/wiki/Sid_Meier").build(),
                new DevBuilder(this,"John Carmack").withJob("Programmer").withBio("American computer programmer, engineer, and businessman. He co-founded id Software. Carmack was the lead programmer of the id video games Commander Keen, Wolfenstein 3D, Doom, Quake, Rage and their sequels.").withUrl("https://en.wikipedia.org/wiki/John_Carmack").build(),
                new DevBuilder(this,"Jon Ritman").withJob("Game programmer").withBio("Jon Ritman is a software developer, notable for his work on major 1980s video games. Working primarily on games for the ZX Spectrum and Amstrad CPC home computer range.").withUrl("https://en.wikipedia.org/wiki/Jon_Ritman").build(),
                new DevBuilder(this,"Måns Olson").withJob("Game programmer").withBio("Swedish compute game programmer author of the best games I've seen participate in the Java 4K contest").build()
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

        return new AboutTabsFragment.Builder(this)
            .withTabsMode(TabLayout.MODE_SCROLLABLE)
            .addPage("Application",appFragment)
            .addPage("Developers",teamFragment)
            .addPage("Libraries",libFragment)
            .addPage("Misc.",new HomeFragment())
            .build();
    }

    Fragment buildWithoutTabsFragment() {
        drawer.getDrawerLayout().setStatusBarBackground(R.color.md_blue_grey_700);
        toolbar.setBackgroundResource(R.color.md_blue_grey_500);
        toolbar.setTitle(R.string.drawer_example_3);

        return new AboutDevFragment.Builder(this)
            .withLogo(R.drawable.icon_dev)
            .withAuthor(R.string.about_dev_author)
            .withInfo(R.string.about_dev_info)
            .withActions(
                ButtonFactory.openPlayStoreDev(this,R.string.about_dev_store),
                ButtonFactory.openGithub(this,R.string.about_dev_github),
                ButtonFactory.openMap(this,"Estadio Helmántico",40.995278,-5.665,18)
            )
            .withApps(getString(R.string.about_dev_other_apps),
                new AppBuilder(this,R.string.about_dev_rea_name).withUrlOrPackageName(R.string.about_dev_rea_package).withDescription(R.string.about_dev_rea_description).withIcon(R.drawable.icon_app_rea).build(),
                new AppBuilder(this,R.string.about_dev_nac_name).withUrlOrPackageName(R.string.about_dev_nac_package).withDescription(R.string.about_dev_nac_description).withIcon(R.drawable.icon_app_nac).build(),
                new AppBuilder(this,R.string.about_dev_aps_name).withUrlOrPackageName(R.string.about_dev_aps_package).withDescription(R.string.about_dev_aps_description).withIcon(R.drawable.icon_app_aps).build(),
                new AppBuilder(this,R.string.about_dev_sqr_name).withUrlOrPackageName(R.string.about_dev_sqr_package).withDescription(R.string.about_dev_sqr_description).withIcon(R.drawable.icon_app_sqr).build(),
                new AppBuilder(this,"AboutAppDevLib Demo").withUrlOrPackageName(BuildConfig.APPLICATION_ID).withDescription("Sample app demo for AboutAppDevLib library").withIcon(R.drawable.icon_lib_aboutappdevlib).build()
            )
            .build();
    }

    Fragment buildCustomColorsFragment() {
        drawer.getDrawerLayout().setStatusBarBackground(R.color.md_black_1000);
        toolbar.setBackgroundResource(R.color.md_black_1000);
        toolbar.setTitle(R.string.drawer_example_4);

        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withBackgroundColorRes(R.color.md_grey_900)
            .withTextColors(0xff66ccff,0x8866ccff,0xff66ccff)
            .withIcon(R.drawable.icon_app)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withThanks(getString(R.string.about_app_thanks_title),getString(R.string.about_app_thanks_text))
            .withChangelog(getString(R.string.about_app_changelog_title),getString(R.string.about_app_changelog_text))
            .withActions(
                ButtonFactory.openPlayStoreAppPage(this,BuildConfig.APPLICATION_ID),
                ButtonFactory.shareThisApp(this,R.string.about_app_share_subject,R.string.about_app_share_message)
            )
            .build();

        Fragment devFragment = new AboutDevFragment.Builder(this)
            .withBackgroundColorRes(R.color.md_grey_900)
            .withTextColors(0xffffcccc,0x88ffcccc,0xffffcccc)
            .withLogo(R.drawable.icon_dev)
            .withAuthor(R.string.about_dev_author)
            .withInfo(R.string.about_dev_info)
            .withActions(
                ButtonFactory.openPlayStoreDev(this,R.string.about_dev_store),
                ButtonFactory.openGithub(this,R.string.about_dev_github)
            )
            .build();

        return new AboutTabsFragment.Builder(this)
            .withTabsMode(TabLayout.MODE_FIXED)
            .withTabsBackgroundColorRes(R.color.md_black_1000)
            .withTabsNormalColor(0x88ffaaaa)
            .withTabsSelectedColor(0xffffaaaa)
            .withTabsIndicatorColor(0xffff0000)
            .addPage(R.string.about_app_tab,appFragment)
            .addPage(R.string.about_dev_tab,devFragment)
            .build();
    }

    Fragment buildAboutFragment() {
        drawer.getDrawerLayout().setStatusBarBackground(R.color.md_blue_grey_700);
        toolbar.setBackgroundResource(R.color.md_blue_grey_500);
        toolbar.setTitle(R.string.drawer_about);

        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withIcon(R.drawable.icon_app)
            .withName(getString(R.string.about_app_name))
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(getString(R.string.about_app_copyright))
            .withThanks(getString(R.string.about_app_thanks_title),getString(R.string.about_app_thanks_text))
            .withChangelog(getString(R.string.about_app_changelog_title),getString(R.string.about_app_changelog_text))
            .withActions(
                ButtonFactory.openPlayStoreAppPage(this,BuildConfig.APPLICATION_ID),
                ButtonFactory.shareThisApp(this,R.string.about_app_share_subject,R.string.about_app_share_message)
            )
            .build();

        Fragment devFragment = new AboutDevFragment.Builder(this)
            .withLogo(R.drawable.icon_dev)
            .withAuthor(R.string.about_dev_author)
            .withInfo(R.string.about_dev_info)
            .withActions(
                ButtonFactory.openPlayStoreDev(this,R.string.about_dev_store),
                ButtonFactory.openGithub(this,R.string.about_dev_github)
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

        return new AboutTabsFragment.Builder(this)
            .withTabsMode(TabLayout.MODE_FIXED)
            .withTabsBackgroundColor(ContextCompat.getColor(this,R.color.md_blue_grey_600))
            .addPage(R.string.about_app_tab,appFragment)
            .addPage(R.string.about_dev_tab,devFragment)
            .addPage(R.string.about_lib_tab,libFragment)
            .build();
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

    void show(final Fragment fragment) {
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
    //endregion

}
