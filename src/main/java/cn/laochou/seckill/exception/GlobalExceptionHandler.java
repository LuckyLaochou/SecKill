package cn.laochou.seckill.exception;

import cn.laochou.seckill.result.CodeMessage;
import cn.laochou.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 拦截异常
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        if(e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            CodeMessage codeMessage = globalException.getCodeMessage();
            return Result.error(codeMessage);
        }else if(e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMessage.BIND_ERROR.fillMessage(message));
        }else {
            return Result.error(CodeMessage.SERVER_ERROR);
        }
    }

}
