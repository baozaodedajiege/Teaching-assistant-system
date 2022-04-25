package com.ctgu.dto;

import com.ctgu.exception.WebError;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class AjaxResult extends HashMap<String, Object> {

    //TODO::深度依赖了，后面需要统一throw
    @Deprecated
    public static final String COMMON_ERROR = "接口调用出错";

    public static final String MESSAGE = "message";
    public static final String SUCCESS = "success";
    public static final String DATA = "data";
    public static final String CODE = "code";

    public static final AjaxResult BLANK_SUCCESS = new AjaxResult().setSuccess(true);

    private static final Map<WebError, AjaxResult> map = new HashMap<>();

    public static AjaxResult fixedError(WebError webError) {
        if (map.get(webError) == null) {
            synchronized (AjaxResult.class) {
                map.computeIfAbsent(webError, e -> new AjaxResult().setState(e));
            }
        }
        return map.get(webError);
    }

    public AjaxResult() {
        put(MESSAGE, "");
        put(SUCCESS, false);
    }

    public AjaxResult setState(WebError webError) {
        put(MESSAGE, webError.errMsg);
        put(CODE, webError.code);
        put(SUCCESS, false);
        return this;
    }

    private AjaxResult setCode(int code) {
        put(CODE, code);
        return this;
    }

    public AjaxResult setMessage(String msg) {
        setSuccess(false);
        put(MESSAGE, msg);
        return this;
    }

    public AjaxResult setSuccess(boolean ret) {
        put(SUCCESS, ret);
        return this;
    }

    public AjaxResult setData(Object obj) {
        setSuccess(true);
        put(DATA, obj);
        return this;
    }

}