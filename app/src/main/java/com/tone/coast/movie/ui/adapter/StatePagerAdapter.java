package com.tone.coast.movie.ui.adapter;

import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class StatePagerAdapter<T> extends PagerAdapter {

    private static final String TAG = "PagerAdapter";
    private static final boolean DEBUG = false;

    protected final FragmentManager mFragmentManager;
    protected FragmentTransaction mCurTransaction = null;

    protected ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();
    protected ArrayList<ItemInfo<T>> mItems = new ArrayList<>();
    protected Fragment mCurrentPrimaryItem = null;

    public StatePagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }


    public abstract Fragment getItem(int position);


    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mItems.size() > position) {
            ItemInfo ii = mItems.get(position);
            if (ii != null) {
                //判断位置是否相等，如果不相等说明新数据有增加或删除(导致了ViewPager那边有空位)，
                if (ii.position == position) {
                    return ii;
                }
            }
        }

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        Fragment fragment = getItem(position);

        if (DEBUG)
            Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
        if (mSavedState.size() > position) {
            Fragment.SavedState fss = mSavedState.get(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }
        while (mItems.size() <= position) {
            mItems.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        ItemInfo<T> iiNew = new ItemInfo<>(fragment, getItemData(position), position);
        mItems.set(position, iiNew);
        mCurTransaction.add(container.getId(), fragment);

        return iiNew;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ItemInfo ii = (ItemInfo) object;

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        if (DEBUG)
            Log.v(TAG, "Removing item #" + position + ": f=" + object + " v=" + ((Fragment) object).getView());
        while (mSavedState.size() <= position) {
            mSavedState.add(null);
        }
        mSavedState.set(position, ii.fragment.isAdded() ? mFragmentManager.saveFragmentInstanceState(ii.fragment) : null);
        if (position < mItems.size()) {
            for (int i = 0; i < mItems.size(); i++) {
                if (mItems.get(i) == null) {
                    continue;
                }
                if (mItems.get(i).position == position) {
                    mItems.set(i, null);
                }
            }
            mItems.set(position, null);
        }

        mCurTransaction.remove(ii.fragment);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        ItemInfo ii = (ItemInfo) object;
        Fragment fragment = ii.fragment;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Fragment fragment = ((ItemInfo) object).fragment;
        return fragment.getView() == view;
    }

    @Override
    public int getItemPosition(Object object) {
        ItemInfo<T> itemInfo = (ItemInfo) object;
        int oldPosition = mItems.indexOf(itemInfo);
        if (oldPosition >= 0) {
            T oldData = itemInfo.data;
            T newData = getItemData(oldPosition);
            if (dataEquals(oldData, newData)) {
                return POSITION_UNCHANGED;
            } else {
                ItemInfo<T> oldItemInfo = mItems.get(oldPosition);
                int oldDataNewPosition = getDataPosition(oldData);
                if (oldDataNewPosition < 0) {
                    oldDataNewPosition = POSITION_NONE;
                }
                //把新的位置赋值到缓存的itemInfo中，以便调整时使用
                if (oldItemInfo != null) {
                    oldItemInfo.position = oldDataNewPosition;
                }
                return oldDataNewPosition;
            }

        }

        return POSITION_UNCHANGED;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //通知ViewPager更新完成后对缓存的ItemInfo List进行调整
        initItems();
    }


    protected void initItems() {
        //只有调用过getItemPosition(也就是有notifyDataSetChanged)才进行缓存的调整
        ArrayList<ItemInfo<T>> newItems = new ArrayList<>();
        //先存入空数据
        for (int i = 0; i < mItems.size(); i++) {
            newItems.add(null);
        }
        //根据缓存的itemInfo中的新position把itemInfo入正确的位置
        for (ItemInfo<T> itemInfo : mItems) {
            if (itemInfo != null) {
                if (itemInfo.position >= 0) {
                    while (newItems.size() <= itemInfo.position) {
                        newItems.add(null);
                    }
                    newItems.set(itemInfo.position, itemInfo);
                }
            }
        }
        mItems = newItems;
    }

    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (mSavedState.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[mSavedState.size()];
            mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i) != null) {
                Fragment f = mItems.get(i).fragment;
                if (f != null && f.isAdded()) {
                    if (state == null) {
                        state = new Bundle();
                    }
                    String key = "f" + i;
                    mFragmentManager.putFragment(state, key, f);
                }
            }
        }

        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            mSavedState.clear();
            mItems.clear();
            if (fss != null) {
                for (int i = 0; i < fss.length; i++) {
                    mSavedState.add((Fragment.SavedState) fss[i]);
                }
            }
            Iterable<String> keys = bundle.keySet();
            for (String key : keys) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (mItems.size() <= index) {
                            mItems.add(null);
                        }
                        f.setMenuVisibility(false);
                        ItemInfo<T> iiNew = new ItemInfo<>(f, getItemData(index), index);
                        mItems.set(index, iiNew);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }

    public Fragment getCurrentPrimaryItem() {
        return mCurrentPrimaryItem;
    }

    public Fragment getFragmentByPosition(int position) {
//        initItems();
        if (position < 0 || position >= mItems.size()) return null;
        if (mItems.get(position) == null) {
            return null;
        }
        return mItems.get(position).fragment;
    }

    public List<Fragment> getCacheFragments() {
        List<Fragment> fragments = new ArrayList<>();
//        initItems();
        for (ItemInfo<T> itemInfo : mItems) {
            if (itemInfo != null && itemInfo.fragment != null) {
                fragments.add(itemInfo.fragment);
            }
        }
        return fragments;
    }

    public abstract T getItemData(int position);

    public abstract boolean dataEquals(T oldData, T newData);

    public abstract int getDataPosition(T data);

    protected static class ItemInfo<D> {
        Fragment fragment;
        D data;
        int position;

        public ItemInfo(Fragment fragment, D data, int position) {
            this.fragment = fragment;
            this.data = data;
            this.position = position;
        }

        public D getData() {
            return data;
        }

        public int getPosition() {
            return position;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }
}
