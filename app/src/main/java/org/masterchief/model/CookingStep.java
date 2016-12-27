package org.masterchief.model;


public class CookingStep {
    private Long id;
    private byte[] name;

    public CookingStep() {
    }

    public CookingStep(Long id, byte[] name) {
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
