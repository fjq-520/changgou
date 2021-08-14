package com.changgou.entity;

import lombok.Data;

import java.util.List;
@Data
public class PageResult<T> {
    private Long total;//總記錄數
    private List<T> rows;//記錄

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {}
}
