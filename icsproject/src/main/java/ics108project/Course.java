package ics108project;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;
    private int hours;
    private String prerequisite;
    private String corequisite;

    public Course (String name,int hours,String prerequisite,String corequisite){
        this.name=name;
        this.hours=hours;
        this.corequisite=corequisite;
        this.prerequisite=prerequisite;
    }
    public String getName(){
        return name;
    }
    public int getHours(){
        return hours;
    }
    public String getPrerequisite(){
        return prerequisite;
    }
    public String getCorequisite(){
        return corequisite;
    }
    public String toString(){
        return "Course name: " + name + " Hours: "+ hours;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Course){
            Course other = (Course)(o);
            if (this.name == other.getName()){
                return true;
            }
        }
        return false;
    }
}
