package com.doctorondemand.doctorondemand.findmydoctor;

public class DrDetails {
    private String drname,Specialization,address,phoneno,email,status;

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }



    public DrDetails(String drname, String specialization, String address, String phoneno, String email, String status) {
        this.drname = drname;
        Specialization = specialization;
        this.address = address;
        this.phoneno = phoneno;
        this.email = email;
        this.status=status;
    }



}
