package com.example.project;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.annotations.NonNull;


public class Queue {

    public String queueName;
    public String date;
    public String elements;
    public String id;
    public Queue(String queueName, String elements){
        this.queueName=queueName;
        this.date=new Date().toString();
        this.elements=elements;
    }
    public Queue(List<String> elements){
        this.queueName="";
        this.date="";
        this.elements="";
        for (String s:elements){
            this.elements+=" " + s;
        }
    }
    public Queue(){
        this.queueName="";
        this.date="";
        this.elements="";
    }
    void setQueueName(String queueName){
        this.queueName=queueName;
    }
    @NonNull
    String getQueueName(){
        return this.queueName;
    }
    String getDate(){
        return this.date;
    }
    String getElements(){
        return this.elements;
    }
    void next(){
        List<String> elems = this.toArrayList();
        if (!elems.isEmpty()){
            elems.remove(0);
        }
        this.elements="";
        for (String s: elems){
            this.elements+=" "+s;
        }
    }
    ArrayList<String> toArrayList(){
        ArrayList<String> elems = new ArrayList<>();
        String buffer = new String("");
        for (int i=0; i<this.elements.length(); i++){
            if (this.elements.charAt(i)!=' ') {
                buffer += this.elements.charAt(i);
            } else{
                if (!buffer.isEmpty()) elems.add(buffer);
                buffer = "";
            }
        }
        if (!buffer.isEmpty()) elems.add(buffer);
        return elems;
    }
    void update(String names){
        this.elements=getElements()+" "+names;
    }
    void setDate(String date){
        this.date = date;
    }
    void updateDate(){
        this.date= new Date().toString();
    }
}
