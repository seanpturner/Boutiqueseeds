package com.boutiqueseeds.boutiqueSeeds.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "pricingStructures")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PricingStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    private String description;

    private Integer discount;

    private BigDecimal minimumOrder;

    private Boolean allowDiscount;

    private Boolean active;

    private Boolean freeShipping;

    private String[] requirements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(BigDecimal minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public Boolean getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(Boolean allowDiscount) {
        this.allowDiscount = allowDiscount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }
}
