package com.alivn.tools.web.args;

import lombok.Data;

/**
 * User: lijinpeng
 * Created by Shanghai on 2020/3/1
 */
@Data
public class Response<T> {
    private String rspCode;
    private String rspDesc;
    private T result;
}
