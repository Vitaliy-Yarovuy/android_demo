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
}