package ics108project;

import java.io.Serializable;

public class Schedule implements Serializable{
    int term;
    Section[] sections;
    public Schedule(int term,Section[] sections){
        this.term = term;
        this.sections = sections;
    }
}