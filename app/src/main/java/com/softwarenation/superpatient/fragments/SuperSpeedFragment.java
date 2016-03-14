package com.softwarenation.superpatient.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwarenation.superpatient.R;
import com.softwarenation.superpatient.utility.GlobalData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperSpeedFragment extends Fragment {

    private TextView superSpeedText;



    public SuperSpeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_super_speed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        superSpeedText = (TextView) view.findViewById(R.id.games_intro_super_speed_text);

        if(GlobalData.getInstance().isHdpi(getActivity())){
            superSpeedText.setTextSize(15);
        }
    }


}
