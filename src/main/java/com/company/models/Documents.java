package com.company.models;

public class Documents {
    private Integer id;
    private String value;

    public Documents(){

    }

    public Documents(String value) {
        this.value = value;
    }

    public Documents(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
