package com.dayuan.entity;

import java.io.Serializable;

//继承接口 实现jdk的接口Serializable --------序列化
// 一个对象序列化的接口，一个类只有实现了Serializable接口，它的对象才是可序列化的。
// 因此如果要序列化某些类的对象，这些类就必须实现Serializable接口。
// 而实际上，Serializable是一个空接口，没有什么具体内容，它的目的只是简单的标识一个类的对象可以被序列化。
public class Movie implements Serializable {

    private Integer id;

    private String moviename;

    private String director;

    private String writers;

    private String starring;

    private String type;

    private String producercountries;

    private String language;

    private String releasedate;

    private String filmlength;

    private String desc;

    private String price;

    private String picpath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename == null ? null : moviename.trim();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director == null ? null : director.trim();
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers == null ? null : writers.trim();
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring == null ? null : starring.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProducercountries() {
        return producercountries;
    }

    public void setProducercountries(String producercountries) {
        this.producercountries = producercountries == null ? null : producercountries.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate == null ? null : releasedate.trim();
    }

    public String getFilmlength() {
        return filmlength;
    }

    public void setFilmlength(String filelength) {
        this.filmlength = filelength == null ? null : filelength.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }
}