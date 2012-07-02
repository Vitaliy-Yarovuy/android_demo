package com.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Intent;
import com.example.model.DatabaseHelper;
import com.example.model.Group;
import com.example.model.Sport;
import com.example.model.Student;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

    public class GroupListActivity extends OrmLiteBaseListActivity<DatabaseHelper> implements View.OnClickListener {
    private final static String TAG = "GroupListActivity";
    private final static String FILE_FOR_DATA = "student.xml";
    private final static int GROUP_EDIT = 101;
    private final static int GROUP_REMOVE = 102;
    private final static int GROUP_ADD = 103;
    public final static String  GROUP_ID = "group_id";
    private List<Group> groups;
    private int selectedIndex;
    private DialogInterface.OnClickListener onRemoveSelected;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageButton btnAdd = (ImageButton) findViewById(R.id.button_add);
        btnAdd.setOnClickListener(this);

        addContent();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.button_edit_remove);
        final Group group = (Group) l.getItemAtPosition(position);
        selectedIndex = position;
        if(toggleButton.isChecked()){
            Intent addIntent = new Intent();
            addIntent.setClass(this, StudentListActivity.class);
            addIntent.putExtra(GroupListActivity.GROUP_ID,group.getId());
            startActivityForResult(addIntent, GROUP_EDIT);
        }else{
            onRemoveSelected = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeGroup(group);
                }
            };
            showDialog(GROUP_REMOVE);
        }
    }

    @Override
    public Dialog onCreateDialog (int id ){
        switch (id){
            case GROUP_REMOVE:
                return new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.is_delete_group))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), onRemoveSelected)
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .create();
            case GROUP_ADD:
                LayoutInflater factory = LayoutInflater.from(this);
                final View textEntryView = factory.inflate(R.layout.droup_add, null);
                return new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.create_group))
                        .setView(textEntryView)
                        .setPositiveButton(getString(R.string.create), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText input = (EditText)textEntryView.findViewById(R.id.group_name);
                                addGroup(new Group(input.getText().toString()));
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
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
        Group  group;
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GroupListActivity.GROUP_EDIT:
                    ListView list = getListView();
                    list.invalidateViews();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        showDialog(GROUP_ADD);
    }


    private void setGroup(int index,Group group){
        groups.set(index,group);
        ListView list = getListView();
        list.invalidateViews();
    }
    private void addGroup(Group group){
        try{
            Dao<Group, Integer> dao = getHelper().getGroupDao();
            dao.create(group);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        groups.add(group);
        ListView list = getListView();
        list.invalidateViews();
    }

    private void removeGroup(Group group){
        try{
            Student student;
            Dao<Group, Integer> dao = getHelper().getGroupDao();
            Dao<Student, Integer> studentDao = getHelper().getStudentDao();
            Iterator<Student> iterator = group.getStudents().iterator();
            while(iterator.hasNext()){
                studentDao.delete(iterator.next());
            }
            dao.delete(group);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        groups.remove(group);
        ListView list = getListView();
        list.invalidateViews();
    }


    @Override
    protected void onResume() {
        super.onResume();
        try{
            Dao<Group, Integer> dao = getHelper().getGroupDao();
            QueryBuilder<Group, Integer> builder = dao.queryBuilder();
            builder.limit(30L);
            groups = dao.query(builder.prepare());
        }
        catch (SQLException e){
            groups = new ArrayList<Group>();
        }

        setListAdapter(new ArrayAdapter<Group>(this, 0, groups) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                Group group = getItem(position);
                TextView label = (TextView) convertView.findViewById(android.R.id.text1);
                label.setText(group.getName());
                return convertView;
            }
        });

    }


    private void addContent() {

        List<Sport> sports;
        try{
            Dao<Sport, Integer> dao = getHelper().getSportDao();
            QueryBuilder<Sport, Integer> builder = dao.queryBuilder();
            builder.limit(1L);
            sports = dao.query(builder.prepare());
            if(sports.size()==0){
                dao.create(new Sport("Football","Football refers to a number of sports that involve, to varying degrees, kicking a ball with the foot to score a goal."));
                dao.create(new Sport("Volleyball","Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to score points by grounding a ball on the other team's court under organized rules."));
                dao.create(new Sport("Rowing","Rowing is a sport in which athletes race against each other on rivers, on lakes or on the ocean, depending upon the type of race and the discipline."));
                dao.create(new Sport("Skiing","Skiing is a way of traveling over snow, using skis strapped to one's feet. In modern times it has been mostly an athletic activity."));
                dao.create(new Sport("Biathlon","Biathlon is a term used to describe any sporting event made up of two disciplines. However, biathlon usually refers specifically to the winter sport that combines cross-country skiing and rifle shooting."));
                dao.create(new Sport("Bicycle","Bicycle racing is a competition sport in which various types of bicycles are used.Bicycle races are popular all over the world, especially in Europe."));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
