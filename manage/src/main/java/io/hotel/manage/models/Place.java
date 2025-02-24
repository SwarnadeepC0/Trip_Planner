package io.hotel.manage.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Place {
    private String placeId;
    private String name;
    private String locationUri;
    private String rating;
    private String summary;
    private String type;

}
