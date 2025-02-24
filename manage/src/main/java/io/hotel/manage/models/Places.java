package io.hotel.manage.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Getter
@Setter
public class Places extends ProblemDetail {
    private final List<Place> places;
    public Places(List<Place> places){
        this.places = places;
    }
}
