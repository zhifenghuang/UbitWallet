package com.ubit.wallet.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {

    private Info user_info;

    public Info getUser_info() {
        return user_info;
    }

    public void setUser_info(Info user_info) {
        this.user_info = user_info;
    }

    public static class Info implements Serializable {
        private String uname;
        private String avatar;
        private long uid;
        private String real_name;
        private String lev;
        private String email;
        private long referrer_id;
        private String address_eth;
        private String address_omni;
        private String address_btc;
        private String phone;
        private String phone_code;
        private String invite_url;
        private String add_time;
        private String usdt;
        private String total_tth;
        private String available_tth;
        private String done_tth;
        private int hasPayPsw;

        public int getHasPayPsw() {
            return hasPayPsw;
        }

        public void setHasPayPsw(int hasPayPsw) {
            this.hasPayPsw = hasPayPsw;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getLev() {
            return lev;
        }

        public void setLev(String lev) {
            this.lev = lev;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getReferrer_id() {
            return referrer_id;
        }

        public void setReferrer_id(long referrer_id) {
            this.referrer_id = referrer_id;
        }

        public String getAddress_eth() {
            return address_eth;
        }

        public void setAddress_eth(String address_eth) {
            this.address_eth = address_eth;
        }

        public String getAddress_omni() {
            return address_omni;
        }

        public void setAddress_omni(String address_omni) {
            this.address_omni = address_omni;
        }

        public String getAddress_btc() {
            return address_btc;
        }

        public void setAddress_btc(String address_btc) {
            this.address_btc = address_btc;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public void setPhone_code(String phone_code) {
            this.phone_code = phone_code;
        }

        public String getInvite_url() {
            return invite_url;
        }

        public void setInvite_url(String invite_url) {
            this.invite_url = invite_url;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUsdt() {
            return usdt;
        }

        public void setUsdt(String usdt) {
            this.usdt = usdt;
        }

        public String getTotal_tth() {
            return total_tth;
        }

        public void setTotal_tth(String total_tth) {
            this.total_tth = total_tth;
        }

        public String getAvailable_tth() {
            return available_tth;
        }

        public void setAvailable_tth(String available_tth) {
            this.available_tth = available_tth;
        }

        public String getDone_tth() {
            return done_tth;
        }

        public void setDone_tth(String done_tth) {
            this.done_tth = done_tth;
        }
    }

}
