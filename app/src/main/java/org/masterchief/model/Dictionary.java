package org.masterchief.model;


import java.util.Arrays;
import java.util.Objects;

public class Dictionary {

    public static final Long FOOD_CATEGORY = 1L;
    public static final Long COOKING_METHOD = 2L;
    public static final Long CUISINE = 3L;
//    public static final Long COUSINE = 4L;

    private Long id;
    private byte[] name;


    public Dictionary() {
    }

    public Dictionary(Long id, byte[] name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", name=" + new String(name) +
                '}';
    }
}
