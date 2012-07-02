package com.example.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "sports")
public class Sport {

    @DatabaseField(generatedId = true)
    private Integer id;

    @ForeignCollectionField
    private ForeignCollection<StudentSport> studentSports;

    @DatabaseField
    private String name;
    @DatabaseField
    private String description;

    public Sport(){

    }

    public Sport(String name, String description ){
        this.name=name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        StringBuilder buff= new StringBuilder();
        buff.append("name:"+this.name+"\n");
        buff.append("description:"+this.description+"\n");
        return buff.toString();
    }


}
