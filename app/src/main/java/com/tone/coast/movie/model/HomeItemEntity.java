package com.tone.coast.movie.model;

import java.io.Serializable;
import java.util.List;

public class HomeItemEntity implements Serializable {
    public String name;
    public List<MovieEntity> movies;
}
