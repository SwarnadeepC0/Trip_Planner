package io.hotel.manage.service;

import io.hotel.manage.models.UserFilter;
import io.hotel.manage.service.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import io.hotel.manage.models.Place;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TripPlannerService {
    @Autowired
    @Qualifier(value="googlePlaceService")
    PlacesService placesService;

    public List<Place> getPlaces(UserFilter filter){
        Query query = adaptFilterToQuery(filter);
        List<Place> places = new ArrayList<>();
        for (Iterator<Place> it = placesService.getPlaces(query); it.hasNext(); ) {
            places.add(it.next());
        }
        return places;
    }

    private Query adaptFilterToQuery(UserFilter filter) {
        Query.QueryBuilder queryBuilder = Query.builder().latitude(filter.getLatitude())
                .longtitude(filter.getLongtitude())
                .radius(filter.getRadius())
                .placeTypes(filter.getFilters());
        return queryBuilder.build();
    }

}
