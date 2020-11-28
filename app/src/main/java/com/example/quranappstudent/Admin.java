package com.example.quranappstudent;

public class Admin {
    private int Id;
    private int UserType;
    private String Name;
    private String PhoneNumber;

    public Admin(int Id, int UserType, String Name , String PhoneNumber) {
        this.Id = Id;
        this.Name = Name;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

}