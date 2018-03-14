package net.ej3.libs.aboutappdevlib.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ej3.libs.aboutappdevlib.app.databinding.HomeFragmentBinding;

/**
 * @author E.J. Jiménez
 * @version 20180313
 */
public class HomeFragment extends Fragment {

    //--------------------------------------------------------------------------
    //region Components
    //
    private HomeFragmentBinding binding;
    //endregion


    //--------------------------------------------------------------------------
    //region Fragment lifecycle
    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false);
        setEvents();
        return binding.getRoot();
    }
    //endregion


    //--------------------------------------------------------------------------
    //region Events
    //
    private void setEvents() {
        binding.layHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity da = (DemoActivity)getActivity();
                if( da == null ) return;
                da.show(da.buildFixedTabsFragment());
            }
        });
        binding.layHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity da = (DemoActivity)getActivity();
                if( da == null ) return;
                da.show(da.buildScrollableTabsFragment());
            }
        });
        binding.layHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity da = (DemoActivity)getActivity();
                if( da == null ) return;
                da.show(da.buildWithoutTabsFragment());
            }
        });
        binding.layHome4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoActivity da = (DemoActivity)getActivity();
                if( da == null ) return;
                da.show(da.buildCustomColorsFragment());
            }
        });
    }
    //endregion

}
