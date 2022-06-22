package com.example.sendasnack.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "riders")
public class Rider {
    // primary key would often be an auto-generated number; in this case, we are using the email
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String phoneNumber;

    @Ignore
    private String status;

    public Rider(@NonNull String email, @NonNull String name, @NonNull String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
