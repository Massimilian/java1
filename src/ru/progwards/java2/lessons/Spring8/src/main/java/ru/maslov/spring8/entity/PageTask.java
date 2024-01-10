package ru.maslov.spring8.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageTask {
    private Integer page;
    private Integer count;
}

