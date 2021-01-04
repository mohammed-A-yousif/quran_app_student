package com.example.quranappstudent.model;

public class Review {
    private int Id;
    private String Studnet;
    private String Teacher;
    private String ReviewDec;
    private String NumberOfParts;
    private String CreatedAt;

    public Review(int Id, String Student, String Teacher, String ReviewDec , String NumberOfParts, String CreatedAt) {
        this.Id = Id;
        this.Studnet = Student;
        this.Teacher = Teacher;
        this.ReviewDec = ReviewDec;
        this.NumberOfParts = NumberOfParts;
        this.CreatedAt = CreatedAt;
    }


    public int getId() {
        return Id;
    }

    public String getStudnet() {
        return Studnet;
    }

    public String getTeacher() {
        return Teacher;
    }

    public String getReviewDec() {
        return ReviewDec;
    }

    public String getNumberOfParts() {
        return NumberOfParts;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }
}