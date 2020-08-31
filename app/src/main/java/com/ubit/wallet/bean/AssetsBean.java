package com.ubit.wallet.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AssetsBean implements Serializable {

    private ArrayList<ItemBean> list;
    private String total;

    public ArrayList<ItemBean> getList() {
        return list;
    }

    public void setList(ArrayList<ItemBean> list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public static class ItemBean implements Serializable {
        private int id;
        private String name;
        private String tousdt;
        private String amount;
        private String fullName;
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

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

        public String getTousdt() {
            return tousdt;
        }

        public void setTousdt(String tousdt) {
            this.tousdt = tousdt;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }


}
