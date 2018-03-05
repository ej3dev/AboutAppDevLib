package net.ej3.libs.aboutappdevlib.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author E.J. Jiménez
 * @version 20180305
 */
public class FooFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.foo_fragment,container,false);
    }
}
