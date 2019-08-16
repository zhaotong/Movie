package com.tone.coast.movie.ui.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: tone
 * Date: 2019-05-20 17:30
 * Description:
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    private Context context;

    public Context getContext() {
        return context;
    }

    public BaseHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
        this.mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public RecyclerView.ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public RecyclerView.ViewHolder setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    public RecyclerView.ViewHolder setBackgroundResource(int viewId, int id) {
        View view = getView(viewId);
        view.setBackgroundResource(id);
        return this;
    }


    public RecyclerView.ViewHolder setText(int viewId, int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    public RecyclerView.ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }



    public RecyclerView.ViewHolder setImageVisibility(int viewId, int visibility) {
        ImageView view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }


    public RecyclerView.ViewHolder setViewVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public RecyclerView.ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


}
