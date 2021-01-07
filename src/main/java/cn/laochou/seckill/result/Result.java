package cn.laochou.seckill.result;

public class Result<T> {

    private int code;

    private String message;

    private T data;

    /**
     * 成功的时候返回
     * @param data 返回的数据
     * @param <T> 泛型
     * @return 返回封装的数据
     */
    public static <T> Result<T> success(T data) {
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
        this.code = 200;
        this.message = "success";
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
