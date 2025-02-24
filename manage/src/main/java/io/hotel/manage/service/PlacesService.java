package io.hotel.manage.service;

import io.hotel.manage.models.Place;
import io.hotel.manage.service.query.Query;

import java.io.IOException;
import java.util.Iterator;

public interface PlacesService {

    Iterator<Place> getPlaces(Query query) ;
}
