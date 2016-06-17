package com.weather.web.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author ryagudin
 */
@JsonIgnoreProperties({"bytes"})
public class History {

    private int id;
    private String userName;
    private Long sendDate;
    private String filter;
    private String city;
    private int temperature;

    public History() {

    }

    public History(String userName, String filter, String city, int temperature) {
        this.userName = userName;
        this.filter = filter;
        this.city = city;
        this.temperature = temperature;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return filter;
    }

}
