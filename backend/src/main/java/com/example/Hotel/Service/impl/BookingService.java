package com.example.Hotel.Service.impl;

import com.example.Hotel.DTO.BookingDTO;
import com.example.Hotel.DTO.Response;
import com.example.Hotel.Entity.Booking;
import com.example.Hotel.Entity.Room;
import com.example.Hotel.Entity.User;
import com.example.Hotel.Exception.OurException;
import com.example.Hotel.Repo.BookingRepository;
import com.example.Hotel.Repo.RoomRepository;
import com.example.Hotel.Repo.UserRepository;
import com.example.Hotel.Service.Interface.IBookingService;
import com.example.Hotel.Service.Interface.IRoomService;
import com.example.Hotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingService implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IRoomService  roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response=new Response();
        try{
            if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
                throw new IllegalArgumentException("Check in or Check Out date is wrong ");
            }
            Room room=roomRepository.findById(roomId).orElseThrow(()->new OurException("Room not Found"));
            User user=userRepository.findById(userId).orElseThrow(()->new OurException("User Not Found"));
            List<Booking> existingBookings=room.getBookings();
            if(!isRoomAvailable(bookingRequest,existingBookings)){
                throw new OurException("Room not Available in given time.");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode= Utils.generateRandomAlphanumeric(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setBookingConfirmationCode(bookingConfirmationCode);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setMessage("Error");
            response.setStatusCode(500);

        }
        return response;
    }

    private boolean isRoomAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response=new Response();
        try{
            Booking booking=bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()->new OurException("Booking Not found"));
            BookingDTO bookingDTO=Utils.mapBookingEntityToBookingDTO(booking);
            response.setBooking(bookingDTO);
            response.setStatusCode(200);
            response.setMessage("Success");
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setMessage("Error");
            response.setStatusCode(500);

        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response=new Response();
        try{
            List<Booking> bookings=bookingRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<BookingDTO> bookingDTOList=Utils.mapBookingListEntityToBookingListDTO(bookings);

            response.setStatusCode(200);
            response.setMessage("Success");
            response.setBookingList(bookingDTOList);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setMessage("Error");
            response.setStatusCode(500);

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response=new Response();
        try{
            Booking booking=bookingRepository.findById(bookingId).orElseThrow(()-> new OurException("Booking Not Found"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Success");
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setMessage("Error");
            response.setStatusCode(500);

        }
        return response;
    }
}
