package com.boutiqueseeds.boutiqueSeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity(name="Purchases")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDateTime purchaseDate;

    private LineItem[] lineItems;

    private Extra[] extras;

    private Boolean orderShipped;

    private LocalDate shippedDate;

    private String shippedVia;

    private String trackingNumber;

    private OrderNote[] orderNotes;

    private BigDecimal shippingFee;

    private BigDecimal preTax;

    private BigDecimal tax;

    private Boolean discountApplied;

    private BigDecimal discountAmount;

    private String discountCode;

    private BigDecimal total;

    private String purchaserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LineItem[] getLineItems() {
        return lineItems;
    }

    public void setLineItems(LineItem[] lineItems) {
        this.lineItems = lineItems;
    }

    public Extra[] getExtras() {
        return extras;
    }

    public void setExtras(Extra[] extras) {
        this.extras = extras;
    }

    public Boolean getOrderShipped() {
        return orderShipped;
    }

    public void setOrderShipped(Boolean orderShipped) {
        this.orderShipped = orderShipped;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getShippedVia() {
        return shippedVia;
    }

    public void setShippedVia(String shippedVia) {
        this.shippedVia = shippedVia;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public OrderNote[] getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(OrderNote[] orderNotes) {
        this.orderNotes = orderNotes;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getPreTax() {
        return preTax;
    }

    public void setPreTax(BigDecimal preTax) {
        this.preTax = preTax;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Boolean getDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(Boolean discountApplied) {
        this.discountApplied = discountApplied;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }
}
