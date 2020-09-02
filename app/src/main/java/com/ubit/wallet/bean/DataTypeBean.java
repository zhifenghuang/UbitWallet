package com.ubit.wallet.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DataTypeBean implements Serializable {

    private ArrayList<DataType> list;

    public ArrayList<DataType> getList() {
        return list;
    }

    public void setList(ArrayList<DataType> list) {
        this.list = list;
    }

    public static class DataType implements Serializable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
