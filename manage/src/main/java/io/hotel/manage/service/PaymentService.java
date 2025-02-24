package io.hotel.manage.service;

import io.hotel.manage.models.Billing;
import io.hotel.manage.models.Booking;

public interface PaymentService {

    Billing pay(Booking booking);

    String getPaymentLink(Booking booking);
}
