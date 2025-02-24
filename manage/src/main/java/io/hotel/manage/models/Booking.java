package io.hotel.manage.models;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Booking {
    String bookingId;
    String customerId;
    String hotelId;
    String roomId;
    Double amount;
    int person;
    int days;
}
