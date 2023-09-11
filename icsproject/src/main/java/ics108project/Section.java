package ics108project;

import java.io.Serializable;
import javafx.scene.layout.HBox;

public class Section implements Serializable{
    private Course course;
    private String section;
    private String activity;
    private String crn;
    private String instructor;
    private String day;
    private String time;
    private String location;
    private String status;
    private String waitlist;
    private transient HBox regestered;

    public Section(Course course,String sec,String activity ,String crn,String instructor,String day,String time,String location,String status,String waitlist){
        this.course = course;
        this.time = time;
        this.crn=crn;
        this.activity=activity;
        this.location = location;
        this.instructor = instructor;
        this.day = day;
        this.status= status;
        this.waitlist= waitlist;
        this.section = sec;
        this.regestered = new HBox();
        this.regestered.setStyle("-fx-background-color: #E6E6FA;");
        
    }

    public String getActivity(){
        return activity;
    }

    public String getSection(){
        return section;
    }

    public String getStatus() {
        return status;
    }
    public String getWaitlist() {
        return waitlist;
    }
    public String getCourseName(){
        return course.getName();
    }
    public String getName(){
        return course.getName();
    }
    
    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }


    public Course getCourse() {
        return course;
    }


    public String getCrn() {
        return crn;
    }


    public String getInstructor() {
        return instructor;
    }


    public String getDay() {
        return day;
    }

    public HBox getRegestered(){
        return regestered;
    }
    public void setStyle(String x){
        regestered.setStyle(x);
    }
    //----------------- setters------------//
    public void setTime(String time) {
        this.time = time;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String toString(){
        return "Course: " + course.getName() + " Time: " + time + "  Location: "+ location;
    }

}