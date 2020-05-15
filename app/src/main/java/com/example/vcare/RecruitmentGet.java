package com.example.vcare;

public class RecruitmentGet {
    String FName;
    String FMobile;
    String FEmail;
    String FAge;
    String FAddress;

    public RecruitmentGet(){}

    public RecruitmentGet(String name, String mobile, String email, String age, String address) {
        this.FName = name;
        this.FMobile = mobile;
        this.FEmail = email;
        this.FAge = age;
        this.FAddress = address;
    }

    public String getFName() {
        return FName;
    }

    public String getFMobile() {
        return FMobile;
    }

    public String getFEmail() {
        return FEmail;
    }

    public String getFAge() {
        return FAge;
    }

    public String getFAddress() {
        return FAddress;
    }
}

