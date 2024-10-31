package com.example.Hotel.Service.Interface;

import com.example.Hotel.DTO.LoginRequest;
import com.example.Hotel.DTO.Response;
import com.example.Hotel.Entity.User;

public interface IUserService {


    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);


}
