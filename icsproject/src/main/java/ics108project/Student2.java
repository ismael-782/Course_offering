package ics108project;

import java.io.Serializable;

public class Student2 implements Serializable{
    String name;
    int id;
    Course[] finishedcourses;
    public Student2(Course[] finishedcourses){
        this.finishedcourses= finishedcourses;
    }
    public Course[] getFinishedcourses() {
        return finishedcourses;
    }
}