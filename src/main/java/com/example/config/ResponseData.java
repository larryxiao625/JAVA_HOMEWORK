package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.time.Instant;

public class ResponseData implements Serializable {
    //code错误码
    private Integer code;
    //外带数据信息
    private Object data;
    //前端进行页面展示的信息
    private Object message="success";
    //返回时间戳
    private Long currentTime;
    /**
     *构造函数（无参数）
     */
    public ResponseData() {
        //毫秒
        this.currentTime = Instant.now().toEpochMilli();
    }
    /**
     *构造函数（有参数）
     */
    public ResponseData(Integer code, Object data, Object message) {
        this.code = code;
        this.data = data;
        this.message = message;
        //毫秒
        this.currentTime = Instant.now().toEpochMilli();
    }
    @Override
    public String toString() {
        return "RestReturn{" +
                ",code='" + code + '\'' +
                ",data=" + data +
                ",message=" + message +
                ",currentTime=" + currentTime +
                '}';
    }

    public ResponseData success(Object data, Object message) {
        this.code = 200;
        this.data = data;
        this.message = "success";

        return this;
    }

    public ResponseData error(Integer code, Object data, Object message) {
        this.code = code;
        this.data = data;
        this.message = message;

        return this;
    }

    public boolean isRestReturnJson(String data) {
        //临时实现先判定下字符串的格式和字段
        try {
            /**
             * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
             */
            ObjectMapper mapper = new ObjectMapper();
            ResponseData dataRestReturn = mapper.readValue(data, ResponseData.class);
            //比较两个类的字段,如果一致返回为真，不一致返回为假
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
