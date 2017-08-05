package com.flyzend.baseproject.client;

/**
 * Created by 王灿 on 2017/7/24.
 */

public class BaseZipBean {
    private String mZipBean1;
    private String mZipBean2;
    private String mZipBean3;
    private String mZipBean4;
    private String mZipBean5;

    public BaseZipBean(String zipBean1, String zipBean2) {
        mZipBean1 = zipBean1;
        mZipBean2 = zipBean2;
    }

    public BaseZipBean(String zipBean1, String zipBean2, String zipBean3) {
        mZipBean1 = zipBean1;
        mZipBean2 = zipBean2;
        mZipBean3 = zipBean3;
    }

    public BaseZipBean(String zipBean1, String zipBean2, String zipBean3, String zipBean4) {
        mZipBean1 = zipBean1;
        mZipBean2 = zipBean2;
        mZipBean3 = zipBean3;
        mZipBean4 = zipBean4;
    }

    public BaseZipBean(String zipBean1, String zipBean2, String zipBean3, String zipBean4, String zipBean5) {
        mZipBean1 = zipBean1;
        mZipBean2 = zipBean2;
        mZipBean3 = zipBean3;
        mZipBean4 = zipBean4;
        mZipBean5 = zipBean5;
    }

    public String getZipBean1() {
        return mZipBean1;
    }

    public String getZipBean2() {
        return mZipBean2;
    }

    public String getZipBean3() {
        return mZipBean3;
    }

    public String getZipBean4() {
        return mZipBean4;
    }

    public String getZipBean5() {
        return mZipBean5;
    }
}
