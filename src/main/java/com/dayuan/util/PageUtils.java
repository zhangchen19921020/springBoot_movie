package com.dayuan.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class PageUtils {
    //单页行数
    private Integer pageRows = 2;
    //开始索引
    private Integer startIndex;
    //总行数
    private Integer totalRows;
    //总页数
    private Integer totalPages;
    //当前页码
    private Integer currentPageindex;
    //页号式导航, 最多显示页号数量为showPagesSize+1;这里显示6页
    private Integer showPagesSize = 4;

    public PageUtils(Integer totalRows, Integer currentPageindex) {
        this.totalRows = totalRows;
        this.currentPageindex = currentPageindex;

        //开始索引
        startIndex = getStartIndex();

        //总页数
        totalPages = getTotalPages();

    }


    public Integer getPageRows() {
        return pageRows;
    }

    public void setPageRows(Integer pageRows) {
        this.pageRows = pageRows;
    }

    //根据当前页码计算开始索引
    public Integer getStartIndex() {
        return (currentPageindex - 1) * pageRows;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * 总页数
     *
     * @return
     */
    public Integer getTotalPages() {
        return (totalRows % pageRows != 0) ? (totalRows / pageRows + 1) : totalRows / pageRows;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurrentPageindex() {
        return currentPageindex;
    }

    public void setCurrentPageindex(Integer currentPageindex) {
        this.currentPageindex = currentPageindex;
    }

    /**
     * 计算页码
     * @return
     */
    public List<Integer> getShowPages() {
        List<Integer> showPages = new ArrayList<>();
        int s = Math.max(currentPageindex - showPagesSize / 2, 1);//如果小于1，则取1
        int e = Math.min(s + showPagesSize, totalPages);//如果超出最大页数，取最大页数

        if (e - s < showPagesSize) {
            s = Math.max(e - showPagesSize, 1);
        }

        for (int i = s; i <= e; i++) {
            showPages.add(i);
        }

        return showPages;
    }
}
