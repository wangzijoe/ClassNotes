package com.example.clone.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tenant implements Serializable {

    private String name;

    private String tel;

    public Tenant(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }
}
