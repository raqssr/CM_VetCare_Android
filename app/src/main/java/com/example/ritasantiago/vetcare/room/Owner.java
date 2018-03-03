package com.example.ritasantiago.vetcare.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ritasantiago on 03-03-2018.
 */

@Entity (tableName = "owner")
public class Owner {
    @PrimaryKey
    private String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @ColumnInfo (name = "phone")
    private  int phone;
    public int getPhone() {return phone;}
    public void setPhone(int phone){this.phone = phone;}

    @ColumnInfo (name="address")
    private String address;
    public String getAddress(){return  address;}
    public void setAddress(String address) {this.address = address;}
}
