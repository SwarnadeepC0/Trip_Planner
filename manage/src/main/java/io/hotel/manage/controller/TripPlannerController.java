package io.hotel.manage.controller;

import io.hotel.manage.models.Place;
import io.hotel.manage.models.Places;
import io.hotel.manage.models.UserFilter;
import io.hotel.manage.service.TripPlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripPlannerController {
    @Autowired
    TripPlannerService tripPlannerService;

    @RequestMapping(value="/manage/hotel/trips/suggestion", method= RequestMethod.POST)
    public ResponseEntity<Places> getTripSuggestion(@RequestBody UserFilter userFilter){
        List<Place> places =  tripPlannerService.getPlaces(userFilter);
        return ResponseEntity.ofNullable(new Places(places));
    }
}
