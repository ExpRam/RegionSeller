package ru.expram;

public class Region {

    private String rg_name;
    private String seller;
    private int price;

    public Region(String rg_name, String seller, int price) {
        this.rg_name = rg_name;
        this.seller = seller;
        this.price = price;
    }

    public String getRg_name() {
        return rg_name;
    }

    public String getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }
}
