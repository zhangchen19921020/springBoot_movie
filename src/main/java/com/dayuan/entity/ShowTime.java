package com.dayuan.entity;

public class ShowTime {
    private Integer id;

    private String showTimeCol;

    private Integer showDayId;

    private String language;

    private String showPlace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShowTimeCol() {
        return showTimeCol;
    }

    public void setShowTimeCol(String showTimeCol) {
        this.showTimeCol = showTimeCol == null ? null : showTimeCol.trim();
    }

    public Integer getShowDayId() {
        return showDayId;
    }

    public void setShowDayId(Integer showDayId) {
        this.showDayId = showDayId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getShowPlace() {
        return showPlace;
    }

    public void setShowPlace(String showPlace) {
        this.showPlace = showPlace == null ? null : showPlace.trim();
    }
}