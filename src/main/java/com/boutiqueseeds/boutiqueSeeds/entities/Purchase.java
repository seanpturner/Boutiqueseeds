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

    private Object[] lineItems;

    private Object[] extras;

    private LocalDate shippedDate;

    private String shippedVia;

    private String trackingNumber;

    private Object[] orderNotes;

    private BigDecimal shippingFee;

    private BigDecimal preTax;

    private BigDecimal tax;

    private Boolean discountApplied;

    private BigDecimal discountAmount;

    private String discountCode;

    private BigDecimal total;

    private String purchaserName;

    private Long orderStatus;

    private LocalDate paymentDate;

    private LocalDate orderPickedDate;

    private LocalDate orderCancelledDate;

    private String deliveryAddress1;

    private String deliveryAddress2;

    private String deliveryNotes;

    private String city;

    private String state;

    private String zip;

    private Long pricingStructure;

    private String orderUser;

    private String email;

    private Long paymentType;

    private String shippingMethod;

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

    public Object[] getLineItems() {
        return lineItems;
    }

    public void setLineItems(Object[] lineItems) {
        this.lineItems = lineItems;
    }

    public Object[] getExtras() {
        return extras;
    }

    public void setExtras(Object[] extras) {
        this.extras = extras;
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

    public Object[] getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(Object[] orderNotes) {
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

    public Long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getOrderPickedDate() {
        return orderPickedDate;
    }

    public void setOrderPickedDate(LocalDate orderPickedDate) {
        this.orderPickedDate = orderPickedDate;
    }

    public LocalDate getOrderCancelledDate() {
        return orderCancelledDate;
    }

    public void setOrderCancelledDate(LocalDate orderCancelledDate) {
        this.orderCancelledDate = orderCancelledDate;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Long getPricingStructure() {
        return pricingStructure;
    }

    public void setPricingStructure(Long pricingStructure) {
        this.pricingStructure = pricingStructure;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Long paymentType) {
        this.paymentType = paymentType;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
