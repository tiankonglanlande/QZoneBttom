package com.lqm.qzonebttom;

import com.example.qzonebttom.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Fragment3 extends Fragment {

	private View rootView;
	private MainActivity ac;
	private LinearLayout layoutHide;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView=inflater.inflate(R.layout.fragment3, container, false);
		return rootView;
	}
}
