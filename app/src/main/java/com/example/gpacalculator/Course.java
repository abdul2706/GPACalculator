package com.example.gpacalculator;

import android.util.Log;
import org.json.JSONArray;
import java.util.ArrayList;

public class Course {

    private static final String TAG = "Course";

    private String courseTitle;
    private int courseCreditHours;

    Course(String courseTitle, int courseCreditHours) {
        setCourseTitle(courseTitle);
        setCourseCreditHours(courseCreditHours);
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseCreditHours() {
        return courseCreditHours;
    }

    public void setCourseCreditHours(int courseCreditHours) {
        this.courseCreditHours = courseCreditHours;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseTitle='" + courseTitle + '\'' +
                ", courseCreditHours=" + courseCreditHours +
                '}';
    }

    public static ArrayList<Course> convertJSONtoCourses(JSONArray jsonArray) {
        Log.d(TAG, "convertJSONtoCourses: starts");
        Log.d(TAG, "convertJSONtoCourses: jsonArray -> " + jsonArray);
        ArrayList<Course> arrayList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray coursesArray = jsonArray.getJSONArray(i);
                String name = coursesArray.getString(0);
                int creditHours = coursesArray.getInt(1);
                arrayList.add(new Course(name, creditHours));
            }
        } catch(Exception e) {
            Log.e(TAG, "convertJSONtoCourses: e -> " + e.getMessage());
            e.printStackTrace();
        }

        Log.d(TAG, "convertJSONtoCourses: ends");
        return arrayList;
    }

}


