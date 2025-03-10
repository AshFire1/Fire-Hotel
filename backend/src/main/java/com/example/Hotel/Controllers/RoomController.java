package com.example.Hotel.Controllers;

import com.example.Hotel.DTO.Response;
import com.example.Hotel.Service.Interface.IBookingService;
import com.example.Hotel.Service.Interface.IRoomService;
import com.example.Hotel.Service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewRoom(@RequestParam(value = "roomPhotoUrl",required = false)String photoUrl,
                                               @RequestParam(value = "roomType",required = false)String roomType,
                                               @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription",required = false)String roomDescription){
        if(photoUrl==null ||photoUrl.isBlank() ||roomType==null || roomType.isBlank() || roomPrice==null ){
                Response response=new Response();
                response.setStatusCode(400);
                response.setMessage("Please Provide Values for All fields");
                return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response =roomService.addNewRoom(photoUrl,roomType,roomPrice,roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/all")
    public  ResponseEntity<Response> getAllRooms    (){
        Response response=roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/types")
    public List<String>  getRoomTypes(){
        List<String> response=roomService.getAllRoomTypes();
        return response;
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable("roomId") Long roomId){
        Response response=roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all-available-rooms")
    public  ResponseEntity<Response> getAvailableRooms()
    {
        Response response=roomService.getAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> getAvailableRoomsByDateAndType(
            @RequestParam(required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(required = false)@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(required = false)String roomType
            ){
        if(checkInDate==null ||roomType==null || roomType.isBlank() || checkOutDate==null ){
            Response response=new Response();
            response.setStatusCode(400);
            response.setMessage("Please Provide Values for All fields");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response =roomService.getAvailableRoomsByDataAndType(checkInDate,checkOutDate,roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId,
                                               @RequestParam(value = "roomPhotoUrl",required = false)String photoUrl,
                                               @RequestParam(value = "roomType",required = false)String roomType,
                                               @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription",required = false)String roomDescription
                                               ){
        Response response=roomService.updateRoom(roomId,roomDescription,roomType,roomPrice,photoUrl);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponseEntity<Response> deleteRoom(@PathVariable Long roomId){
        Response response=roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
