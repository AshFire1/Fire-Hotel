package com.example.Hotel.Service.impl;

import com.example.Hotel.DTO.Response;
import com.example.Hotel.DTO.RoomDTO;
import com.example.Hotel.Entity.Room;
import com.example.Hotel.Exception.OurException;
import com.example.Hotel.Repo.BookingRepository;
import com.example.Hotel.Repo.RoomRepository;
import com.example.Hotel.Service.Interface.IRoomService;
import com.example.Hotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public Response addNewRoom(String ImageUrl, String roomType, BigDecimal roomPrice, String description) {
        Response response=new Response();

        try{
            Room room=new Room();
            room.setRoomPhotoUrl(ImageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom=roomRepository.save(room);
            RoomDTO roomDTO= Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
            response.setRoom(roomDTO);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error saving room ");
        }
        return response;
    }



    @Override
    public List<String> getAllRoomTypes() {
        List<String> roomTypeList=roomRepository.findDistinctRoomTypes();

        return roomTypeList;
    }

    @Override
    public Response getAllRooms() {
        Response response=new Response();

        try{
            List<Room> roomList=roomRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<RoomDTO>roomDTOList=Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
            response.setRoomList(roomDTOList);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error  ");
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response=new Response();

        try{
            roomRepository.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Succesfull");
        }catch (OurException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setMessage("Error");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, String ImageUrl) {
        Response response=new Response();

        try{
            Room room =roomRepository.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
            if(roomType!=null){
                room.setRoomType(roomType);
            }
            if(roomPrice!=null)
            {
                room.setRoomPrice(roomPrice);
            }
            if(ImageUrl!=null){
                room.setRoomPhotoUrl(ImageUrl);
            }
            if(description!=null){
                room.setRoomDescription(description);
            }
            Room updatedRoom=roomRepository.save(room);
            RoomDTO roomDTO=Utils.mapRoomEntityToRoomDTO(updatedRoom);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setRoom(roomDTO);
        }catch (OurException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error");
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response=new Response();

        try{
            Room room =roomRepository.findById(roomId).orElseThrow(()->new OurException("Room Not Found"));
            RoomDTO roomDTO =Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setRoom(roomDTO);
            response.setStatusCode(200);
        }catch (OurException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error");
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response=new Response();

        try{
            List<Room> roomList=roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate,checkOutDate,roomType);
            List<RoomDTO> roomDTOList=Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setRoomList(roomDTOList);
            response.setMessage("Success");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error");
        }
        return response;
    }

    @Override
    public Response getAvailableRooms() {
        Response response=new Response();

        try{
            List<Room>roomList=roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList=Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setRoomList(roomDTOList);
            response.setMessage("Success");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error");
        }
        return response;
    }
}
