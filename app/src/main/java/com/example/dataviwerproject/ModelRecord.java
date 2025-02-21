package com.example.dataviwerproject;

public class ModelRecord {
    public boolean isAdding;
    private String id, name, image, bio, phone, email, dob, addedTime, updateTime;
    private boolean isEditing;

    public ModelRecord(String id, String name, String image, String bio, String phone, String email, String dob, String addedTime, String updateTime){
        this.id = id;
        this.name = name;
        this.image = image;
        this.bio = bio;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
        this.addedTime = addedTime;
        this.updateTime = updateTime;
        this.isEditing = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public boolean isAdding() {
        return isAdding;
    }

    public void setAdding(boolean isAdding) {
        this.isAdding = isAdding;
    }
}
