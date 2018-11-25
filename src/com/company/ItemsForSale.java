package com.company;

public class ItemsForSale {
    private String name;
    private Integer price;

    public ItemsForSale(String name, int price) {
        this.setName(name);
        this.setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


}
