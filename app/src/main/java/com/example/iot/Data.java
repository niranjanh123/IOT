package com.example.iot;

public class Data {
    String mail,pass,repass;

    public Data(String mail, String pass, String repass) {
        this.mail = mail;
        this.pass = pass;
        this.repass = repass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
}
