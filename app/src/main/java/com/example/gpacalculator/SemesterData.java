package com.example.gpacalculator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SemesterData {

    private static final String TAG = "SemesterData";

    private String degree;
    private int semesterNumber;
    private ArrayList<Course> courses;

    public SemesterData() {
        this("Degree", 0, new ArrayList<Course>());
    }

    public SemesterData(String degree, int semesterNumber, ArrayList<Course> courses) {
        setDegree(degree);
        setSemesterNumber(semesterNumber);
        setCourses(courses);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "SemesterData{" +
                "degree='" + degree + '\'' +
                ", semesterNumber=" + semesterNumber +
                ", courses=" + courses +
                '}';
    }

}
