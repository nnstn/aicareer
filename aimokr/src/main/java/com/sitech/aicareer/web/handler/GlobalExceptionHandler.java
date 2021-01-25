package com.sitech.aicareer.web.handler;

import com.sitech.aicareer.bean.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public JsonData runtimeexception(RuntimeException e) {
        log.error(e.getMessage(), e);
        return JsonData.fail(e.getMessage());
    }
}

