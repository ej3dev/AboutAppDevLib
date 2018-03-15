package net.ej3.libs.aboutappdevlib.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author E.J. Jiménez
 * @version 20180315
 */
public class LicenseFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.license_fragment,container,false);
    }
    //endregion

}
