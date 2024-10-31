package com.example.Hotel.Service.impl;

import com.example.Hotel.DTO.LoginRequest;
import com.example.Hotel.DTO.Response;
import com.example.Hotel.DTO.UserDTO;
import com.example.Hotel.Entity.User;
import com.example.Hotel.Exception.OurException;
import com.example.Hotel.Repo.UserRepository;
import com.example.Hotel.Service.Interface.IUserService;
import com.example.Hotel.utils.JWTUtils;
import com.example.Hotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public Response register(User user) {
        Response response=new Response();

        try{
            if(user.getRole()==null ||user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepository.existsByEmail(user.getEmail())){
                throw new OurException(user.getEmail()+"Already Exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser=userRepository.save(user);
            UserDTO userDTO= Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        }catch(OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occured During User Registraion" + e.getMessage());
        }
        return response;

    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response=new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            var user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException(("User Not Found")));
            var token=jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Succesfull");
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occured During User Registraion" + e.getMessage());
        }
        return response;

    }

    @Override
    public Response getAllUsers() {
        Response response= new Response();
        try{
            List<User> userList=userRepository.findAll();
            List<UserDTO> userDTOList=Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
            response.setUserList(userDTOList);
        }catch (OurException e){
            response.setStatusCode(500);
            response.setMessage("Problem .");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response=new Response();
        try{
            User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User Not Found"));
            UserDTO userDTO=Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting Users");

        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response=new Response();
        try{
            User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Succesfull");
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting Users");

        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response=new Response();
        try{
            User user=userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User Not Found"));
            UserDTO userDTO=Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting Users");

        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response=new Response();
        try{
            User user=userRepository.findByEmail(email).orElseThrow(()->new OurException("User Not Found"));
            UserDTO userDTO=Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
            response.setUser(userDTO);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting Users");

        }
        return response;
    }
}
