package com.tiyanrcode.guestbookclient.model;

/**
 * Created by sulistiyanto on 3/25/2015.
 */
public class Family {

    String guest_name = "";
    String guest_foto = "";
    String family_name = "";
    String family_sex = "";

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_foto() {
        return guest_foto;
    }

    public void setGuest_foto(String guest_foto) {
        this.guest_foto = guest_foto;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getFamily_sex() {
        return family_sex;
    }

    public void setFamily_sex(String family_sex) {
        this.family_sex = family_sex;
    }
}
