package com.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.model.Group;
import com.example.model.Student;

public class StudentListActivity extends ListActivity {
    private final static String TAG = "StudentListActivity";
    private final static int STUDENT_ADD = 100;
    private final static int STUDENT_EDIT = 101;
    private final static int STUDENT_REMOVE = 102;
    public final static String STUDENT = "student";
    private Group group;
    private DialogInterface.OnClickListener onRemoveSelected;
    private Button btnSave;
    private int selectIndex;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        group = (Group)getIntent().getParcelableExtra(GroupListActivity.GROUP);
        ListView list = getListView();
        setListAdapter(new ArrayAdapter<Student>(this, 0, group) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }
                Student student = (Student) getItem(position);
                TextView label = (TextView) convertView.findViewById(android.R.id.text1);
                label.setText(student.getName());
                TextView label2 = (TextView) convertView.findViewById(android.R.id.text2);
                label2.setText(student.getLastName());
                return convertView;
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
        btnSave = (Button)findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(GroupListActivity.GROUP,group);
                setResult(RESULT_OK,returnIntent);
                StudentListActivity.this.finish();
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.button_edit_remove);
        selectIndex = position;
        Student student = (Student) l.getItemAtPosition(position);
        if(toggleButton.isChecked()){
            Intent addIntent = new Intent();
            addIntent.setClass(this, StudentActivity.class);
            addIntent.putExtra(StudentListActivity.STUDENT,student);
            startActivityForResult(addIntent, STUDENT_EDIT);
        }else{
            onRemoveSelected = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeStudent(selectIndex);
                }
            };
            showDialog(STUDENT_REMOVE);
        }
    }

    @Override
    public Dialog onCreateDialog (int id ){
        switch (id){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Student  student;
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case StudentListActivity.STUDENT_EDIT:
                    student = (Student)data.getParcelableExtra(StudentListActivity.STUDENT);
                    setStudent(selectIndex,student) ;
                    break;
                case StudentListActivity.STUDENT_ADD:
                    student = (Student)data.getParcelableExtra(StudentListActivity.STUDENT);
                    addStudent(student) ;
                    break;
            }
        }
    }

    private void setStudent(int index,Student student){
        group.set(index, student);
        onListChange();
    }

    private void addStudent(Student student){
        group.add(student);
        onListChange();
    }

    private void removeStudent(int index){
        group.remove(index);
        onListChange();
    }

    private void onListChange(){
        btnSave.setVisibility(View.VISIBLE);
        ListView list = getListView();
        list.invalidateViews();
    }




}
