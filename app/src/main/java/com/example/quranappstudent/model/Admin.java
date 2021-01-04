package com.example.quranappstudent.model;

public class Admin {
    private int Id;
    private int UserType;
    private String Name;
    private  int IdTeacher;
    private String PhoneNumber;

    public Admin(int Id, int UserType, String Name, int IdTeacher , String PhoneNumber) {
        this.Id = Id;
        this.Name = Name;
        this.IdTeacher = IdTeacher;
        this.PhoneNumber = PhoneNumber;
        this.UserType = UserType;
    }

    public int getId() {
        return Id;
    }

    public int getUserType() {
        return UserType;
    }

    public String getName() {
        return Name;
    }

    public int getIdTeacher() {
        return IdTeacher;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

}