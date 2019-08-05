package com.llv.chapiion.netdemo.net;

public interface UrlConstants {
    String PROTOCOL = "http://";
    String IP_WITH_PORT = "192.168.1.164:8108";
    String SEPARATOR = "/";
    String BACK_SERVER_NAME = "app";//全部都携带的一个名称
    String SERVER = PROTOCOL + IP_WITH_PORT + SEPARATOR + BACK_SERVER_NAME + SEPARATOR;


    interface Login{
        String LOGIN_IN = "loginIn";
        String LOGIN_OUT = "loginOut";
    }
}
