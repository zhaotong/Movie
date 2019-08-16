package com.tone.coast.movie.ui.adapter;

import androidx.fragment.app.Fragment;

import com.tone.coast.movie.model.entity.ChannelEntity;
import com.tone.coast.movie.ui.fragment.ListFragment;

public class FragmentTypeHelper {

    public static Fragment getFragment(ChannelEntity entity) {
        return ListFragment.newInstance(entity);
    }
}
