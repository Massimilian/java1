package ru.maslov.spring8.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PageTask {
    private Integer page;
    private Integer count;
}
