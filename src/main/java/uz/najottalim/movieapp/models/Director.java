package uz.najottalim.movieapp.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    private int id;
    private String name;
    private int oscarCount;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Movie> movies;

    {
        movies = new ArrayList<>();
    }
}
