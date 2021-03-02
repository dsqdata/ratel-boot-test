package org.ratel.config;

import lombok.extern.slf4j.Slf4j;
import org.ratel.framework.module.handler.AbstractGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author Ratel-Cloud
 * @version 1.0
 * @date 2021/02/24
 */
@Configuration
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class ExceptionConfiguration extends AbstractGlobalExceptionHandler {
}
