package com.example.propelrrhandsonexam;

public class Response {

    private String id, fullName, emailAddress, mobileNumber, dateOfBirth, gender;
    private int age;

    public Response() {
    }

    public Response(String fullName, String emailAddress, String mobileNumber, String dateOfBirth, String gender, int age) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.age = age;
    }

    public Response(String id, String fullName, String emailAddress, String mobileNumber, String dateOfBirth, String gender, int age) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
