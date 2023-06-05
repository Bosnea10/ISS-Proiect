package com.example.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table( name = "programmers" )
public class Programmer implements Identifiable<Integer>{

    private Integer id;
    private String name;
    private String username;
    private String password;
    private String companyName;

    public Programmer(){}

    public Programmer(String name, String username, String password, String companyName) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.companyName = companyName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
