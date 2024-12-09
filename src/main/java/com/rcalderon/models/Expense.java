package com.rcalderon.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Expense {

    private String uuid;
    private LocalDateTime addDate;
    private String description;
    private BigDecimal amount;
    private ExpenseCategory category;

    public Expense() {

    }

    public Expense(Builder builder) {
        this.uuid = UUID.randomUUID().toString();
        this.addDate = builder.addDate;
        this.description = builder.description;
        this.amount = builder.amount;
        this.category = builder.category;
    }

    public static class Builder {

        private  LocalDateTime addDate;
        private  String description;
        private  BigDecimal amount;
        private  ExpenseCategory category;

        public Builder setDate(LocalDateTime addDate) {
            this.addDate = addDate;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCategory(ExpenseCategory category) {
            this.category = category;
            return this;
        }

        public Expense build() {
            return new Expense(this);
        }
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }
}
