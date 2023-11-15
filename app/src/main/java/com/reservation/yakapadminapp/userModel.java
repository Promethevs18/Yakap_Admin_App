package com.reservation.yakapadminapp;

public class userModel {
    String address, email, fullName, paymentMode, phoneNum, pwd, pwdIdLink, receiptLink,
           section, ticketsBought, transactNo, confirmedTime, confirmedBy;
    Double totalPrice;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwdIdLink() {
        return pwdIdLink;
    }

    public void setPwdIdLink(String pwdIdLink) {
        this.pwdIdLink = pwdIdLink;
    }

    public String getReceiptLink() {
        return receiptLink;
    }

    public void setReceiptLink(String receiptLink) {
        this.receiptLink = receiptLink;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTicketsBought() {
        return ticketsBought;
    }

    public void setTicketsBought(String ticketsBought) {
        this.ticketsBought = ticketsBought;
    }

    public String getTransactNo() {
        return transactNo;
    }

    public void setTransactNo(String transactNo) {
        this.transactNo = transactNo;
    }

    public String getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(String confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public userModel() {
    }

    public userModel(String address, String email, String fullName, String paymentMode, String phoneNum, String pwd, String pwdIdLink, String receiptLink, String section, String ticketsBought, String transactNo, String confirmedTime, String confirmedBy, Double totalPrice) {
        this.address = address;
        this.email = email;
        this.fullName = fullName;
        this.paymentMode = paymentMode;
        this.phoneNum = phoneNum;
        this.pwd = pwd;
        this.pwdIdLink = pwdIdLink;
        this.receiptLink = receiptLink;
        this.section = section;
        this.ticketsBought = ticketsBought;
        this.transactNo = transactNo;
        this.confirmedTime = confirmedTime;
        this.confirmedBy = confirmedBy;
        this.totalPrice = totalPrice;
    }
}
