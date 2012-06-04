package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.model.Student;

public class StudentActivity extends Activity implements View.OnClickListener {
    private final static String TAG = "StudentActivity";

    private Student student;
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputNumber;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_edit);

        student = (Student)getIntent().getParcelableExtra(StudentListActivity.STUDENT);
        if(student == null){
            student = new Student();
        }
        Button btnSave = (Button)findViewById(R.id.button_save);
        inputFirstName = (EditText)findViewById(R.id.first_name_input);
        inputLastName = (EditText)findViewById(R.id.last_name_input);
        inputNumber = (EditText)findViewById(R.id.number_input);

        inputFirstName.setText(student.getName());
        inputLastName.setText(student.getLastName());
        inputNumber.setText(student.getNumber());

        btnSave.setOnClickListener(this);
        Log.v(TAG, student.toString());
    }

    @Override
    public void onClick(View view) {
        student.setName(inputFirstName.getText().toString());
        student.setLastName(inputLastName.getText().toString());
        student.setNumber(inputNumber.getText().toString());
        
        
        Intent returnIntent = new Intent();
        returnIntent.putExtra(StudentListActivity.STUDENT,student);
        setResult(RESULT_OK,returnIntent);
        this.finish();
    }
}
