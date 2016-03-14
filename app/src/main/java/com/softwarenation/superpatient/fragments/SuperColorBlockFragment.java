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
public class SuperColorBlockFragment extends Fragment {


    private TextView superColorBlockText;
    private TextView superColorBlockYellow;

    public SuperColorBlockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_super_color_block, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        superColorBlockText = (TextView) view.findViewById(R.id.games_intro_super_color_block_text);
        superColorBlockYellow = (TextView) view.findViewById(R.id.games_intro_super_color_block_yellow);

        if(GlobalData.getInstance().isHdpi(getActivity())){
            superColorBlockText.setTextSize(15);
            superColorBlockYellow.setTextSize(22);
        }
    }

}
