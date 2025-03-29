package com.soft.MarketPlace.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String HandleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            Integer code = Integer.valueOf(status.toString());
            if(code == HttpStatus.NOT_FOUND.value() || code == HttpStatus.GATEWAY_TIMEOUT.value() || code ==  HttpStatus.LOCKED.value() || code == HttpStatus.MOVED_PERMANENTLY.value()) {
                return "error/404";
            }else if(code == HttpStatus.INTERNAL_SERVER_ERROR.value() || code ==  HttpStatus.TOO_MANY_REQUESTS.value()) {
                return "error/500";
            }else if(code == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }else if(code == HttpStatus.BAD_REQUEST.value()) {
                return "error/400";
            }else if(code == HttpStatus.CONFLICT.value()) {
                return "error/409";
            }else if(code == HttpStatus.REQUEST_TIMEOUT.value()) {
                return "error/408";
            }else if(code == HttpStatus.BAD_GATEWAY.value()) {
                return "error/502";
            }
            else {
                return "error/400";
            }
        }
        return "error";
    }
}
