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
public class FollowSuperPatientFragment extends Fragment {

    private TextView followSuperPatientText;

    public FollowSuperPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow_super_patient, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        followSuperPatientText = (TextView) view.findViewById(R.id.games_intro_follow_super_patient_text);

        if(GlobalData.getInstance().isHdpi(getActivity())){
            followSuperPatientText.setTextSize(15);
        }
    }


}
