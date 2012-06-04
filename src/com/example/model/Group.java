package com.example.model;


import android.os.Parcel;
import android.os.Parcelable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.*;

@Root
public class Group implements List<Student>,Parcelable {
    @Element
    private String name;
    @ElementList
    private ArrayList<Student> students;

    public Group(){

    }

    public Group(String name){
        this.name=name;
        this.students=new ArrayList<Student>() ;
    }

    public Group(String name, ArrayList<Student> students){
         this.name=name;
        this.students=students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        buff.append("name:"+this.name+"\n");
        return buff.toString();
    }
    // implement List<Student>

    @Override
    public void add(int i, Student student) {
        students.add(i,student);
    }

    @Override
    public boolean add(Student student) {
        return students.add(student);
    }

    @Override
    public boolean addAll(int i, Collection<? extends Student> students) {
        return this.students.addAll(i,students);
    }

    @Override
    public boolean addAll(Collection<? extends Student> students) {
        return this.students.addAll(students);
    }

    @Override
    public void clear() {
        students.clear();
    }

    @Override
    public boolean contains(Object o) {
        return students.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return students.containsAll(objects);
    }

    @Override
    public Student get(int i) {
        return students.get(i);
    }

    @Override
    public int indexOf(Object o) {
        return students.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return students.isEmpty();
    }

    @Override
    public Iterator<Student> iterator() {
        return students.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return students.lastIndexOf(o);
    }

    @Override
    public ListIterator<Student> listIterator() {
        return students.listIterator();
    }

    @Override
    public ListIterator<Student> listIterator(int i) {
        return students.listIterator(i);
    }

    @Override
    public Student remove(int i) {
        return students.remove(i);
    }

    @Override
    public boolean remove(Object o) {
        return students.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return students.removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return students.retainAll(objects);
    }

    @Override
    public Student set(int i, Student student) {
        return students.set(i,student);
    }

    @Override
    public int size() {
        return students.size();
    }

    @Override
    public List<Student> subList(int i, int i1) {
        return students.subList(i,i1);
    }

    @Override
    public Object[] toArray() {
        return students.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return students.toArray(ts);
    }

    //implement Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(students);
    }

    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        public Group createFromParcel(Parcel in) {
            String name = in.readString();
            ArrayList<Student> students = new ArrayList<Student>();
            in.readTypedList( students,Student.CREATOR);
            return new Group(name,students);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
