package com.example.libManage.dto.response;

import com.example.libManage.entity.user.UserBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {
    private UserBean user;
    private String token;
}
