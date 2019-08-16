package com.tone.coast.movie.ui.adapter;

import androidx.annotation.NonNull;

import com.tone.coast.movie.model.entity.ListItemEntity;
import com.tone.coast.movie.ui.holder.ViewBinderBanner;
import com.tone.coast.movie.ui.holder.ViewBinderSelection;

import java.util.List;

import me.drakeet.multitype.ClassLinker;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

public class ListAdapter extends MultiTypeAdapter {


    public void setData(List items) {
        setItems(items);
        notifyDataSetChanged();
    }

    public ListAdapter() {

        register(ListItemEntity.class)
                .to(new ViewBinderBanner()
                        , new ViewBinderSelection())
                .withClassLinker(new ClassLinker<ListItemEntity>() {
                    @NonNull
                    @Override
                    public Class<? extends ItemViewBinder<ListItemEntity, ?>> index(int position, @NonNull ListItemEntity item) {
                        if (item.itemType == 0) {
                            return ViewBinderBanner.class;
                        }
                        if (item.itemType == 1) {
                            return ViewBinderSelection.class;
                        }
                        return null;
                    }
                });
    }

}
