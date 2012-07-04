package com.example.model;


import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@DatabaseTable(tableName = "students")
public class Student  {
    public static final String ID = "id";

    @DatabaseField(generatedId = true, columnName = Student.ID)
    private Integer id;

    @DatabaseField(canBeNull = true, foreign = true)
    private Group group;


    @ForeignCollectionField
    private ForeignCollection<StudentSport> studentSports;
    @DatabaseField
    private String name;
    @DatabaseField
    private String lastName;
    @DatabaseField
    private String number;

    public Student(){

    }

    public Student(String name, String lastName, String number ){
         this.name=name;
         this.lastName=lastName;
         this.number=number;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ForeignCollection<StudentSport> getStudentSport(){
        return studentSports;
    }

    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        buff.append("name:"+this.name+"\n");
        buff.append("lastName:"+this.lastName+"\n");
        buff.append("number:"+this.number+"\n");
        return buff.toString();
    }

}
