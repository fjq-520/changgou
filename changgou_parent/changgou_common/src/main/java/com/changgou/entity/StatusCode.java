package com.changgou.entity;

public class StatusCode {
    public static final int OK = 20000;//成功
    public static final int ERROR = 20001;//失敗
    public static final int LOGINERROR = 20002;//用戶名或密碼錯誤，登錄失敗
    public static final int ACCESSERROR =20003;//权限不足
    public static final int REMOTEERROR =20004;//远程调用失败
    public static final int REPERROR =20005;//重复操作
}
