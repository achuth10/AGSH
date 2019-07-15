package com.example.agsh.Models;

public class InvitedUser {
    private String number;
    private String amt;
    private String by;

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    private String invitee;
InvitedUser()
{

}

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public InvitedUser(String toString, String toString1, String by) {
        number = toString;
        amt=toString1;
        this.by = by;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
