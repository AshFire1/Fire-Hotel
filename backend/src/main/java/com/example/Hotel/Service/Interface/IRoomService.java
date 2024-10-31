package com.example.Hotel.Service.Interface;

import com.example.Hotel.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {


    Response addNewRoom(String ImageUrl, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Response getAllRooms();

    Response deleteRoom(Long roomId);



    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, String ImageUrl);

    Response getRoomById(Long roomId);

    Response getAvailableRoomsByDataAndType(LocalDate checkInDate,LocalDate checkOutDate,String roomType);

    Response getAvailableRooms();
}
