package com.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.model.DatabaseHelper;
import com.example.model.Group;
import com.example.model.Student;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class StudentListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {
    private final static String TAG = "StudentListActivity";
    private final static int STUDENT_ADD = 100;
    private final static int STUDENT_EDIT = 101;
    private final static int STUDENT_REMOVE = 102;
    public final static String STUDENT_ID = "student_id";
    private Group group;
    private DialogInterface.OnClickListener onRemoveSelected;
    private int selectIndex;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        try {
            int groupId = (int) getIntent().getIntExtra(GroupListActivity.GROUP_ID, 0);
            Dao<Group, Integer> dao = getHelper().getGroupDao();
            QueryBuilder<Group, Integer> builder = dao.queryBuilder();
            builder.where().eq(Group.ID, groupId);
            group = dao.query(builder.prepare()).get(0);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(StudentListActivity.this, "student on resume", Toast.LENGTH_LONG).show();

        ListView list = getListView();
        setListAdapter(new ArrayAdapter<Student>(this, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }
                //todo change approach access to list of students
                Student student = (Student)group.getStudents().toArray()[position];
                TextView label = (TextView) convertView.findViewById(android.R.id.text1);
                label.setText(student.getName());
                TextView label2 = (TextView) convertView.findViewById(android.R.id.text2);
                label2.setText(student.getLastName());
                return convertView;
            }
            @Override
            public int getCount(){
                 return group.getStudents().size();
            }
        });
        TextView groupName = (TextView) findViewById(R.id.group_name);
        groupName.setText(group.getName());
        ImageButton btnAdd = (ImageButton) findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent();
                addIntent.setClass(StudentListActivity.this, StudentActivity.class);
                startActivityForResult(addIntent, STUDENT_ADD);
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.button_edit_remove);
        selectIndex = position;
        final Student student = (Student)group.getStudents().toArray()[position];
        if (toggleButton.isChecked()) {
            Intent addIntent = new Intent();
            addIntent.setClass(this, StudentActivity.class);
            addIntent.putExtra(StudentListActivity.STUDENT_ID,student.getId());
            startActivityForResult(addIntent, STUDENT_EDIT);
        } else {
            onRemoveSelected = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeStudent(student);
                }
            };
            showDialog(STUDENT_REMOVE);
        }
    }

    @Override
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case STUDENT_REMOVE:
                return new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.is_delete_student))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), onRemoveSelected)
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create();
            default:
                return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Student student;
        int studentId;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case StudentListActivity.STUDENT_EDIT:
                    Log.v(StudentListActivity.TAG,"student edit");
                    studentId = data.getIntExtra(StudentListActivity.STUDENT_ID,0);
                    try {
                        Dao<Student, Integer> dao = getHelper().getStudentDao();
                        QueryBuilder<Student, Integer> builder = dao.queryBuilder();
                        builder.where().eq(Student.ID, studentId);
                        student = dao.query(builder.prepare()).get(0);
                        group.getStudents().update(student);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case StudentListActivity.STUDENT_ADD:
                    Log.v(StudentListActivity.TAG,"student add");
                    studentId = data.getIntExtra(StudentListActivity.STUDENT_ID,0);
                    try {
                        Dao<Student, Integer> dao = getHelper().getStudentDao();
                        QueryBuilder<Student, Integer> builder = dao.queryBuilder();
                        builder.where().eq(Student.ID, studentId);
                        student = dao.query(builder.prepare()).get(0);
                        student.setGroup(group);
                        dao.update(student);
                        group.getStudents().update(student);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    getListView().invalidate();
                    break;
            }
        }
    }


    private void removeStudent(Student student) {
        group.getStudents().remove(student);
        try{
            Dao<Student, Integer> dao = getHelper().getStudentDao();
            dao.delete(student);
            group.getStudents().update(student);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        Log.v(StudentListActivity.TAG,"student remove");
        getListView().invalidate();
    }

}
