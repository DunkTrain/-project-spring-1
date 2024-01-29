package com.javarush.shevchenko.dto;

import lombok.Getter;
import lombok.Setter;
import com.javarush.shevchenko.domain.Status;

@Getter @Setter
public class TaskDTO {
    private String description;
    private Status status;
}
