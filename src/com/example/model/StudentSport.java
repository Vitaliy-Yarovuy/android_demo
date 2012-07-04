package com.example.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "student_sports")
public class StudentSport {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true)
    private Student student;
    @DatabaseField(foreign = true)
    private Sport sport;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}