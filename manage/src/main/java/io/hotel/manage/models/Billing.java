package io.hotel.manage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Billing {
    private String billingId;
    private String bookingId;
    private String billingAmount;
    private String internalBillingId;
    private String customerId;
    private String roomId;
    private String hotelId;

    public  Billing(String bookingId, String customerId, String hotelId, String roomId, Double amount, String id) {
        this.bookingId = bookingId;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.billingAmount = Double.toString(amount);
        this.internalBillingId = id;
        this.customerId = customerId;
        this.billingId = UUID.randomUUID().toString();
    }
}
