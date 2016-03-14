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
public class SuperMashUpIntroFragment extends Fragment {

    private TextView superMashUpText;

    public SuperMashUpIntroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_super_mash_up_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        superMashUpText = (TextView) view.findViewById(R.id.games_intro_super_mash_up_text);

        if(GlobalData.getInstance().isHdpi(getActivity())){
            superMashUpText.setTextSize(15);
        }
    }
}
