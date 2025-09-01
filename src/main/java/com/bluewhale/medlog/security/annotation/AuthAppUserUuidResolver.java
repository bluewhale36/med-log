package com.bluewhale.medlog.security.annotation;

import com.bluewhale.medlog.appuser.domain.value.AppUserUuid;
import com.bluewhale.medlog.security.domain.entity.UserDetails_Impl;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthAppUserUuidResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasCorrectAnnotation = parameter.getParameterAnnotation(AuthAppUserUuid.class) != null,
                isAppUserUuidType = parameter.getParameterType().equals(AppUserUuid.class);

        return hasCorrectAnnotation && isAppUserUuidType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (!parameter.getParameterType().equals(AppUserUuid.class)) {
            throw new IllegalArgumentException(String.format("Argument type mismatch: %s", parameter.getParameterType()));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails_Impl userDetails) {
            return userDetails.getAppUserUuid();
        }

        return null;
    }

}
