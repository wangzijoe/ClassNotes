package com.example.clone.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Room implements Serializable {

    private int roomNo;

    private List<Tenant> tenants;

    public Room(int roomNo, List<Tenant> tenants) {
        this.roomNo = roomNo;
        this.tenants = tenants;
    }
}
