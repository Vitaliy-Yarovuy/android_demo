package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.model.DatabaseHelper;
import com.example.model.Group;
import com.example.model.Sport;
import com.example.model.Student;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class StudentActivity extends OrmLiteBaseListActivity<DatabaseHelper> implements View.OnClickListener {
    private final static String TAG = "StudentActivity";

    private Student student;
    private List<Sport> sports;
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

        Button btnSave = (Button)findViewById(R.id.button_save);
        inputFirstName = (EditText)findViewById(R.id.first_name_input);
        inputLastName = (EditText)findViewById(R.id.last_name_input);
        inputNumber = (EditText)findViewById(R.id.number_input);

        btnSave.setOnClickListener(this);

        //Toast.makeText(this, "student active", Toast.LENGTH_LONG).show();

        try {
            fillStudent();
            fillSports();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Log.v(TAG, student.toString());
    }

    private  void fillStudent()throws SQLException{
        int studentId = (int) getIntent().getIntExtra(StudentListActivity.STUDENT_ID, 0);
        Dao<Student, Integer> dao = getHelper().getStudentDao();
        QueryBuilder<Student, Integer> builder = dao.queryBuilder();
        builder.where().eq(Student.ID, studentId);
        List<Student> students = dao.query(builder.prepare());
        if(students.size() == 0){
            student = new Student();
        }
        else{
            student = students.get(0);
        }

        inputFirstName.setText(student.getName());
        inputLastName.setText(student.getLastName());
        inputNumber.setText(student.getNumber());
    }

    private void fillSports()throws SQLException{
        Dao<Sport, Integer> dao = getHelper().getSportDao();
        QueryBuilder<Sport, Integer> builder = dao.queryBuilder();
        sports = dao.query(builder.prepare());

    }


    @Override
    public void onClick(View view) {
        student.setName(inputFirstName.getText().toString());
        student.setLastName(inputLastName.getText().toString());
        student.setNumber(inputNumber.getText().toString());

        try {
            Dao<Student, Integer> dao = getHelper().getStudentDao();
//            dao.createOrUpdate(student);
            if(student.getId() == null){
                dao.create(student);
            }
            else{
                dao.update(student);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(StudentListActivity.STUDENT_ID,student.getId());
        setResult(RESULT_OK,returnIntent);
        this.finish();
    }
}
