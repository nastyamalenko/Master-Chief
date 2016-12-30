package org.masterchief.model;


public class Dictionary {

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
}
