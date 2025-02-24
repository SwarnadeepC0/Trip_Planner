package io.hotel.manage.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserFilter {
    private String latitude;
    private String longtitude;
    private List<PlaceType> filters;
    private String radius;
}
