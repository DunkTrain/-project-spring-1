package com.javarush.shevchenko.domain;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "todo", name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

}



