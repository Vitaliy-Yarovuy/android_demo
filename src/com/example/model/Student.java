package com.example.model;


import android.os.Parcel;
import android.os.Parcelable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
public class Student implements Parcelable {
    @Element
    private String name;
    @Element
    private String lastName;
    @Element
    private String number;

    public Student(){

    }

    public Student(String name, String lastName, String number ){
         this.name=name;
         this.lastName=lastName;
         this.number=number;

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

    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        buff.append("name:"+this.name+"\n");
        buff.append("lastName:"+this.lastName+"\n");
        buff.append("number:"+this.number+"\n");
        return buff.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.lastName);
        parcel.writeString(this.number);
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            String name = in.readString();
            String lastName = in.readString();
            String number = in.readString();
            return new Student(name,lastName,number);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

}
