package com.qdgxy.oa.meta;

/**
 * Created by liyongjun on 17/2/4.
 */
public class Customer {
    private String CustomerID;
    private String CompanyName;
    private String ContactName;

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
