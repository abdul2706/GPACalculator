package com.example.gpacalculator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";

    private MainActivity mainActivity;
    private ArrayList<Course> courseArrayList;
    private ArrayList<ViewHolder> viewHolders;

    public CustomAdapter(MainActivity mainActivity, ArrayList<Course> arrayList) {
        this.mainActivity = mainActivity;
        this.courseArrayList = arrayList;
        this.viewHolders = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_detail, parent, false);
        viewHolders.add(new ViewHolder(view));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");
        final int index = position;
        holder.courseTitle.setText(courseArrayList.get(position).getCourseTitle());
        holder.courseCreditHours.setText(String.format(Locale.getDefault(), "Credit Hours: %d", courseArrayList.get(position).getCourseCreditHours()));
//        holder.courseDetailLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d(TAG, "onLongClick: index -> " + index);
//                Toast.makeText(mainActivity, courseArrayList.get(index).getCourseTitle() + " deleted", Toast.LENGTH_LONG).show();
//                courseArrayList.remove(index);
//                viewHolders.remove(index);
//                mainActivity.update();
//                return false;
//            }
//        });
        Log.d(TAG, "onBindViewHolder: ends");
    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

//    public void setCourseArrayList(ArrayList<Course> arrayList) {
//        this.courseArrayList = arrayList;
//        mainActivity.update();
//        this.viewHolders = new ArrayList<>();
//    }

    public ArrayList<ViewHolder> getViewHolders() {
        return viewHolders;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout courseDetailLayout;
        TextView courseTitle, courseCreditHours;
        EditText courseExpectedMarks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseDetailLayout = itemView.findViewById(R.id.relativeLayoutCourseDetail);
            courseTitle = courseDetailLayout.findViewById(R.id.courseTitle);
            courseCreditHours = courseDetailLayout.findViewById(R.id.creditHours);
            courseExpectedMarks = courseDetailLayout.findViewById(R.id.expectedMarks);
        }
    }

}































//        holder.courseExpectedMarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                EditText editText = (EditText)v;
//                Log.d(TAG, "onFocusChange: hasFocus -> " + hasFocus);
//                if(!hasFocus) {
//                    Log.d(TAG, "onFocusChange: focusOut");
//                    String expectedNumberStr = editText.getText().toString();
//                    int expectedNumberInt = (!expectedNumberStr.equals("")) ? Integer.parseInt(expectedNumberStr) : 0;
//                    Log.d(TAG, "onFocusChange: index -> " + index);
//                    Log.d(TAG, "onFocusChange: expectedNumberInt -> " + expectedNumberInt);
//                    MainActivity.setExpectedNumbers(index, expectedNumberInt);
//                } else {
//                    Log.d(TAG, "onFocusChange: focusIn");
//                }
//            }
//        });
//
//        holder.courseExpectedMarks.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                Log.d(TAG, "onKey: index -> " + index);
////                Log.d(TAG, "onKey: v -> " + v);
////                Log.d(TAG, "onKey: keyCode -> " + keyCode);
////                Log.d(TAG, "onKey: event -> " + event);
//                EditText editText = (EditText)v;
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    String expectedNumberStr = editText.getText().toString();
//                    int expectedNumberInt = (!expectedNumberStr.equals("")) ? Integer.parseInt(expectedNumberStr) : 0;
//                    Log.d(TAG, "onFocusChange: index -> " + index);
//                    Log.d(TAG, "onFocusChange: expectedNumberInt -> " + expectedNumberInt);
//                    MainActivity.setExpectedNumbers(index, expectedNumberInt);
//                } else {
//                    Log.d(TAG, "onKey: keyCode -> " + KeyEvent.keyCodeToString(keyCode));
//                }
//                return false;
//            }
//        });











