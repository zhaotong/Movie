package com.tone.coast.movie.model;

import java.io.Serializable;
import java.util.List;

public class ListWrapper<T> implements Serializable {
    public List<T> list;
}
