package com.example.acceptance.bean;

/**
 * @author :created by ${ WYW }
 * 时间：2019/10/12 14
 */
public class PackageBean  {
    private String name;
    private String path;

    public PackageBean(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
