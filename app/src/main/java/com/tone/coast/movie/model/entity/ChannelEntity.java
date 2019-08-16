package com.tone.coast.movie.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChannelEntity implements Serializable {
    public String name;
    public String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelEntity that = (ChannelEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
