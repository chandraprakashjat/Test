package com.example.testdemo;

public class User {


    private String name;
    private String phoneNumber;
    private long age;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;


    public User(String name,String phoneNumber,long age)

    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    public User(String name,String phoneNumber,long age,String detail)

    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.detail = detail;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }


}
