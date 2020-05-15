package com.example.vcare;

public class Packedfooddata
{
    String Foodtitle;
    String Fooddescription;
    String Foodexpirydate;
    String Contactforfood;
    String Pickupaddress;
    String procure;

    public Packedfooddata(){}

    public Packedfooddata(String foodtitle, String fooddescription, String foodexpiry, String foodcontact, String foodaddress,String procure)
    {
        this.Foodtitle = foodtitle;
        this.Fooddescription = fooddescription;
        this.Foodexpirydate = foodexpiry;
        this.Contactforfood = foodcontact;
        this.Pickupaddress = foodaddress;
        this.procure=procure;
    }

    public String getFoodtitle() {
        return Foodtitle;
    }

    public void setFoodtitle(String foodtitle) {
        this.Foodtitle = foodtitle;
    }

    public String getFooddescription() {
        return Fooddescription;
    }

    public void setFooddescription(String fooddescription) {
        this.Fooddescription = fooddescription;
    }

    public String getFoodexpiry() {
        return Foodexpirydate;
    }

    public void setFoodexpiry(String foodexpiry) {
        this.Foodexpirydate = foodexpiry;
    }

    public String getFoodcontact() {
        return Contactforfood;
    }

    public void setFoodcontact(String foodcontact) {
        this.Contactforfood = foodcontact;
    }

    public String getFoodaddress() {
        return Pickupaddress;
    }

    public void setFoodaddress(String foodaddress) {
        this.Pickupaddress = foodaddress;
    }

    public String getProcure() {
        return procure;
    }

    public void setProcure(String procure) {
        this.procure = procure;
    }
}
