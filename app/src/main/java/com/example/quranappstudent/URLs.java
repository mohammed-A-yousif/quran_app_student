package com.example.quranappstudent;

public class URLs {

//    public  static String BaseUrl = "http://10.0.2.2:5000/";
//    public  static String BaseUrl = "https://aletgan-api.herokuapp.com/";
    public  static String BaseUrl = "https://aletgan-api-dev.herokuapp.com/";

    public static String Login = BaseUrl + "admin_login/";
    public static String AddTeacher = BaseUrl + "teacher_register/";

    public static String GetTeachers = BaseUrl + "teachers/";
    public static String GetStudents = BaseUrl + "students/";

    public static String AddStudent = BaseUrl + "student_register/";

    public static String getBaseUrl() {
        return BaseUrl;
    }
}
