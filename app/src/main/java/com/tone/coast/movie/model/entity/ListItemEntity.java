package com.tone.coast.movie.model.entity;

import androidx.annotation.IntDef;

public class ListItemEntity<T> {


    @IntDef(
            value = {
                    0,//banner
                    1,//secetion
                    2,//item
            }
    )
    public @interface ItemType {
    }

    public ListItemEntity(@ItemType int itemType, T data) {
        this.itemType = itemType;
        this.data = data;
    }


    @ItemType
    public int itemType;

    public T data;

}
