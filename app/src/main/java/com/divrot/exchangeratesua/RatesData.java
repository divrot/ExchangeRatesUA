package com.divrot.exchangeratesua;

import java.util.Date;

/**
 * Created by divrot on 24.11.16.
 */
public class RatesData {

    String ccy = null;
    String ccy_name_ru = null;
    String ccy_name_ua = null;
    String ccy_name_en = null;
    double buy = 0;
    double unit = 0;
    Date date = null;

    //Setters
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public void setCcy_name_ru(String ccy_name_ru) {
        this.ccy_name_ru = ccy_name_ru;
    }

    public void setCcy_name_ua(String ccy_name_ua) {
        this.ccy_name_ua = ccy_name_ua;
    }

    public void setCcy_name_en(String ccy_name_en) {
        this.ccy_name_en = ccy_name_en;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    //Getters
    public String getCcy() {
        return ccy;
    }

    public String getCcy_name_ru() {
        return ccy_name_ru;
    }

    public String getCcy_name_ua() {
        return ccy_name_ua;
    }

    public String getCcy_name_en() {
        return ccy_name_en;
    }

    public double getBuy() {
        return buy;
    }

    public double getUnit() {
        return unit;
    }

    public Date getDate() {
        return date;
    }
}
