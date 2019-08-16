package com.tone.coast.movie.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.tone.coast.movie.ui.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: tone
 * Date: 2019-05-20 17:29
 * Description:
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected LayoutInflater mInflater;
    private Context mContext;
    protected List<T> mListItems = new ArrayList<>();
    protected final int mItemLayoutId;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        <T> void onItemClick(View view, int position, T item);
    }

    public interface OnItemLongClickListener {
        <T> void onItemLongClick(View view, int position, T item);
    }

    public BaseRecyclerViewAdapter(Context context, int itemLayoutId) {
        super();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemLayoutId = itemLayoutId;
    }

    public void setData(List<T> mDatas) {
        this.mListItems = mDatas;
        notifyDataSetChanged();
    }

    public void setDiffData(List<T> mDatas) {
        this.mListItems = mDatas;
    }

    public List<T> getData() {
        return mListItems;
    }

    public Context getContext() {
        return mContext;
    }

    public void add(T item) {
        mListItems.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> dataList) {
        mListItems.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear() {
        mListItems.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        notifyItemRemoved(position);
        mListItems.remove(position);
        notifyItemRangeChanged(position, mListItems.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    @Override
    public void onBindViewHolder(final BaseHolder myViewHolder, final int position) {
        setItemData(myViewHolder, mListItems.get(position), position);
        if (mOnItemClickListener != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(myViewHolder.itemView, position, mListItems.get(position));
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(myViewHolder.itemView, position, mListItems.get(position));
                    return true;
                }
            });
        }
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mItemLayoutId, viewGroup, false);
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }


    public abstract void setItemData(BaseHolder holder, T item, int position);

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
