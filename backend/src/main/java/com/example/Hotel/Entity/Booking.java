package com.example.Hotel.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Check In Date is Required")
    private LocalDate checkInDate;
    @NotNull(message = "Check Out Date should be in future. ")
    private LocalDate checkOutDate;
    @Min(value=1,message="Number of Adults must be greater than 0")
    private int numOfAdults;
    @Min(value=0,message = "Number of Child cannot be less than 0")
    private int numOfChildren;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    public  void calculateTotalGuests(){
        this.totalNumOfGuest=this.numOfAdults+this.numOfChildren;
    }

    public void setNumOfAdults(@Min(value = 1, message = "Number of Adults must be greater than 0") int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalGuests();
    }

    public void setNumOfChildren(@Min(value = 0, message = "Number of Child cannot be less than 0") int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildern=" + numOfChildren +
                ", totalNumOfGuest=" + totalNumOfGuest +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                ", user=" + user +
                ", room=" + room +
                '}';
    }
}
