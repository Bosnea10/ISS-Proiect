package com.example.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table( name = "bugs" )
public class Bug implements Identifiable<Integer> {

    private Integer id;
    private String name;
    private String description;
    private String projectName;
    private String programmerName;

    public Bug() {}

    public Bug(String name, String description, String projectName, String programmerName) {
        this.name = name;
        this.description = description;
        this.projectName = projectName;
        this.programmerName = programmerName;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "programmer_name")
    public String getProgrammerName() {
        return programmerName;
    }

    public void setProgrammerName(String programmerName) {
        this.programmerName = programmerName;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectName=" + projectName +
                ", programmerName=" + programmerName +
                '}';
    }
}
