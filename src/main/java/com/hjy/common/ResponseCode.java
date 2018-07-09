package com.hjy.common;

/**
 * Created by weimin
 */
public enum ResponseCode {
    /**
     * 一切正常
     */
    SUCCESS(200,"SUCCESS"),
    /**
     * 新资源已经被创建
     */
    BUILD_SUCCESS(201,"BUILD_SUCCESS"),
    /**
     * 资源删除成功
     */
    DELETE_SUCCESS(201,"DELETE_SUCCESS"),

    /**
     * 非法参数
     */
    BAD_REQUEST(400,"Bad Request"),
    /**
     * 需要登录
     */
    NEED_LOGIN(401,"需要登录"),
    /**
     * 未找到，指定的资源不存在
     */
    NOT_FIND(404,"NOT_FIND"),
    /**
     * 服务器错误
     */
    ERROR(500,"ERROR");

    private final int code;
    private final String desc;


    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
