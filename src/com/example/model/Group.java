package com.example.model;


import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.*;

@DatabaseTable(tableName = "groups")
public class Group{
    public static final String ID = "id";

    @DatabaseField(generatedId = true,columnName = Group.ID)
    private Integer id;


    @DatabaseField
    private String name;

    @ForeignCollectionField
    private ForeignCollection<Student> students;

    public Group(){

    }

    public int getId() {
        return id;
    }

    public Group(String name){
        this.name = name;
    }

    public Group(String name, Collection<Student> students){
        this.name=name;
        this.students.addAll(students);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<Student> getStudents(){
        return students;
    }

    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        buff.append("name:"+this.name+"\n");
        return buff.toString();
    }
}
