package com.llv.chapiion.netdemo.net.bean;

import java.io.Serializable;
import java.util.List;

public class BaseData<T> implements Serializable {
    private int nRes;
    private String vcRes;
    private int count;
    private T object;
    private List<T> rows;

    public int getnRes() {
        return nRes;
    }

    public void setnRes(int nRes) {
        this.nRes = nRes;
    }

    public String getVcRes() {
        return vcRes;
    }

    public void setVcRes(String vcRes) {
        this.vcRes = vcRes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
