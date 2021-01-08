package cn.laochou.seckill.result;


/**
 * 状态码
 * 消息
 * 对应
 */
public class CodeMessage {

    private int code;

    private String message;

    // 通用异常
    public static final CodeMessage SUCCESS = new CodeMessage(200, "success");
    public static final CodeMessage SERVER_ERROR = new CodeMessage(500, "server error");
    public static final CodeMessage PARAM_ERROR = new CodeMessage(100, "param error");
    public static final CodeMessage NOT_LOGIN = new CodeMessage(202, "未登录");

    public static final CodeMessage MOBILE_FORMAT_ERROR = new CodeMessage(500212, "手机号格式错误");
    public static final CodeMessage MOBILE_NOT_EXIST = new CodeMessage(500213, "手机号不存在");
    public static final CodeMessage PASSWORD_ERROR = new CodeMessage(500214, "密码错误");
    public static final CodeMessage BIND_ERROR = new CodeMessage(500215, "参数校验异常 : %s");
    public static final CodeMessage GOODS_NOT_EXIST = new CodeMessage(500216, "您所查找的商品并不存在");


    private CodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeMessage fillMessage(String message) {
        this.message = String.format(this.message, message);
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
