package com.nisoft.inspectortools.ui.typeproblem;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nisoft.inspectortools.bean.org.UserLab;
import com.nisoft.inspectortools.bean.problem.Recode;
import com.nisoft.inspectortools.ui.base.ChooseMemberDialog;
import com.nisoft.inspectortools.ui.base.DatePickerDialog;
import com.nisoft.inspectortools.ui.base.EditTextActivity;

import java.util.Date;

/**
 * Created by NewIdeaSoft on 2017/7/2.
 */

public abstract class RecodeFragment extends Fragment {

    private Recode mRecode;
//    private int layoutResId;


    protected abstract void init();

    public abstract void updateData();

    //    public abstract void updateView();
    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

    //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = initView(inflater, container);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void startEditTextActivity(int requestCode, String initText) {
        Intent intent = new Intent(getActivity(), EditTextActivity.class);
        intent.putExtra("initText", initText);
        startActivityForResult(intent, requestCode);
    }

    protected void showDatePickerDialog(Fragment targetFragment, int requestCode, Date date) {
        FragmentManager fm = getFragmentManager();
        DatePickerDialog dialog = DatePickerDialog.newInstance(-1, date);
        dialog.setTargetFragment(targetFragment, requestCode);
        dialog.show(fm, "date");
    }

    protected void showContactsDialog(int requestCode) {
        String parentId = UserLab.getUserLab(getActivity()).getEmployee().getOrgId();
        FragmentManager fm = getFragmentManager();
        ChooseMemberDialog dialog = ChooseMemberDialog.newInstance(parentId);
        dialog.setTargetFragment(this, requestCode);
        dialog.show(fm, "date");
    }
    protected abstract void onDataChanged();
}
