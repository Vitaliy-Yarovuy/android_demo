package com.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Intent;
import com.example.model.Group;
import com.example.model.GroupRepository;
import com.example.model.Student;

public class GroupListActivity extends ListActivity implements View.OnClickListener {
    private final static String TAG = "GroupListActivity";
    private final static String FILE_FOR_DATA = "student.xml";
    private final static int GROUP_EDIT = 101;
    private final static int GROUP_REMOVE = 102;
    private final static int GROUP_ADD = 103;
    public final static String GROUP = "group";
    private GroupRepository groups;
    private int selectedIndex;
    private DialogInterface.OnClickListener onRemoveSelected;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try{
            groups = GroupRepository.load(openFileInput(FILE_FOR_DATA));
        }catch (Exception e){
            groups = new GroupRepository();
            addContent();
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
        ImageButton btnAdd = (ImageButton) findViewById(R.id.button_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onDestroy(){
        try{
            GroupRepository.save(openFileOutput(FILE_FOR_DATA,MODE_PRIVATE),groups);
        }
        catch(Exception e){
            Toast.makeText(this,getString(R.string.data_not_saved),Toast.LENGTH_SHORT);
        }
        super.onDestroy();

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ToggleButton toggleButton = (ToggleButton)findViewById(R.id.button_edit_remove);
        Group group = (Group) l.getItemAtPosition(position);
        selectedIndex = position;
        if(toggleButton.isChecked()){
            Intent addIntent = new Intent();
            addIntent.setClass(this, StudentListActivity.class);
            addIntent.putExtra(GroupListActivity.GROUP,group);
            startActivityForResult(addIntent, GROUP_EDIT);
        }else{
            onRemoveSelected = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeGroup(selectedIndex);
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
                    group = (Group)data.getParcelableExtra(GroupListActivity.GROUP);
                    setGroup(selectedIndex,group) ;
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
        groups.add(group);
        ListView list = getListView();
        list.invalidateViews();
    }

    private void removeGroup(int index){
        groups.remove(index);
        ListView list = getListView();
        list.invalidateViews();
    }


    private void addContent() {
        Group group1 = new Group("group-1");
        group1.add(new Student("Ava","Henderson","082212323"));
        group1.add(new Student("Amm","Vatson","0811123243"));
        group1.add(new Student("Dirsy","Villa","0811123243"));
        groups.add(group1);
        Group group2 = new Group("group-2");
        group2.add(new Student("Erica","Sundry","0823453"));
        group2.add(new Student("Greta","Gersy","081345"));
        group2.add(new Student("Masha","Drendy","08345"));
        groups.add(group2);
    }

}
