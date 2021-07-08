package cn.laochou.seckill.result;

import java.io.Serializable;

public class Result<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

    /**
     * 成功的时候返回
     * @param data 返回的数据
     * @param <T> 泛型
     * @return 返回封装的数据
     * 注意，这个 T 是可以更改的，因为我们是通过 T data 来得到 类型的
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> success(CodeMessage data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMessage codeMessage) {
        return new Result<>(codeMessage);
    }


    private Result(CodeMessage codeMessage) {
        if(codeMessage == null) {
            return;
        }
        this.code = codeMessage.getCode();
        this.message = codeMessage.getMessage();
    }

    private Result(T data) {
        this.code = CodeMessage.SUCCESS.getCode();
        this.message = CodeMessage.SUCCESS.getMessage();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
