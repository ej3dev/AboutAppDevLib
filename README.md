[![](https://jitpack.io/v/ej3dev/AboutAppDevLib.svg)](https://jitpack.io/#ej3dev/AboutAppDevLib)
![](https://jitpack.io/v/ej3dev/AboutAppDevLib/month.svg)
[![](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/ej3dev/AboutAppDevLib/master/LICENSE)
[![](https://ga-beacon.appspot.com/UA-56739606-2/readme?pixel)](https://github.com/ej3dev/AboutAppDevLib)
<img align="right" src="https://raw.githubusercontent.com/ej3dev/AboutAppDevLib/master/app/src/main/res/drawable/icon_lib_aboutappdevlib.png" width="128" height="128"/>

AboutAppDevLib
==============

**AboutAppDevLib** is a library to build easily a fully featured &#34;About&#34; screen for your app. With a few lines of code you can create a screens with info about you, your team, your app, third party libraries used or build a section with Frequently Asked Questions.

### Features

* Easy to implement
* Material design with tabs support
* Include `AboutAppFragment` class to create screen with app info like name, versión, changelog, thanks, repos, privacy info...
* Include `AboutDevFragment` class to create screen with developers info: personal info, team members, others apps...
* Include `AboutLibFragment` class to build a list with third party libraries used in the app
* Include `AboutFaqFragment` class to easily build a Frequently Asked Questions (FAQ) screen
* Include `ButtonFactory` class to generate buttons with commons actions: view URLs, location maps, launch apps, send emails, share app...
* Open, flexible and extensible API
 
### Sample app

Take a look to the sample app to see a working example of the possibilities of **AboutAppDevLib** library:
<br><a href="https://play.google.com/store/apps/details?id=net.ej3.libs.aboutappdevlib.app"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_gb/badges/images/generic/en_badge_web_generic.png" height="60" /></a>

### Screenshots

<img align="right" src="https://raw.githubusercontent.com/ej3dev/AboutAppDevLib/master/art/AboutAppFragment_01_400x658.jpg"/>
&nbsp;
<img align="right" src="https://raw.githubusercontent.com/ej3dev/AboutAppDevLib/master/art/AboutDevFragment_01_400x658.jpg"/>


Setup
-----

### Requirements

**AboutAppDevLib** requires `minSdkVersion` to be set to 21 or above.

### Dependencies

* **Add the [JitPack](https://jitpack.io) repository to your root `build.gradle` file at the end of repositories:**

```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

* **Add the dependency to your module `build.gradle` file:**

```gradle
dependencies {
    implementation 'com.github.ej3dev:AboutAppDevLib:v1.1.0'
}
```

### Clone repo & run source code

There is a sample app in the [app/](https://github.com/ej3dev/AboutAppDevLib/tree/master/app) directory that demonstrates most of the features of **AboutAppDevLib** library. Load the project in [Android Studio](https://en.wikipedia.org/wiki/Android_Studio) and run it on your Android device to see a demonstration.

Before you can run the sample app, you must create a project in the [Firebase console](https://console.firebase.google.com/). Add an Android app to the project, and copy the generated `google-services.json` file into the [app/](https://github.com/ej3dev/AboutAppDevLib/tree/master/app) directory.


Use
---

### Basic implementation

```java
public class BasicExampleActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ...
        
        //
        // Create Fragments with build predefined classes or your custom implementations
        //
        Fragment appFragment = new AboutAppFragment.Builder(this)
            .withIcon(R.drawable.ic_launcher)
            .withName(R.string.about_app_name)
            .withVersion(getString(R.string.about_app_version,BuildConfig.VERSION_NAME,BuildConfig.VERSION_CODE))
            .withCopyright(R.string.about_app_copyright);
        
        Fragment devFragment = new AboutDevFragment.Builder(this)
            .withLogo(R.drawable.icon_dev)
            .withAuthor(R.string.about_dev_author)
            .withInfo(R.string.about_dev_info);
        
        //
        // Create "About Screen" with tabs
        //
        Fragment aboutFragment = new AboutFragment.Builder(this)
            .withTabsMode(TabLayout.MODE_FIXED)
            .addPage("APP",appFragment)
            .addPage("DEV",devFragment)
            .build();
        
        //
        // Show "About Screen" in your layout
        //
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.content,aboutFragment).commitAllowingStateLoss();
            }
        });

        // ...
        
    }
}
```

### More examples

Take a look to [`DemoActivity`](https://github.com/ej3dev/AboutAppDevLib/blob/master/app/src/main/java/net/ej3/libs/aboutappdevlib/app/DemoActivity.java) class to see more examples of how to use **AboutAppDevLib**.


Build-in screens
----------------

**AboutAppDevLib** library includes a collection of classes to generate `Fragments` as screens commonly used to display information about your app.

### AboutTabsFragment
```java
AboutTabsFragment.Builder(@NonNull final Context ctx)
    .withTabsMode(int mode);
    .withTabsBackgroundColor(@ColorInt int tabsBackgroundColor);
    .withTabsBackgroundColorRes(@ColorRes int tabsBackgroundColorRes);
    .withTabsNormalColor(@ColorInt int tabsNormalColor);
    .withTabsNormalColorRes(@ColorRes int tabsNormalColorRes);
    .withTabsSelectedColor(@ColorInt int tabsSelectedColor);
    .withTabsSelectedColorRes(@ColorRes int tabsSelectedColorRes);
    .withTabsIndicatorColor(@ColorInt int tabsIndicatorColor);
    .withTabsIndicatorColorRes(@ColorRes int tabsIndicatorColorRes);
    .addPage(@NonNull String tabTitle,@NonNull Fragment fragment);
    .addPage(@StringRes int tabTitleRes,@NonNull Fragment fragment);
    .addPageWithIcon(@DrawableRes int tabIcon,@NonNull Fragment fragment);
```

### AboutAppFragment
```java
AboutAppFragment.Builder(@NonNull final Context ctx)
    .withBackgroundColor(@ColorInt int backgroundColor);
    .withBackgroundColorRes(@ColorRes int backgroundColorRes);
    .withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor);
    .withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor);
    .withIcon(@Nullable Drawable icon);
    .withIcon(@DrawableRes int iconRes);
    .withName(@Nullable String name);
    .withName(@StringRes int nameRes);
    .withVersion(@Nullable String version);
    .withVersion(@StringRes int versionRes);
    .withCopyright(@Nullable String copyright);
    .withCopyright(@StringRes int copyrightRes);
    .withThanks(@Nullable String title,@Nullable String text);
    .withThanks(@StringRes int titleRes,@StringRes int textRes);
    .withChangelog(@Nullable String title,@Nullable String text);
    .withChangelog(@StringRes int titleRes,@StringRes int textRes);
    .withActions(View... actions);
```

### AboutDevFragment
```java
AboutDevFragment.Builder(@NonNull final Context ctx)
    .withBackgroundColor(@ColorInt int backgroundColor);
    .withBackgroundColorRes(@ColorRes int backgroundColorRes);
    .withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor);
    .withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor);
    .withLogo(@Nullable Drawable logo);
    .withLogo(@DrawableRes int logoRes);
    .withAuthor(@Nullable String author);
    .withAuthor(@StringRes int authorRes);
    .withInfo(@Nullable String info);
    .withInfo(@StringRes int infoRes);
    .withActions(View... actions);
    .withDevs(@Nullable String title,Dev... devs);
    .withDevs(@StringRes int titleRes,Dev... devs);
    .withApps(@Nullable String title,App... apps);
    .withApps(@StringRes int titleRes,App... apps);
```

### AboutLibFragment
```java
AboutLibFragment.Builder(@NonNull final Context ctx)
    .withBackgroundColor(@ColorInt int backgroundColor);
    .withBackgroundColorRes(@ColorRes int backgroundColorRes);
    .withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor);
    .withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor);
    .withInfo(@Nullable String info);
    .withInfo(@StringRes int infoRes);
    .withLibs(@Nullable String title,Lib... libs);
    .withLibs(@StringRes int titleRes,Lib... libs);
```

### AboutFaqFragment
```java
AboutLibFragment.Builder(@NonNull final Context ctx)
    .withBackgroundColor(@ColorInt int backgroundColor);
    .withBackgroundColorRes(@ColorRes int backgroundColorRes);
    .withTextColors(@ColorInt int primaryColor,@ColorInt int secondaryColor,@ColorInt int sectionColor);
    .withTextColorsRes(@ColorRes int primaryColor,@ColorRes int secondaryColor,@ColorRes int sectionColor);
    .withInfo(@Nullable String info);
    .withInfo(@StringRes int infoRes);
    .withFaqs(@Nullable String title,Faq... faqs);
    .withFaqs(@StringRes int titleRes,Faq... faqs);
```


Build-in buttons
----------------

[`ButtonFactory`](https://github.com/ej3dev/AboutAppDevLib/blob/master/library/src/main/java/net/ej3/libs/aboutappdevlib/util/ButtonFactory.java) class include methods to build commonly used action buttons:

### Custom buttons
```java
ButtonFactory.customButton(@NonNull final Context ctx,@DrawableRes final int icon,@NonNull final String label,final View.OnClickListener onClick);
ButtonFactory.customButton(@NonNull final Context ctx,@DrawableRes final int iconRes,@StringRes final int labelRes,final View.OnClickListener onClick);
```

### System action buttons
```java
ButtonFactory.openUrl(@NonNull final Context ctx,@NonNull final String label,@NonNull final String url);
ButtonFactory.openUrl(@NonNull final Context ctx,@StringRes final int labelRes,@StringRes final int urlRes);
ButtonFactory.openAddContact(@NonNull final Context ctx,@NonNull final String name,@NonNull final String phone);
ButtonFactory.openAddContact(@NonNull final Context ctx,@StringRes final int nameRes,@StringRes final int phoneRes);
ButtonFactory.sendEmail(@NonNull final Context ctx,@NonNull final String email,@NonNull final String subject,@NonNull final String message);
ButtonFactory.sendEmail(@NonNull final Context ctx,@StringRes final int emailRes,@StringRes final int subjectRes,@StringRes final int messageRes);
ButtonFactory.shareThisApp(@NonNull final Context ctx,@NonNull final String subject,@NonNull final String message);
ButtonFactory.shareThisApp(@NonNull final Context ctx,@StringRes final int subjectRes,@StringRes final int messageRes);
ButtonFactory.callPhone(@NonNull final Context ctx,@NonNull final String phone);
ButtonFactory.callPhone(@NonNull final Context ctx,@StringRes final int phoneRes);
ButtonFactory.openMap(@NonNull final Context ctx,@Nullable final String place,final double latitude,final double longitude,final int zoom);
ButtonFactory.openMap(@NonNull final Context ctx,@StringRes final int placeRes,final double latitude,final double longitude,final int zoom);
```

### Repositories
```java
ButtonFactory.openGithub(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openGithub(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openBitbucket(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openBitbucket(@NonNull final Context ctx,@StringRes final int userRes);
```

### Apps
```java
ButtonFactory.openInstagram(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openInstagram(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openFacebook(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openFacebook(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openTwitter(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openTwitter(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openGooglePlus(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openGooglePlus(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openYoutubeChannel(@NonNull final Context ctx,@NonNull final String channel);
ButtonFactory.openYoutubeChannel(@NonNull final Context ctx,@StringRes final int channelRes);
ButtonFactory.openYoutubeUser(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openYoutubeUser(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openLinkedIn(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openLinkedIn(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openSkype(@NonNull final Context ctx,@NonNull final String phone);
ButtonFactory.openSkype(@NonNull final Context ctx,@StringRes final int phoneRes);
```

### Play Store
```java
ButtonFactory.openPlayStoreDev(@NonNull final Context ctx,@NonNull final String user);
ButtonFactory.openPlayStoreDev(@NonNull final Context ctx,@StringRes final int userRes);
ButtonFactory.openPlayStoreAppPage(@NonNull final Context ctx,@NonNull final String app);
ButtonFactory.openPlayStoreAppPage(@NonNull final Context ctx,@StringRes final int appRes);
ButtonFactory.openPlayStoreAppsList(@NonNull final Context ctx,@NonNull final String app);
ButtonFactory.openPlayStoreAppsList(@NonNull final Context ctx,@StringRes final int appRes);
```


Build-in cards
--------------

**AboutAppDevLib** library includes a collection of [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object) classes used as data store for specialized [CardView](https://developer.android.com/reference/android/support/v7/widget/CardView.html) widgets.

### App
```java
AppBuilder(@NonNull final Context ctx,@NonNull final String name)
AppBuilder(@NonNull final Context ctx,@StringRes final int nameRes)
    .withUrlOrPackageName(@Nullable final String urlOrPackageName);
    .withUrlOrPackageName(@StringRes final int urlOrPackageNameRes);
    .withDescription(@Nullable final String description);
    .withDescription(@StringRes final int descriptionRes);
    .withIcon(@Nullable final Drawable icon);
    .withIcon(@DrawableRes final int iconRes);
```

### Dev
```java
DevBuilder(@NonNull final Context ctx,@NonNull final String name)
DevBuilder(@NonNull final Context ctx,@StringRes final int nameRes)
    .withJob(@Nullable final String job);
    .withJob(@StringRes final int jobRes);
    .withBio(@Nullable final String bio);
    .withBio(@StringRes final int bioRes);
    .withPhoto(@Nullable final Drawable photo);
    .withPhoto(@DrawableRes final int photoRes);
    .withUrl(@Nullable final String url);
    .withUrl(@StringRes final int urlRes);
```

### Lib
```java
LibBuilder(@NonNull final Context ctx,@NonNull final String name)
LibBuilder(@NonNull final Context ctx,@StringRes final int nameRes)
    .withAuthor(@Nullable final String author);
    .withAuthor(@StringRes final int authorRes);
    .withDescription(@Nullable final String description);
    .withDescription(@StringRes final int descriptionRes);
    .withIcon(@Nullable final Drawable icon);
    .withIcon(@DrawableRes final int iconRes);
    .withUrl(@Nullable final String url);
    .withUrl(@StringRes final int urlRes);
```

### Faq
```java
FaqBuilder(@NonNull final Context ctx,@NonNull final String question)
FaqBuilder(@NonNull final Context ctx,@StringRes final int questionRes)
    .withAnswer(@Nullable final String answer);
    .withAnswer(@StringRes final int answerRes);
    .isOpen(final boolean open);
```


Apps using the AboutAppDevLib
-----------------------------

Send me an [email](emailto:ej3dev@gmail.com) to add new apps to this list:

* [Reminder Alarm](https://play.google.com/store/apps/details?id=net.ej3.apps.reminderalarm)


License
-------

    Copyright (c) 2018 Emilio José Jiménez <ej3dev@gmail.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.