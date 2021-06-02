package com.om.springboot.dto;

public class Test {

    Integer id;
    String testid;
    String testname;

    public Test(Integer id,String testid,String testname){
        this.id = id;
        this.testid = testid;
        this.testname = testname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }
}
