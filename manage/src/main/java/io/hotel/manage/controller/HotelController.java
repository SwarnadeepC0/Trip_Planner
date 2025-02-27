package io.hotel.manage.controller;

import com.stripe.model.v2.Event;
import io.hotel.manage.models.BookRoom;
import io.hotel.manage.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
public class HotelController{

    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/manage/hotels/{hotelId}/rooms/{roomId}", method=RequestMethod.POST)
    public ResponseEntity<String> bookRoom(@RequestBody BookRoom bookRoom, @PathVariable(name="hotelId")String hotelId,@PathVariable(name="roomId")String roomId ){
       return ResponseEntity.ofNullable(bookingService.book(bookRoom));
    }

    @RequestMapping(value = "/manage/hotels/payment/success", method=RequestMethod.POST)
    public ResponseEntity<String> paymentSuccess(@RequestBody Object object){
        HashMap<String, Object> event = (LinkedHashMap) object;
        String type = (String)event.get("type");

        switch (type) {

            case "checkout.session.completed":
                HashMap<String, Object>data = (LinkedHashMap) event.get("data");
                String paymentLinkId = (String)((LinkedHashMap) data.get("object")).get("payment_link");
                System.out.println(paymentLinkId);
                break;
            default:
                System.out.println("Unhandled event type: " + type);
        }

        return null;
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
