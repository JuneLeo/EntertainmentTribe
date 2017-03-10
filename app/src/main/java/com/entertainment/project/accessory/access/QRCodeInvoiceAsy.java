package com.entertainment.project.accessory.access;

import android.content.Context;

import com.entertainment.project.accessory.Conf;
import com.entertainment.project.common.Constants;
import com.entertainment.project.common.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sick on 2016/11/15.
 */
public class QRCodeInvoiceAsy extends Accessory {

    private String sellerName; //收款方
    private String money;      //发票金额
    private String occurTime;  //发票时间
    private String billCode;   //发票代码
    private String billNumber; //发票号码
    private String amount;     //价税合计
    private String tax;        //税额
    private String buyerName;  //付款方
    private String category;   //发票种类
    private ArrayList<ExpenseQRCodeInvoiceDetail> detailInfo;

    public QRCodeInvoiceAsy(HashMap hashMap, int accessoryType, Context context, Conf.BillType billType) {
        super(hashMap, accessoryType, context,billType);
    }

    public QRCodeInvoiceAsy(File file, int typeQrcodeInvoice, Context context, Conf.BillType billType) {
        super(file, typeQrcodeInvoice, context, billType);
    }

    @Override
    public void functionFromService() {
        super.functionFromService();
        if (Conf.isContainsKey(hashMap, "sellerName")) {
            sellerName = Util.parseString(hashMap.get("sellerName"));
        }
        if (Conf.isContainsKey(hashMap, "money")) {
            money = Util.parseString(hashMap.get("money"));
        }
        if (Conf.isContainsKey(hashMap, "occurTime")) {
            Date date = Util.parseDate(Util.parseString(hashMap.get("occurTime")), Constants.format.yyyy_MM_dd);
            occurTime = Constants.format.yyyy_MM_dd2.format(date);
        }
        if (Conf.isContainsKey(hashMap, "billCode")) {
            billCode = Util.parseString(hashMap.get("billCode"));
        }
        if (Conf.isContainsKey(hashMap, "billNumber")) {
            billNumber = Util.parseString(hashMap.get("billNumber"));
        }
        if (Conf.isContainsKey(hashMap, "amount")) {
            amount = Util.parseString(hashMap.get("amount"));
        }
        if (Conf.isContainsKey(hashMap, "tax")) {
            tax = Util.parseString(hashMap.get("tax"));
        }
        if (Conf.isContainsKey(hashMap, "buyerName")) {
            buyerName = Util.parseString(hashMap.get("buyerName"));
        }
        if (Conf.isContainsKey(hashMap, "category")) {
            category = Util.parseString(hashMap.get("category"));
        }
        if (Conf.isContainsKey(hashMap, "detailInfo") && hashMap.get("detailInfo") instanceof List) {
            List<HashMap<String, Object>> detailInfos = (List<HashMap<String, Object>>) hashMap.get("detailInfo");
            ArrayList<ExpenseQRCodeInvoiceDetail> details = new ArrayList<ExpenseQRCodeInvoiceDetail>();
            for (int j = 0; j < detailInfos.size(); j++) {
                HashMap<String, Object> mapDetail = detailInfos.get(j);
                ExpenseQRCodeInvoiceDetail detail = new ExpenseQRCodeInvoiceDetail();
                Object Name = mapDetail.get("Name");
                if (Name != null) {
                    detail.setName(Util.parseString(Name));
                }
                Object Quantity = mapDetail.get("Quantity");
                if (Quantity != null) {
                    detail.setQuantity(Util.parseString(Util.parseInt(Quantity, 0)));
                } else {
                    detail.setQuantity("");
                }
                Object Price = mapDetail.get("Price");
                if (Price != null) {
                    detail.setPrice(Util.parseString(Price));
                }
                Object Amount = mapDetail.get("Amount");
                if (Amount != null) {
                    detail.setAmount(Util.parseString(Amount));
                }
                Object TaxAmount = mapDetail.get("TaxAmount");
                if (TaxAmount != null) {
                    detail.setTaxAmount(Util.parseString(TaxAmount));
                }
                details.add(detail);
            }
        }
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<ExpenseQRCodeInvoiceDetail> getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(ArrayList<ExpenseQRCodeInvoiceDetail> detailInfo) {
        this.detailInfo = detailInfo;
    }
}
