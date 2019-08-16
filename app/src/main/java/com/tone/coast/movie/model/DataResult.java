package com.tone.coast.movie.model;

import java.io.Serializable;

public class DataResult<T> implements Serializable {
    public String msg;
    public T data;
    public int code;
}
