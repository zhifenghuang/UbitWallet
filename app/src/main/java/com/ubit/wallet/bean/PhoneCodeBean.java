package com.ubit.wallet.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class PhoneCodeBean implements Serializable {

    private ArrayList<CodeBean> phone_code;

    public ArrayList<CodeBean> getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(ArrayList<CodeBean> phone_code) {
        this.phone_code = phone_code;
    }

    public static class CodeBean implements Serializable {
        private int id;
        private int code;
        private String country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }


}
