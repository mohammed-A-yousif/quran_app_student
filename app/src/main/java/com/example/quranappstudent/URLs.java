package com.example.quranappstudent;

public class URLs {

    public  static String BaseUrl = "http://10.0.2.2:5000/";
//    public  static String BaseUrl = "https://aletgan-api.herokuapp.com/";
//    public  static String BaseUrl = "https://aletgan-api-dev.herokuapp.com/";

    public static String Login = BaseUrl + "student_login/";

    public static String GetTask = BaseUrl + "tasks_student/";
    public static String ChangeTaskStatus = BaseUrl + "edit_task/";



    public static String getBaseUrl() {
        return BaseUrl;
    }
}
