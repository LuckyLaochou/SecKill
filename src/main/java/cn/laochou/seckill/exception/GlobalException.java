package cn.laochou.seckill.exception;

import cn.laochou.seckill.result.CodeMessage;

public class GlobalException extends RuntimeException{

    private final CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage) {
        this.codeMessage = codeMessage;
    }


    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
