package com.example.model;


import android.util.Log;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

@Root
public class GroupRepository implements List<Group> {

    public static GroupRepository load(InputStream inStream)throws java.lang.Exception{
        Serializer serializer = new Persister();
        return serializer.read(GroupRepository.class, inStream);
    }

    public static void save(OutputStream inStream,GroupRepository group)throws java.lang.Exception{
        Serializer serializer = new Persister();
        serializer.write(group, inStream);
    }

    @ElementList
    private ArrayList<Group> groups;

    public GroupRepository(){
        this.groups = new ArrayList<Group>();
    }
    public GroupRepository( ArrayList<Group> groups){
        this.groups = groups;
    }

    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        return buff.toString();
    }


    // implement List<Group>

    @Override
    public void add(int i, Group group) {
        groups.add(i,group);
    }

    @Override
    public boolean add(Group group) {
        return groups.add(group);
    }

    @Override
    public boolean addAll(int i, Collection<? extends Group> groups) {
        return this.groups.addAll(i,groups);
    }

    @Override
    public boolean addAll(Collection<? extends Group> groups) {
        return this.groups.addAll(groups);
    }

    @Override
    public void clear() {
        groups.clear();
    }

    @Override
    public boolean contains(Object o) {
        return groups.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return groups.containsAll(objects);
    }

    @Override
    public Group get(int i) {
        return groups.get(i);
    }

    @Override
    public int indexOf(Object o) {
        return groups.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return groups.isEmpty();
    }

    @Override
    public Iterator<Group> iterator() {
        return groups.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return groups.lastIndexOf(o);
    }

    @Override
    public ListIterator<Group> listIterator() {
        return groups.listIterator();
    }

    @Override
    public ListIterator<Group> listIterator(int i) {
        return groups.listIterator(i);
    }

    @Override
    public Group remove(int i) {
        return groups.remove(i);
    }

    @Override
    public boolean remove(Object o) {
        return groups.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return groups.removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return groups.retainAll(objects);
    }

    @Override
    public Group set(int i, Group group) {
        return groups.set(i,group);
    }

    @Override
    public int size() {
        return groups.size();
    }

    @Override
    public List<Group> subList(int i, int i1) {
        return groups.subList(i,i1);
    }

    @Override
    public Object[] toArray() {
        return groups.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return groups.toArray(ts);
    }
}
