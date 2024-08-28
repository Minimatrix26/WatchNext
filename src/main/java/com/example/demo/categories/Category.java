package com.example.demo.categories;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @SequenceGenerator(
            name = "categories_id_seq",
            sequenceName = "categories_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "categories_id_seq"
    )
    private int id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
