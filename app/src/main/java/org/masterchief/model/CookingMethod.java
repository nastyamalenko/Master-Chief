package org.masterchief.model;



public class CookingMethod {

    private Long id;
    private byte[] name;

    public CookingMethod() {
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
