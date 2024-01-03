package com.example.testmock.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    private long id;
    private String name;
    private Date date;
    private PriorityType priority;
    private Type type;
}
