package io.hotel.manage.service;

import io.hotel.manage.models.BookRoom;
import org.springframework.stereotype.Service;

@Service
public class RateCalculator {

    private final CalculatorStategy calculatorStrategy;

    public RateCalculator(CalculatorStategy calculatorStrategy) {
        this.calculatorStrategy = calculatorStrategy;
    }

    private CalculatorStategy getStrategy(){
        return calculatorStrategy;
    }

    public Double calculate(BookRoom bookRoom){
        return calculatorStrategy.calculateAmount(bookRoom);
    }

}
