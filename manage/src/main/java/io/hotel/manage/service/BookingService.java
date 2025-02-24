package io.hotel.manage.service;

import io.hotel.manage.models.BookRoom;
import io.hotel.manage.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    RateCalculator rateCalculator;

    @Autowired
    @Qualifier("stripe")
    PaymentService paymentService;

    public String book(BookRoom bookRoom) {
        return paymentService.getPaymentLink(adaptToBooking(bookRoom));
    }

    private Booking adaptToBooking(BookRoom bookRoom) {
        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setDays(bookRoom.getDays());
        booking.setAmount(calculateAmount(bookRoom));
        booking.setCustomerId(bookRoom.getCustomerId());
        booking.setPerson(bookRoom.getPerson());
        booking.setHotelId(bookRoom.getHotelId());
        booking.setRoomId(bookRoom.getRoomId());
        return booking;
    }

    private Double calculateAmount(BookRoom bookRoom) {
        return rateCalculator.calculate(bookRoom);
    }
}
