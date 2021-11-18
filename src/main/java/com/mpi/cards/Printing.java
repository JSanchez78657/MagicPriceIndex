package com.mpi.cards;

public class Printing {
    private String setName;
    private String setSymbol;
    private String finish;
    private double price;
    private double priceFoil;

    @Override
    public String toString() {
        return "Printing{" +
                "setName='" + setName + '\'' +
                ", setSymbol='" + setSymbol + '\'' +
                ", finish='" + finish + '\'' +
                ", price=" + price +
                ", priceFoil=" + priceFoil +
                '}';
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetSymbol() {
        return setSymbol;
    }

    public void setSetSymbol(String setSymbol) {
        this.setSymbol = setSymbol;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceFoil() {
        return priceFoil;
    }

    public void setPriceFoil(double priceFoil) {
        this.priceFoil = priceFoil;
    }
}
