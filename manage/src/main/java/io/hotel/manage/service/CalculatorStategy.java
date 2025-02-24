package io.hotel.manage.service;

import io.hotel.manage.models.BookRoom;

public interface CalculatorStategy {

    Double calculateAmount(BookRoom bookRoom);
}
