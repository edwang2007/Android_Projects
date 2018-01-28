package hu.ait.studybuddy.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jana on 7/1/2017.
 */

public class Class extends RealmObject {
    String className;
    String professor;
    @PrimaryKey
    private String classId;

    public Class(){

    }

    public Class(String className, String professor) {
        this.className = className;
        this.professor = professor;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
