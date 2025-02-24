package io.hotel.manage.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRoom {
    private String roomId;
    private String hotelId;
    private String customerId;
    private int days;
    private int person;
}
