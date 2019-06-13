package com.example.demo.Helper;




public class OrderWrapper {
    private Double moneyAmount;
    private String market;

    public OrderWrapper(Double moneyAmount, String market) {
        this.moneyAmount = moneyAmount;
        this.market = market;
    }

    public OrderWrapper() {
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
