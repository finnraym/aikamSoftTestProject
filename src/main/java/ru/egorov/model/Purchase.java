package ru.egorov.model;

import java.time.LocalDate;

public class Purchase {
    private long id;
    private Buyer buyer;
    private Product product;
    private LocalDate purchaseDate;

    public Purchase() {
    }

    public Purchase(long id, Buyer buyer, Product product, LocalDate purchaseDate) {
        this.id = id;
        this.buyer = buyer;
        this.product = product;
        this.purchaseDate = purchaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
