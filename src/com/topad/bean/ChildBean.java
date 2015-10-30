package com.topad.bean;

/**
 * ${todo}<请描述这个类是干什么的>
 *
 * @author lht
 * @data: on 15/10/29 15:18
 */
public class ChildBean {
    private String userid;
    private String fullname;
    private String username;
    private boolean isChecked;

    public ChildBean(String userid, String fullname, String username) {
        this.userid = userid;
        this.fullname = fullname;
        this.username = username;
        this.isChecked = false;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public String getUserid() {
        return userid;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }
}
