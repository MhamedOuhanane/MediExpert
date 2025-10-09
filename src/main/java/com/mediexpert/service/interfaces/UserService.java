package com.mediexpert.service.interfaces;

public interface UserService {
    Boolean checkEmail(String email);
    Object login(String email, String password);
}
