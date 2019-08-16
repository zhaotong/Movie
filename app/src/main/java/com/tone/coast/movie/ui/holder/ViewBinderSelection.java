package com.tone.coast.movie.ui.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tone.coast.movie.R;
import com.tone.coast.movie.model.entity.HomeItemEntity;
import com.tone.coast.movie.model.entity.ListItemEntity;
import com.tone.coast.movie.model.entity.MovieEntity;
import com.tone.coast.movie.ui.adapter.BaseRecyclerViewAdapter;
import com.tone.coast.movie.util.imageloader.ImageLoader;

import me.drakeet.multitype.ItemViewBinder;

public class ViewBinderSelection extends ItemViewBinder<ListItemEntity, BaseHolder> {

    @NonNull
    @Override
    protected BaseHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_selection_item, parent, false);
        return new BaseHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull BaseHolder holder, @NonNull ListItemEntity item) {

        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(holder.getContext(), 3);

        recyclerView.setLayoutManager(layoutManager);
        BaseRecyclerViewAdapter<MovieEntity> adapter = new BaseRecyclerViewAdapter<MovieEntity>(holder.getContext(), R.layout.layout_meida_item) {
            @Override
            public void setItemData(BaseHolder holder, MovieEntity item, int position) {

                ImageLoader.getInstance().loadImage(getContext(), item.image, holder.getView(R.id.item_cover));
                holder.setText(R.id.item_name, item.name);
                holder.setText(R.id.item_text_hd, item.label);


            }
        };

        HomeItemEntity entity = (HomeItemEntity) item.data;
        holder.setText(R.id.text_name, entity.name);
        recyclerView.setAdapter(adapter);
        adapter.setData(entity.movies);

    }
}
