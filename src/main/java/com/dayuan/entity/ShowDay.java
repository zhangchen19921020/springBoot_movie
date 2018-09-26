package com.dayuan.entity;

public class ShowDay {
    private Integer id;

    private Integer movieId;

    private String showDayCol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getShowDayCol() {
        return showDayCol;
    }

    public void setShowDayCol(String showDayCol) {
        this.showDayCol = showDayCol == null ? null : showDayCol.trim();
    }
}