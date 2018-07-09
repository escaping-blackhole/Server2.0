package com.hjy.entity;

import java.io.Serializable;

public class Admin implements Serializable{
    private Long adminId;

    private String adminname;

    private String password;

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminname='" + adminname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Admin(Long adminId, String adminname, String password) {
        this.adminId = adminId;
        this.adminname = adminname;
        this.password = password;
    }

    public Admin() {
        super();
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname == null ? null : adminname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}