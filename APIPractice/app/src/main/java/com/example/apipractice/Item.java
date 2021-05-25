package com.example.apipractice;

public class Item {
    String hospitalName, hospitalAddr;

    public Item(String hospitalName, String hospitalAddr) {
        this.hospitalName = hospitalName;
        this.hospitalAddr = hospitalAddr;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalAddr() {
        return hospitalAddr;
    }

    public void setHospitalName() {
        this.hospitalName = hospitalName;
    }

    public void setHospitalAddr() {
        this.hospitalAddr = hospitalAddr;
    }
}
