package com.example.doctello.Local.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "profile_table")
public class ProfileEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("UserName")
    private String UserName;

    @SerializedName("UserPhone")
    private String UserPhone;

    public ProfileEntity(int id, String userName, String userPhone) {
        this.id = id;
        UserName = userName;
        UserPhone = userPhone;
    }

    public ProfileEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
}
