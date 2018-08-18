package com.wechat.mainwindow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jingluyuan on 7/27/18.
 */

public class TabFragment extends Fragment {

    private String mTitle = "Default";
    public static String TITLE = "title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments()!= null)
        {
            mTitle = getArguments().getString(TITLE);

        }

        TextView tv = new TextView(getActivity());
        tv.setTextSize(20);
        tv.setBackgroundColor(Color.parseColor("#ffffffff"));
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
