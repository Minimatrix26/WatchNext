package com.example.demo.movies;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @SequenceGenerator(
            name = "movies_id_seq",
            sequenceName = "movies_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movies_id_seq"
    )
    private int id;
    private String title;
    @Column(
            name = "categoryid"
    )
    private int categoryId;
    @Column(
            name = "imdbid"
    )
    private String imdbId;
    @Column(
            name = "imdbscore"
    )
    private double imdbScore;
    @Column(
            name = "description"
    )
    private String description;
    @Column(
            name = "releasedate"
    )
    private LocalDate releaseDate;

    public Movie(String title, int categoryId, String imdbId, double imdbScore, String description, LocalDate releaseDate) {
        this.title = title;
        this.categoryId = categoryId;
        this.imdbId = imdbId;
        this.imdbScore = imdbScore;
        this.description = description;
        this.releaseDate = releaseDate;
    }


}
