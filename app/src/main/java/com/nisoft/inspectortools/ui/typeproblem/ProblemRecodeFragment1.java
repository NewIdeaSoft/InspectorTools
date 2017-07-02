package com.nisoft.inspectortools.ui.typeproblem;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.nisoft.inspectortools.R;
import com.nisoft.inspectortools.bean.problem.ProblemDataLab;
import com.nisoft.inspectortools.bean.problem.ProblemDataPackage;
import com.nisoft.inspectortools.db.problem.RecodeDbSchema.RecodeTable;

import java.util.ArrayList;

/**
 * Created by NewIdeaSoft on 2017/4/25.
 */

public class ProblemRecodeFragment1 extends Fragment {
    private static ProblemDataPackage mProblem;
    private ViewPager problemViewPager;
    private ArrayList<Fragment> problemFragmentList;
    private LinearLayout tab_problem_simple_info;
    private LinearLayout tab_problem_detailed_info;
    private LinearLayout tab_problem_reason_info;
    private LinearLayout tab_problem_solved_info;
    public static ProblemRecodeFragment1 newInstance(String problemId){
        Bundle args = new Bundle();
        args.putString(RecodeTable.Cols.PROBLEM_ID,problemId);
        ProblemRecodeFragment1 fragment = new ProblemRecodeFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater,container,savedInstanceState);
    }
    private View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_problem_recode,container,false);
        ActionBar actionBar = getActivity().getActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(mProblem.getProblem().getTitle()!=null) {

        }else {

        }

        problemViewPager = (ViewPager) view.findViewById(R.id.problem_info_viewpager);
        FragmentManager fm = getFragmentManager();
        problemViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return problemFragmentList.get(position);
            }
            @Override
            public int getCount() {
                return problemFragmentList.size();
            }
        });
        problemViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        resTabBackground();
                        tab_problem_simple_info.setBackgroundResource(R.color.colorTabSelect);
                        break;
                    case 1:
                        resTabBackground();
                        tab_problem_detailed_info.setBackgroundResource(R.color.colorTabSelect);
                        break;
                    case 2:
                        resTabBackground();
                        tab_problem_reason_info.setBackgroundResource(R.color.colorTabSelect);
                        break;
                    case 3:
                        resTabBackground();
                        tab_problem_solved_info.setBackgroundResource(R.color.colorTabSelect);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tab_problem_simple_info = (LinearLayout) view.findViewById(R.id.tab_problem_simple_info);
        tab_problem_detailed_info = (LinearLayout) view.findViewById(R.id.tab_problem_detailed_info);
        tab_problem_reason_info = (LinearLayout) view.findViewById(R.id.tab_problem_reason_info);
        tab_problem_solved_info = (LinearLayout) view.findViewById(R.id.tab_problem_sovled_info);

        tab_problem_simple_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resTabBackground();
                tab_problem_simple_info.setBackgroundResource(R.color.colorTabSelect);
                problemViewPager.setCurrentItem(0);
            }
        });
        tab_problem_detailed_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resTabBackground();
                tab_problem_detailed_info.setBackgroundResource(R.color.colorTabSelect);
                problemViewPager.setCurrentItem(1);
            }
        });
        tab_problem_reason_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resTabBackground();
                tab_problem_reason_info.setBackgroundResource(R.color.colorTabSelect);
                problemViewPager.setCurrentItem(2);
            }
        });
        tab_problem_solved_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resTabBackground();
                tab_problem_solved_info.setBackgroundResource(R.color.colorTabSelect);
                problemViewPager.setCurrentItem(3);
            }
        });
        return view;
    }

    private void initVariables(){
        problemFragmentList = new ArrayList<>();
        String problemId = getArguments().getString(RecodeTable.Cols.PROBLEM_ID);
        mProblem = ProblemDataLab.getProblemDataLab(getActivity()).getProblemById(problemId);
        problemFragmentList.add(new ProblemSimpleInfoFragment());
        problemFragmentList.add(new ProblemDetailedInfoFragment());
        problemFragmentList.add(new ProblemReasonInfoFragment());
        problemFragmentList.add(new ProblemSolvedInfoFragment());
    }

    private void resTabBackground(){
        tab_problem_simple_info.setBackgroundResource(R.color.colorTabBackgroung);
        tab_problem_detailed_info.setBackgroundResource(R.color.colorTabBackgroung);
        tab_problem_reason_info.setBackgroundResource(R.color.colorTabBackgroung);
        tab_problem_solved_info.setBackgroundResource(R.color.colorTabBackgroung);
    }

    @Override
    public void onPause() {
        super.onPause();
        ProblemDataLab.getProblemDataLab(getActivity()).updateProblem(getProblem());
    }

    public static ProblemDataPackage getProblem(){
        if(mProblem==null) {
            mProblem =new ProblemDataPackage();
        }
        return mProblem;
    }
}