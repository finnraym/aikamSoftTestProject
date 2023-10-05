package ru.egorov.dto;

public class ProductNameAndCountPurchasesCriteria implements Criteria {

    private String productName;
    private int minTimes;

    public ProductNameAndCountPurchasesCriteria(String productName, int minTimes) {
        this.productName = productName;
        this.minTimes = minTimes;
    }

    public ProductNameAndCountPurchasesCriteria() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMinTimes() {
        return minTimes;
    }

    public void setMinTimes(int minTimes) {
        this.minTimes = minTimes;
    }
}
