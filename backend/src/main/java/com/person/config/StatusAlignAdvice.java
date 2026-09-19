package com.person.config;

import com.person.dto.dtoBase.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class StatusAlignAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResponse) {
            Integer status = ((BaseResponse) body).getStatus();
            if (status != null) {
                try {
                    response.setStatusCode(HttpStatus.valueOf(status));
                } catch (IllegalArgumentException ignored) {
                    // bilinmeyen kod gelirse HTTP'ye dokunma
                }
            }
        }
        return body;
    }
}
