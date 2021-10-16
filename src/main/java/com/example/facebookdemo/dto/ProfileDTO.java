package com.example.facebookdemo.dto;

public class ProfileDTO {

    private  String fullName;

    private  String email;

    private Integer age;

    private String address;

    private boolean isFullNamePublic;

    private String password;

    private String repeatPassword;

//    private Image userImage;

    public ProfileDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFullNamePublic() {
        return isFullNamePublic;
    }

    public void setFullNamePublic(boolean fullNamePublic) {
        isFullNamePublic = fullNamePublic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    //    public Image getUserImage() {
//        return userImage;
//    }
//
//    public void setUserImage(Image userImage) {
//        this.userImage = userImage;
//    }
}
