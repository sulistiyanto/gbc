package com.tiyanrcode.guestbookclient.model;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class GuestSearch {

    String guest_id;
    String guest_name = "";
    String guest_foto = "";
    String guest_presence = "";

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

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

    public String getGuest_presence() {
        return guest_presence;
    }

    public void setGuest_presence(String guest_presence) {
        this.guest_presence = guest_presence;
    }
}
