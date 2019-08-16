package com.tone.coast.movie.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.tone.coast.movie.model.event.EventEmpty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Author: tone
 * Date: 2019-05-16 13:44
 * Description:
 */
public class BaseFragment extends Fragment {

    public static final String INTENT_DATA = "data";
    public boolean isInit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        initView(savedInstanceState);
        isInit = true;
    }


    public <T extends View> T findView(@IdRes int id) {
        return getView().findViewById(id);
    }

    public void startActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void startActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtra(INTENT_DATA, bundle);
        startActivity(intent);
    }



    public void initView(Bundle bundle) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventEmpty event) {

    }

    public int getLayout() {
        return 0;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isInit) {
            visibleToUser(isVisibleToUser);
        }
    }

    public void visibleToUser(boolean isVisibleToUser) {


    }


}
