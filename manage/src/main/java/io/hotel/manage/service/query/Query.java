package io.hotel.manage.service.query;

import io.hotel.manage.models.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Query {
    String latitude;
    String longtitude;
    List<PlaceType> placeTypes;
    String radius;

}
