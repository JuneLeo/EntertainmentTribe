package com.entertainment.project.accessory.access;

import java.io.Serializable;

/**
 * Created by Sick on 2016/11/15.
 */
public class ExpenseQRCodeInvoiceDetail implements Serializable {
    private String Name;  //费用明细
    private String Quantity;   //商品数量
    private String Price;  //价税合计
    private String Amount; //金额
    private String TaxAmount;//税额

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        TaxAmount = taxAmount;
    }
}
