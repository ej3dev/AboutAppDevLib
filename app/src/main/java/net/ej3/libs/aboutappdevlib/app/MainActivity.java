package net.ej3.libs.aboutappdevlib.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import net.ej3.libs.aboutappdevlib.AboutFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_NONE)
            .replace(R.id.content,buildFragment())
            .commitAllowingStateLoss();
    }

    private Fragment buildFragment() {
        return new AboutFragment.Builder()
            .withTabsMode(TabLayout.MODE_SCROLLABLE)
            .withTabsIndicatorColor(ContextCompat.getColor(this,R.color.md_red_800))
            .addPage("ONE",new FooFragment())
            .addPage("TWO",new FooFragment())
            .addPage("THREE",new FooFragment())
            .addPage(R.mipmap.ic_launcher,new FooFragment())
            .addPage(R.mipmap.ic_launcher_round,new FooFragment())
            .addPage("FOUR",new FooFragment())
            .addPage("FIVE",new FooFragment())
            .build();
    }
}
