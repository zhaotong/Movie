package com.tone.coast.movie.model.entity;

import java.io.Serializable;

public class DataResult<T> implements Serializable {
    public String msg;
    public T data;
    public int code;
}
