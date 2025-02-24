package io.hotel.manage.service;

import io.hotel.manage.models.BookRoom;
import org.springframework.stereotype.Service;

@Service("no-op")
public class NoOpCalculatorStrategy implements CalculatorStategy {
    @Override
    public Double calculateAmount(BookRoom bookRoom) {
        return 1000.0;
    }
}
