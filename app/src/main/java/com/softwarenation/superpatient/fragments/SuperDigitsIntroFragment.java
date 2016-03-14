package com.softwarenation.superpatient.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.softwarenation.superpatient.utility.GlobalData;

import com.softwarenation.superpatient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperDigitsIntroFragment extends Fragment {

private TextView superDigitsText;

    public SuperDigitsIntroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_super_digits_intro, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        superDigitsText = (TextView) view.findViewById(R.id.games_intro_super_digits_text);

        if(GlobalData.getInstance().isHdpi(getActivity())){
            superDigitsText.setTextSize(15);
        }
    }



}
