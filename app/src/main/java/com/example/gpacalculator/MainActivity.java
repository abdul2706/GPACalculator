package com.example.gpacalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final ArrayList<String> DEGREES = new ArrayList<>(Arrays.asList("bscis"));

    public static ArrayList<SemesterData> semesterDataArrayList = new ArrayList<>();
    public static String CURRENT_DEGREE;
    public static int CURRENT_SEMESTER;
//    public static int[] expectedNumbers;
//    public static int[] creditHours;

    private ActionBar toolbar;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private Button buttonCalculate;
    private TextView textViewGPA, textViewHelp;
    private double GPA = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starts");

        toolbar = getSupportActionBar();

        loadSemesterData(loadJSONData());
        recyclerView = findViewById(R.id.recyclerViewSemester);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(this, new ArrayList<Course>());
        recyclerView.setAdapter(customAdapter);

        textViewHelp = findViewById(R.id.textViewHelp);
        textViewGPA = findViewById(R.id.textViewGPA);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");
                GPA = calculateGPA();
                textViewGPA.setText(String.format(Locale.getDefault(), "%.2f", GPA));
                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.about) {
            Toast.makeText(this, "This App is created by Abdul Rehman Khan from BSCIS(17-21).\nAll Rights Reserved.", Toast.LENGTH_LONG).show();
        } else if(item.getItemId() == R.id.add_course) {
            Toast.makeText(this, "Alert: Under Construction", Toast.LENGTH_SHORT).show();
        } else {
            textViewHelp.setVisibility(View.INVISIBLE);
            String title = item.getTitle().toString();
            toolbar.setSubtitle(title);
            String[] titleSplit = title.split(" ");
            CURRENT_DEGREE = titleSplit[0];
            CURRENT_SEMESTER = Integer.parseInt(titleSplit[1].substring(titleSplit[1].length() - 1));
            int semesterIndex = DEGREES.indexOf(CURRENT_DEGREE.toLowerCase()) * 8 + CURRENT_SEMESTER - 1;
            ArrayList<Course> courseArrayList = semesterDataArrayList.get(semesterIndex).getCourses();
//            expectedNumbers = new int[courseArrayList.size()];
//            creditHours = new int[courseArrayList.size()];
//            for(int i = 0; i < creditHours.length; i++) {
//                creditHours[i] = courseArrayList.get(i).getCourseCreditHours();
//            }
            customAdapter = new CustomAdapter(this, courseArrayList);
            recyclerView.setAdapter(customAdapter);
        }

        return true;
    }

    public double calculateGPA() {
        int listItems, totalCreditHours = 0;
        double sum = 0.0;
        GPA = 0.0;
        CustomAdapter tempAdapter = ((CustomAdapter)recyclerView.getAdapter());
        if(tempAdapter == null) {
            return GPA;
        }
        ArrayList<CustomAdapter.ViewHolder> viewHolderArrayList = tempAdapter.getViewHolders();
        listItems = tempAdapter.getItemCount();
//        if(viewHolderArrayList.size() != listItems) {
//            Toast.makeText(this, "Alert: Fill All Number Fields", Toast.LENGTH_SHORT).show();
//            return GPA;
//        }
        for(int i = 0; i < listItems; i++) {
            CustomAdapter.ViewHolder viewHolder = viewHolderArrayList.get(i);
            String expectedMarksStr = viewHolder.courseExpectedMarks.getText().toString();
            String creditHoursStr = viewHolder.courseCreditHours.getText().toString();
            int expectedMarksInt = (!expectedMarksStr.equals("")) ? Integer.parseInt(expectedMarksStr) : 0;
            int creditHoursInt = (!creditHoursStr.equals("")) ? Integer.parseInt(creditHoursStr.substring(creditHoursStr.length() - 1)) : 0;
            if(expectedMarksInt == 0) {
                continue;
            }
            sum += (mapToGPA(expectedMarksInt) * creditHoursInt);
            totalCreditHours += creditHoursInt;
        }

        GPA = sum / totalCreditHours;
        return GPA;
    }

    public void update() {
        customAdapter.notifyDataSetChanged();
    }

    public double mapToGPA(int temp) {
        if(temp >= 0.0 && temp <= 100){
            if(temp >= 80) return 4.0;
            else if(temp >= 75) return 3.67;
            else if(temp >= 70) return 3.33;
            else if(temp >= 65) return 3.0;
            else if(temp >= 60) return 2.67;
            else if(temp >= 55) return 2.33;
            else if(temp >= 50) return 2.0;
            else if(temp >= 45) return 1.0;
            else return 0.0;
        }
        return 0.0;
    }

    private JSONObject loadJSONData() {
        Log.d(TAG, "loadJSONData: starts");
        JSONObject jsonCoursesData = null;
        int size, readResult;
        byte[] bytes;
        String jsonDataString;
        InputStream inputStream;
        try {
            inputStream = getAssets().open("data.json");
            size = inputStream.available();
            bytes = new byte[size];
            readResult = inputStream.read(bytes);
            inputStream.close();
            jsonDataString = new String(bytes, StandardCharsets.UTF_8);
            jsonCoursesData = new JSONObject(jsonDataString);
        } catch(Exception e) {
            Log.e(TAG, "loadJSONData: e -> " + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "loadJSONData: ends");
        return jsonCoursesData;
    }

    void loadSemesterData(JSONObject jsonCoursesData) {
        Log.d(TAG, "loadSemesterData: starts");
        for (String degree1 : DEGREES) {
            try {
                JSONObject degree = jsonCoursesData.getJSONObject(degree1);
                for (int j = 0; j < degree.length(); j++) {
                    JSONArray semester = degree.getJSONArray("semester" + (j + 1));
                    ArrayList<Course> courses = Course.convertJSONtoCourses(semester);
                    semesterDataArrayList.add(new SemesterData(degree1, j + 1, courses));
                }
            } catch (Exception e) {
                Log.e(TAG, "onOptionsItemSelected: e -> " + e.getMessage());
                e.printStackTrace();
            }

        }
        Log.d(TAG, "loadSemesterData: ends");
    }

}
