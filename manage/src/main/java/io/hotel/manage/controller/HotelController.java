package io.hotel.manage.controller;

import io.hotel.manage.models.BookRoom;
import io.hotel.manage.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController{

    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/manage/hotels/{hotelId}/rooms/{roomId}", method=RequestMethod.POST)
    public ResponseEntity<String> bookRoom(@RequestBody BookRoom bookRoom, @PathVariable(name="hotelId")String hotelId,@PathVariable(name="roomId")String roomId ){
       return ResponseEntity.ofNullable(bookingService.book(bookRoom));
    }



//    @RequestMapping("/manage/hotel")
//    public ResponseEntity<Hotels> getHotels(){
//
//    }

    //    @RequestMapping("/manage/hotel/:hotel_id")
//    public ResponseEntity<Hotel> getHotel(){
//
//    }

    //@RequestMapping("/manage/hotels/:hotel_id/rooms")
    //public ResponseEntity<Rooms> getRooms(){}





}
