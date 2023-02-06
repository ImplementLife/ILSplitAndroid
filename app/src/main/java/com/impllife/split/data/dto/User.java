package com.impllife.split.data.dto;

public class User {
    public Long id;
    public String name;
    public String act;
    public String phone;
    public boolean isPremium;

    public User() {
    }

    public User(Long id, String name, String act, String phone, boolean isPremium) {
        this.id = id;
        this.name = name;
        this.act = act;
        this.phone = phone;
        this.isPremium = isPremium;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }
}
