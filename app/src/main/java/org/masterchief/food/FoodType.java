package org.masterchief.food;

public class FoodType {

    private String name;
    private Integer imageRef;

    public FoodType(String name, Integer imageRef) {
        this.name = name;
        this.imageRef = imageRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageRef() {
        return imageRef;
    }

    public void setImageRef(Integer imageRef) {
        this.imageRef = imageRef;
    }
}
