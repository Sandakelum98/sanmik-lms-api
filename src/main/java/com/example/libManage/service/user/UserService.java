package com.example.libManage.service.user;

import com.example.libManage.dto.request.user.UserLoginRequest;
import com.example.libManage.dto.request.user.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ResponseEntity register(HttpServletRequest request, UserRegisterRequest userRegisterRequest);

    ResponseEntity login(HttpServletRequest request, UserLoginRequest userLoginRequest);

}
